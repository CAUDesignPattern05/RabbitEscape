package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.World;

public class Burning extends Behaviour
{
    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( OldRabbit oldRabbit, World world )
    {
        return world.fireAt( oldRabbit.x, oldRabbit.y );
    }

    @Override
    public State newState(
        BehaviourTools t, boolean triggered
        )
    {
        if ( triggered )
        {
            if ( t.oldRabbit.onSlope )
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
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        switch ( state )
        {
        case RABBIT_BURNING:
        case RABBIT_BURNING_ON_SLOPE:
        {
            world.changes.killRabbit( oldRabbit );
            return true;
        }
        default:
        {
            return false;
        }
        }
    }
}
