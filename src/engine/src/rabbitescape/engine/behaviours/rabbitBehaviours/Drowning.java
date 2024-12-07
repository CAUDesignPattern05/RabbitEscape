package rabbitescape.engine.behaviours.rabbitBehaviours;

import static rabbitescape.engine.CellularDirection.DOWN;
import static rabbitescape.engine.CellularDirection.UP;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Drowning extends RabbitBehaviour
{
    public Drowning( RabbitHandler rabbitHandler) { super(rabbitHandler); }

    @Override
    public boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world )
    {
        int yCoordinate = behaviourExecutor.y;
        CellularDirection directionToCheck = UP;
        if ( behaviourExecutor.isOnSlope() )
        {
            // The rabbit's head is at the bottom of the cell above.
            yCoordinate = behaviourExecutor.y - 1;
            directionToCheck = DOWN;
        }
        // TODO Find out why the rabbit's y coordinate is allowed to be
        // larger than the size of the world (see solution for easy-12).
        if ( yCoordinate < 0 || yCoordinate >= world.size.height )
        {
            return false;
        }
        for ( WaterRegion waterRegion :
              world.waterTable.getItemsAt( behaviourExecutor.x, yCoordinate ) )
        {
            if ( waterRegion.isConnected( directionToCheck ) )
            {
                return ( waterRegion.getContents() >= waterRegion.capacity );
            }
        }
        return false;
    }

    @Override
    public State newState(BehaviourTools t)
    {
        boolean triggered = checkTriggered( t.behaviourExecutor, t.world );
        return ( triggered ? State.RABBIT_DROWNING : null );
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_DROWNING:
            {
                notifyDeath( behaviourExecutor );
                return true;
            }
            default:
                return false;
        }
    }
}
