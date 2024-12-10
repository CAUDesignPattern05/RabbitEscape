package rabbitescape.engine.behaviours.rabbitBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Exiting extends RabbitBehaviour
{
    public Exiting( RabbitHandler rabbitHandler) { super(rabbitHandler); }

    @Override
    public boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world )
    {
        for ( Thing thing : world.things )
        {
            if (
                   ( thing instanceof Exit )
                && ( thing.x == behaviourExecutor.x && thing.y == behaviourExecutor.y )
            )
            {
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
            if ( t.behaviourExecutor.state == RABBIT_CLIMBING_LEFT_CONTINUE_2 )
            {
                return RABBIT_ENTERING_EXIT_CLIMBING_LEFT;
            }
            if ( t.behaviourExecutor.state == RABBIT_CLIMBING_RIGHT_CONTINUE_2 )
            {
                return RABBIT_ENTERING_EXIT_CLIMBING_RIGHT;
            }
            return RABBIT_ENTERING_EXIT;
        }
        return null;
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch( state )
        {
            case RABBIT_ENTERING_EXIT:
            case RABBIT_ENTERING_EXIT_CLIMBING_LEFT:
            case RABBIT_ENTERING_EXIT_CLIMBING_RIGHT:
            {
                notifyExiting( behaviourExecutor );
                return true;
            }
            default:
                return false;
        }
    }
}
