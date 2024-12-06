package rabbitescape.engine.behaviours.deathBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Behaviour;

public class Exiting extends Behaviour
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
            return false;  // Rabbots ignore exits
        }

        for ( Thing thing : world.things )
        {
            if (
                   ( thing instanceof Exit )
                && ( thing.x == oldRabbit.x && thing.y == oldRabbit.y )
            )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            if ( t.oldRabbit.state == RABBIT_CLIMBING_LEFT_CONTINUE_2 )
            {
                return RABBIT_ENTERING_EXIT_CLIMBING_LEFT;
            }
            if ( t.oldRabbit.state == RABBIT_CLIMBING_RIGHT_CONTINUE_2 )
            {
                return RABBIT_ENTERING_EXIT_CLIMBING_RIGHT;
            }
            return RABBIT_ENTERING_EXIT;
        }
        return null;
    }

    @Override
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        if (
               state == RABBIT_ENTERING_EXIT
            || state == RABBIT_ENTERING_EXIT_CLIMBING_RIGHT
            || state == RABBIT_ENTERING_EXIT_CLIMBING_LEFT
           )
        {
            world.changes.saveRabbit( oldRabbit );
            return true;
        }
        else
        {
            return false;
        }
    }
}
