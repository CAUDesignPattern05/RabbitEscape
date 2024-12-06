package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Blocking extends Behaviour
{
    private boolean abilityActive;

    public Blocking(BehaviourHandler behaviourHandler) { super(behaviourHandler); }

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
    public void behave( World world, BehaviourExecutor behaviorexecutor, State state ) {}

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
        BehaviourExecutor[] behaviorexecutors = world.getRabbitsAt( nextX, nextY );
        for ( BehaviourExecutor r : behaviorexecutors )
        {
            State state = r.state;
            if (state == RABBIT_BLOCKING || state == RABBIT_BLOCKING_RISE_RIGHT || state == RABBIT_BLOCKING_RISE_LEFT)
                return true;
        }
        return false;
    }

    static boolean isBlocking( State s )
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
