package rabbitescape.engine.behaviours;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.World;

public class RabbotCrash extends Behaviour
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
            for ( OldRabbit otherOldRabbit : world.oldRabbits )
            {
                if ( otherOldRabbit.type == OldRabbit.Type.RABBIT &&
                    otherOldRabbit.x == oldRabbit.x &&
                    otherOldRabbit.y == oldRabbit.y
                )
                {
                    world.changes.killRabbit( otherOldRabbit );
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
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
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        if ( state == State.RABBIT_CRASHING )
        {
            world.changes.killRabbit( oldRabbit );
            return true;
        }

        return false;
    }
}
