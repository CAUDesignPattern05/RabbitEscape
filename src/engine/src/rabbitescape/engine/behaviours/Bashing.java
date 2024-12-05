package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Bashing extends Behaviour
{
    private int stepsOfBashing;

    public Bashing(BehaviourHandler behaviourHandler) { super(behaviourHandler); }

    @Override
    public State newState(BehaviourTools t) {
        if (t.isOnUpSlope() && t.blockAboveNext() != null ) {
            if (t.blockAboveNext().material == Block.Material.METAL) {
                stepsOfBashing = 0;
                return t.rl( RABBIT_BASHING_USELESSLY_RIGHT_UP, RABBIT_BASHING_USELESSLY_LEFT_UP );
            } else {
                stepsOfBashing = 2;
                return t.rl( RABBIT_BASHING_UP_RIGHT, RABBIT_BASHING_UP_LEFT );
            }
        } else if (t.isOnUpSlope() && t.blockAboveNext() == null) {
            return t.rl( RABBIT_BASHING_USELESSLY_RIGHT_UP, RABBIT_BASHING_USELESSLY_LEFT_UP );
        } else if ( t.blockNext() != null ) {
            if ( t.blockNext().material == Block.Material.METAL ) {
                stepsOfBashing = 0;
                return t.rl( RABBIT_BASHING_USELESSLY_RIGHT, RABBIT_BASHING_USELESSLY_LEFT );
            } else {
                stepsOfBashing = 2;
                return t.rl( RABBIT_BASHING_RIGHT, RABBIT_BASHING_LEFT );
            }
        } else {
            return t.rl( RABBIT_BASHING_USELESSLY_RIGHT, RABBIT_BASHING_USELESSLY_LEFT );
        }
    }

    @Override
    public void behave( World world, BehaviorExecutor behaviorExecutor, State state )
    {
        switch ( state ) {
            case RABBIT_BASHING_RIGHT:
            case RABBIT_BASHING_LEFT: {
                world.changes.removeBlockAt( destX( behaviorExecutor ), behaviorExecutor.y );
                break;
            }
            case RABBIT_BASHING_UP_RIGHT:
            case RABBIT_BASHING_UP_LEFT: {
                world.changes.removeBlockAt( destX( behaviorExecutor ), behaviorExecutor.y - 1 );
                behaviorExecutor.y -= 1;
                break;
            }
            case RABBIT_BASHING_USELESSLY_RIGHT_UP:
            case RABBIT_BASHING_USELESSLY_LEFT_UP: {
                behaviorExecutor.y -= 1;
                break;
            }
            default:
                break;
        }
        this.behaviourHandler.restoreItem();
    }

    private int destX( BehaviorExecutor behaviorExecutor )
    {
        return ( behaviorExecutor.getDirection() == RIGHT ) ? behaviorExecutor.x + 1 : behaviorExecutor.x - 1;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfGtZero(
            saveState, "Bashing.stepsOfBashing", stepsOfBashing
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        stepsOfBashing = BehaviourState.restoreFromState(
            saveState, "Bashing.stepsOfBashing", stepsOfBashing
        );

        if ( stepsOfBashing > 0 )
        {
            ++stepsOfBashing;
        }
    }
}
