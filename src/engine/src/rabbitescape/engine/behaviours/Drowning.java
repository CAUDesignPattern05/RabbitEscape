package rabbitescape.engine.behaviours;

import static rabbitescape.engine.CellularDirection.DOWN;
import static rabbitescape.engine.CellularDirection.UP;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.CellularDirection;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.WaterRegion;
import rabbitescape.engine.World;

public class Drowning extends Behaviour
{
    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( OldRabbit oldRabbit, World world )
    {
        if ( oldRabbit.type == OldRabbit.Type.RABBOT )
        {
            return false;  // Rabbots don't drown
        }

        int yCoordinate = oldRabbit.y;
        CellularDirection directionToCheck = UP;
        if ( oldRabbit.onSlope )
        {
            // The rabbit's head is at the bottom of the cell above.
            yCoordinate = oldRabbit.y - 1;
            directionToCheck = DOWN;
        }
        // TODO Find out why the rabbit's y coordinate is allowed to be
        // larger than the size of the world (see solution for easy-12).
        if ( yCoordinate < 0 || yCoordinate >= world.size.height )
        {
            return false;
        }
        for ( WaterRegion waterRegion :
              world.waterTable.getItemsAt( oldRabbit.x, yCoordinate ) )
        {
            if ( waterRegion.isConnected( directionToCheck ) )
            {
                return ( waterRegion.getContents() >= waterRegion.capacity );
            }
        }
        return false;
    }

    @Override
    public State newState(
        BehaviourTools t,
        boolean triggered )
    {
        return ( triggered ? State.RABBIT_DROWNING : null );
    }

    @Override
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        switch ( state )
        {
        case RABBIT_DROWNING:
            world.changes.killRabbit( oldRabbit );
            return true;
        default:
            return false;
        }
    }
}
