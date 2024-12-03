package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;

public class RabbotWait extends Behaviour
{
    private boolean within1Vertically( OldRabbit otherOldRabbit, OldRabbit oldRabbit )
    {
        return ( Math.abs( otherOldRabbit.y - oldRabbit.y ) < 2 );
    }

    private boolean noseToNose( OldRabbit otherOldRabbit, OldRabbit oldRabbit )
    {
        if ( 
            otherOldRabbit.x == oldRabbit.x - 1 &&
            otherOldRabbit.dir == Direction.RIGHT &&
            oldRabbit.dir == Direction.LEFT
        )
        {
            return true;
        }
        else if ( 
            otherOldRabbit.x == oldRabbit.x + 1 &&
            otherOldRabbit.dir == Direction.LEFT &&
            oldRabbit.dir == Direction.RIGHT
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
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( OldRabbit oldRabbit, World world )
    {
        if (
            oldRabbit.type == OldRabbit.Type.RABBOT &&
            !Blocking.isBlocking( oldRabbit.state) &&
            !Digging.isDigging( oldRabbit.state)
        )
        {
            for ( OldRabbit otherOldRabbit : world.oldRabbits )
            {
                if (
                    otherOldRabbit.type == OldRabbit.Type.RABBIT &&
                    within1Vertically( otherOldRabbit, oldRabbit ) &&
                    noseToNose( otherOldRabbit, oldRabbit ) &&
                    !Blocking.isBlocking( otherOldRabbit.state)
                )
                {
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
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        if ( 
            state == State.RABBIT_WAITING_LEFT ||
            state == State.RABBIT_WAITING_RIGHT 
        )
        {
            return true;
        }

        return false;
    }
}
