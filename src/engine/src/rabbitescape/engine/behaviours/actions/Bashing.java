package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.BehaviourHandler;

public class Bashing extends Action
{
    private int isBashed;

    public Bashing(ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState(BehaviourTools t) {
        if (isBashed == 1) {
            return actionHandler.newMoveState(t);
        } else if (t.isOnUpSlope() && t.blockAboveNext() != null ) {
            if (t.blockAboveNext().material == Block.Material.METAL) {
                return t.rl( RABBIT_BASHING_USELESSLY_RIGHT_UP, RABBIT_BASHING_USELESSLY_LEFT_UP );
            } else {
                return t.rl( RABBIT_BASHING_UP_RIGHT, RABBIT_BASHING_UP_LEFT );
            }
        } else if (t.isOnUpSlope() && t.blockAboveNext() == null) {
            return t.rl( RABBIT_BASHING_USELESSLY_RIGHT_UP, RABBIT_BASHING_USELESSLY_LEFT_UP );
        } else if ( t.blockNext() != null ) {
            if ( t.blockNext().material == Block.Material.METAL ) {
                return t.rl( RABBIT_BASHING_USELESSLY_RIGHT, RABBIT_BASHING_USELESSLY_LEFT );
            } else {
                return t.rl( RABBIT_BASHING_RIGHT, RABBIT_BASHING_LEFT );
            }
        } else {
            return t.rl( RABBIT_BASHING_USELESSLY_RIGHT, RABBIT_BASHING_USELESSLY_LEFT );
        }
    }

    @Override
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        if (isBashed == 1) {
            actionHandler.moveBehave(world, behaviourExecutor, state);
            isBashed = 0;
        } else {
            switch ( state ) {
                case RABBIT_BASHING_RIGHT:
                case RABBIT_BASHING_LEFT: {
                    world.changes.removeBlockAt( destX( behaviourExecutor ), behaviourExecutor.y );
                    isBashed = 1;
                    break;
                }
                case RABBIT_BASHING_UP_RIGHT:
                case RABBIT_BASHING_UP_LEFT: {
                    world.changes.removeBlockAt( destX( behaviourExecutor ), behaviourExecutor.y - 1 );
                    behaviourExecutor.y -= 1;
                    isBashed = 1;
                    break;
                }
                case RABBIT_BASHING_USELESSLY_RIGHT_UP:
                case RABBIT_BASHING_USELESSLY_LEFT_UP: {
                    behaviourExecutor.y -= 1;
                    this.actionHandler.setBehaviour(this.actionHandler.getWalkingBehaviour());
                    break;
                }
                default:
                    this.actionHandler.setBehaviour(this.actionHandler.getWalkingBehaviour());
                    break;
            }
        }
    }

    private int destX( BehaviourExecutor behaviourExecutor )
    {
        return ( behaviourExecutor.getDirection() == RIGHT ) ? behaviourExecutor.x + 1 : behaviourExecutor.x - 1;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfGtZero(
            saveState, "Bashing.stepsOfBashing", isBashed
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        isBashed = BehaviourState.restoreFromState(
            saveState, "Bashing.stepsOfBashing", isBashed
        );

        if ( isBashed > 0 )
        {
            ++isBashed;
        }
    }

    @Override
    public void clearMemberVariables() {
        isBashed = 0;
    }
}
