package rabbitescape.engine.behaviours.rabbotBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.World;

public class RabbotCrashing extends RabbotBehaviour
{
    public RabbotCrashing( RabbotHandler rabbotHandler) { super(rabbotHandler); }

    @Override
    public boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world )
    {
            for ( BehaviourExecutor otherBehaviourExecutor : world.behaviourExecutors )
            {
                if ( otherBehaviourExecutor.type == OldRabbit.Type.RABBIT &&
                    otherBehaviourExecutor.x == behaviourExecutor.x &&
                    otherBehaviourExecutor.y == behaviourExecutor.y
                )
                {
                    world.changes.killRabbit( otherBehaviourExecutor );
                    return true;
                }
            }

        return false;
    }

    @Override
    public State newState(BehaviourTools t)
    {
        boolean triggered = checkTriggered( t.behaviourExecutor, t.world );
        if ( triggered )
        {
            return State.RABBIT_CRASHING;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_CRASHING:
            {
                world.changes.killRabbit( behaviourExecutor );
                return true;
            }
            default:
                return false;
        }
    }
}
