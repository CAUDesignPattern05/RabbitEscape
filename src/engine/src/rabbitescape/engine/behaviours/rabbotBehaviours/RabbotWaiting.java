package rabbitescape.engine.behaviours.rabbotBehaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.behaviours.actions.Blocking;
import rabbitescape.engine.behaviours.actions.Digging;

public class RabbotWaiting extends RabbotBehaviour
{
    public RabbotWaiting( RabbotHandler rabbotHandler) { super(rabbotHandler); }

    private boolean within1Vertically( BehaviourExecutor otherBehaviourExecutor, BehaviourExecutor behaviourExecutor )
    {
        return ( Math.abs( otherBehaviourExecutor.y - behaviourExecutor.y ) < 2 );
    }

    private boolean noseToNose( BehaviourExecutor otherBehaviourExecutor, BehaviourExecutor behaviourExecutor )
    {
        if (
            otherBehaviourExecutor.x == behaviourExecutor.x - 1 &&
            otherBehaviourExecutor.getDirection() == Direction.RIGHT &&
            behaviourExecutor.getDirection() == Direction.LEFT
        )
        {
            return true;
        }
        else if (
            otherBehaviourExecutor.x == behaviourExecutor.x + 1 &&
            otherBehaviourExecutor.getDirection() == Direction.LEFT &&
            behaviourExecutor.getDirection() == Direction.RIGHT
        )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world )
    {
        if (
            !Blocking.isBlocking( behaviourExecutor.state) &&
            !Digging.isDigging( behaviourExecutor.state)
        )
        {
            for ( BehaviourExecutor otherBehaviourExecutor : world.behaviourExecutors )
            {
                if (
                    otherBehaviourExecutor.getAffectedByRabbot() &&
                    within1Vertically( otherBehaviourExecutor, behaviourExecutor ) &&
                    noseToNose( otherBehaviourExecutor, behaviourExecutor ) &&
                    !Blocking.isBlocking( otherBehaviourExecutor.state)
                )
                {
                    return true;
                }
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
            return t.rl( 
                State.RABBIT_WAITING_RIGHT,
                State.RABBIT_WAITING_LEFT 
            );
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
            case RABBIT_WAITING_LEFT:
            case RABBIT_WAITING_RIGHT:
            {
                 return true;
            }
            default:
                return false;
        }
    }
}
