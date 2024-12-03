package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Token.Type.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Blocking extends Behaviour
{
    public boolean abilityActive = false;

    public Blocking(BehaviourHandler behaviourHandler) { super(behaviourHandler); }

    @Override
    public State newState( BehaviourTools t)
    {
        if ( abilityActive || triggered )
        {
            t.oldRabbit.possiblyUndoSlopeBashHop( t.world );
            abilityActive = true;
            Block here = t.blockHere();
            if( BehaviourTools.isRightRiseSlope( here ) )
            {
                return RABBIT_BLOCKING_RISE_RIGHT;
            }
            else if ( BehaviourTools.isLeftRiseSlope( here ) )
            {
                return RABBIT_BLOCKING_RISE_LEFT;
            }
            else
            {
                return RABBIT_BLOCKING;
            }
        }

        return null;
    }

    @Override
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        return isBlocking( state );
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
        OldRabbit[] oldRabbits = world.getRabbitsAt( nextX, nextY );
        for ( OldRabbit r : oldRabbits )
        {
            if ( isBlocking( r.state ) )
            {
                return true;
            }
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
