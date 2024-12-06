package rabbitescape.engine.behaviours.rabbitBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public class Burning extends RabbitBehaviour
{
    public Burning( RabbitHandler rabbitHandler) { super(rabbitHandler); }

    @Override
    public boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world )
    {
        return world.fireAt( behaviourExecutor.x, behaviourExecutor.y );
    }

    @Override
    public State newState(BehaviourTools t)
    {
        boolean triggered = checkTriggered( t.behaviourExecutor, t.world );
        if ( triggered )
        {
            if ( t.behaviourExecutor.isOnSlope() )
            {
                return RABBIT_BURNING_ON_SLOPE;
            }
            else
            {
                return RABBIT_BURNING;
            }
        }

        return null;
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_BURNING:
            case RABBIT_BURNING_ON_SLOPE:
            {
                world.changes.killRabbit( behaviourExecutor );
                return true;
            }
            default:
                return false;
        }
    }
}
