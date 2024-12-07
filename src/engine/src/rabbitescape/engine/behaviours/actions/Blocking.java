package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Behaviour;

public class Blocking extends Action
{
    private boolean abilityActive;

    public Blocking(ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState(BehaviourTools t) {
        Block here = t.blockHere();
        if( BehaviourTools.isRightRiseSlope( here ) ) {
            return RABBIT_BLOCKING_RISE_RIGHT;
        } else if ( BehaviourTools.isLeftRiseSlope( here ) ) {
            return RABBIT_BLOCKING_RISE_LEFT;
        } else {
            return RABBIT_BLOCKING;
        }
    }


    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state ) {
        return true;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Blocking.abilityActive", abilityActive
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        abilityActive = BehaviourState.restoreFromState(
            saveState, "Blocking.abilityActive", abilityActive
        );
    }

    public static boolean blockerAt( World world, int nextX, int nextY )
    {
        BehaviourExecutor[] behaviourExecutors = world.getRabbitsAt( nextX, nextY );
        for ( BehaviourExecutor r : behaviourExecutors )
        {
            State state = r.state;
            if (state == RABBIT_BLOCKING || state == RABBIT_BLOCKING_RISE_RIGHT || state == RABBIT_BLOCKING_RISE_LEFT)
                return true;
        }
        return false;
    }

    public static boolean isBlocking( State s )
    {
        switch ( s ) {
        case RABBIT_BLOCKING:
        case RABBIT_BLOCKING_RISE_RIGHT:
        case RABBIT_BLOCKING_RISE_LEFT:
            return true;
        default:
            return false;
        }
    }
}
