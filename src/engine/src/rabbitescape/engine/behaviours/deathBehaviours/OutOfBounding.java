package rabbitescape.engine.behaviours.deathBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.World;
import rabbitescape.engine.config.TapTimer;

public class OutOfBounding extends Behaviour
{
    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( OldRabbit oldRabbit, World world )
    {
        return (
            oldRabbit.x < 0
            || oldRabbit.x >= world.size.width
            || oldRabbit.y < 0
            || oldRabbit.y >= world.size.height
        );
    }

    @Override
    public State newState(
        BehaviourTools t, boolean triggered
    )
    {
        if ( triggered )
        {
            return RABBIT_OUT_OF_BOUNDS;
        }

        return null;
    }

    @Override
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        switch( state )
        {
            case RABBIT_OUT_OF_BOUNDS:
            {
                checkMars( world, oldRabbit );
                world.changes.killRabbit( oldRabbit );
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    /**
     * Test if mars mode has been triggered
     */
    private void checkMars( World world, OldRabbit oldRabbit )
    {
        /* The rabbit must leave the world at the correct coordinates,
         * the index count is likely to only be correct if this is the
         * first rabbit out of the entrance, and it must be the correct
         * level.
         */
        if ( 12 == oldRabbit.x && -1 == oldRabbit.y &&
             world.getRabbitIndexCount() == 2 &&
             world.name.equals( "Ghost versus pie" ) )
        {
            TapTimer.setMars();
        }
    }
}
