package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Token.Type.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Digging extends Behaviour
{
    int stepsOfDigging;

    public Digging(BehaviourHandler behaviourHandler) { super(behaviourHandler); }

    @Override
    public State newState( BehaviourTools t)
    {
        if ( !triggered && stepsOfDigging == 0 )
        {
            return null;
        }

        t.oldRabbit.possiblyUndoSlopeBashHop( t.world );

        if ( t.oldRabbit.state == RABBIT_DIGGING )
        {
            stepsOfDigging = 1;
            return RABBIT_DIGGING_2;
        }

        if (
               triggered
            || stepsOfDigging > 0
        )
        {
            if ( t.oldRabbit.onSlope && t.blockHere() != null )
            {
                stepsOfDigging = 1;
                return RABBIT_DIGGING_ON_SLOPE;
            }
            else if ( t.blockBelow() != null )
            {
                if ( t.blockBelow().material == Block.Material.METAL )
                {
                    stepsOfDigging = 0;
                    return RABBIT_DIGGING_USELESSLY;
                }
                else
                {
                stepsOfDigging = 2;
                return RABBIT_DIGGING;
                }
            }
        }

        --stepsOfDigging;
        return null;
    }

    @Override
    public boolean behave( World world, OldRabbit oldRabbit, State state )
    {
        switch ( state )
        {
            case RABBIT_DIGGING:
            {
                world.changes.removeBlockAt( oldRabbit.x, oldRabbit.y + 1 );
                ++oldRabbit.y;
                return true;
            }
            case RABBIT_DIGGING_ON_SLOPE:
            {
                world.changes.removeBlockAt( oldRabbit.x, oldRabbit.y );
                oldRabbit.onSlope = false;
                return true;
            }
            case RABBIT_DIGGING_2:
            case RABBIT_DIGGING_USELESSLY:
            {
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfGtZero(
            saveState, "Digging.stepsOfDigging", stepsOfDigging );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        stepsOfDigging = BehaviourState.restoreFromState(
            saveState, "Digging.stepsOfDigging", stepsOfDigging );
    }

    public static boolean isDigging( State state )
    {
        switch ( state )
        {
            case RABBIT_DIGGING:
            case RABBIT_DIGGING_2:
            case RABBIT_DIGGING_ON_SLOPE:
            case RABBIT_DIGGING_USELESSLY:
                return true;
            default:
                return false;
        }
    }

}
