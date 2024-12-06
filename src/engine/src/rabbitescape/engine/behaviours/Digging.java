package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Token.Type.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Digging extends Behaviour
{
    private int stepsOfDigging;

    public Digging(BehaviourHandler behaviourHandler) { super(behaviourHandler); }

    @Override
    public State newState(BehaviourTools t)
    {
        if ( t.behaviourExecutor.state == RABBIT_DIGGING ) {
            stepsOfDigging = 2;
            return RABBIT_DIGGING_2;
        } else if ( t.behaviourExecutor.isOnSlope() && t.blockHere() != null) {
            stepsOfDigging = 1;
            return RABBIT_DIGGING_ON_SLOPE;
        } else if (t.blockBelow() != null) {
            if (t.blockBelow().material == Block.Material.METAL) {
                stepsOfDigging = 0;
                return RABBIT_DIGGING_USELESSLY;
            } else {
                stepsOfDigging = 1;
                return RABBIT_DIGGING;
            }
        } else {
            this.behaviourHandler.setBehaviour(this.behaviourHandler.getWalkingBehaviour());
            return behaviourHandler.newState(t);
        }
    }

    @Override
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_DIGGING: {
                world.changes.removeBlockAt( behaviourExecutor.x, behaviourExecutor.y + 1 );
                ++behaviourExecutor.y;
                break;
            }
            case RABBIT_DIGGING_ON_SLOPE: {
                world.changes.removeBlockAt( behaviourExecutor.x, behaviourExecutor.y );
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_DIGGING_2: {
                behaviourHandler.moveBehave(world, behaviourExecutor, state);
                break;
            }
            case RABBIT_DIGGING_USELESSLY:
            default: {
                this.behaviourHandler.setBehaviour(this.behaviourHandler.getWalkingBehaviour());
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

    @Override
    public void clearMemberVariables() {
        stepsOfDigging = 0;
    }
}
