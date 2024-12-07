package rabbitescape.engine.behaviours.deathBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.behaviours.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;
import rabbitescape.engine.World;

public class Burning extends Behaviour
{

    public Burning( ActionHandler actionHandler) { super(behaviourHandler); }

    @Override
    public State newState(BehaviourTools t)
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
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
        case RABBIT_BURNING:
        case RABBIT_BURNING_ON_SLOPE:
        {
            notifyDeath(behaviourExecutor);
            return true;
        }
        default:
        {
            return false;
        }
        }
    }
}
