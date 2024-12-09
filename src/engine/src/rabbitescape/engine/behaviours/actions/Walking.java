package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.Block.Shape.*;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Walking extends Action
{
    public Walking( ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState( BehaviourTools t )
    {
        if (!t.isOnSlope() && t.blockBelow() == null) {
            actionHandler.setBehaviour(actionHandler.getFallingBehaviour());
            return actionHandler.newState(t); // switching to falling
        } else if ( t.isOnUpSlope() ) {
            Block aboveNext = t.blockAboveNext();
            Block above = t.blockAbove();
            int nextX = t.nextX();
            int nextY = t.behaviourExecutor.y - 1;

            if (actionHandler.isClimbingAbility() && !t.isRoof(above) && t.isWall(aboveNext)) {
                actionHandler.setBehaviour(actionHandler.getClimbingBehaviour());
                return actionHandler.newState(t);
            } else if (t.isWall(aboveNext) || Blocking.blockerAt(t.world, nextX, nextY)
                || t.isRoof(above) || (t.isCresting() && Blocking.blockerAt(t.world, nextX, t.behaviourExecutor.y))) {
                return t.rl(
                    RABBIT_TURNING_RIGHT_TO_LEFT_RISING,
                    RABBIT_TURNING_LEFT_TO_RIGHT_RISING
                );
            } else if ( t.isUpSlope( aboveNext ) ) {
                return t.rl( RABBIT_RISING_RIGHT_CONTINUE, RABBIT_RISING_LEFT_CONTINUE );
            } else if ( t.isDownSlope( t.blockNext() ) ) {
                return t.rl( RABBIT_RISING_AND_LOWERING_RIGHT, RABBIT_RISING_AND_LOWERING_LEFT );
            } else {
                return t.rl( RABBIT_RISING_RIGHT_END, RABBIT_RISING_LEFT_END );
            }
        } else if (actionHandler.isClimbingAbility() && !t.isRoof(t.blockAbove()) && t.isWall(t.blockNext())) {
            actionHandler.setBehaviour(actionHandler.getClimbingBehaviour()); // switching to climbing
            return actionHandler.newState(t);
        } else if (t.isOnDownSlope()) {
            int nextX = t.nextX();
            int nextY = t.behaviourExecutor.y + 1;
            Block next = t.blockNext();
            Block belowNext = t.blockBelowNext();

            if (t.isWall( next ) || Blocking.blockerAt( t.world, nextX, nextY )
                || ( t.isValleying() && Blocking.blockerAt( t.world, nextX, t.behaviourExecutor.y ) ) ) {
                return t.rl(
                    RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING,
                    RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING
                );
            } else if ( t.isUpSlope( next ) ) {
                return t.rl( RABBIT_LOWERING_AND_RISING_RIGHT, RABBIT_LOWERING_AND_RISING_LEFT );
            } else if ( t.isDownSlope( belowNext ) ) {
                return t.rl( RABBIT_LOWERING_RIGHT_CONTINUE, RABBIT_LOWERING_LEFT_CONTINUE );
            } else {
                if ( Blocking.blockerAt( t.world, nextX, t.behaviourExecutor.y ) ) {
                    return t.rl( RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING, RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING );
                } else {
                    return t.rl( RABBIT_LOWERING_RIGHT_END, RABBIT_LOWERING_LEFT_END );
                }
            }
        } else { // On flat ground now
            int nextX = t.nextX();
            int nextY = t.behaviourExecutor.y;
            Block next = t.blockNext();

            if (t.isWall( next ) || Blocking.blockerAt( t.world, nextX, nextY )) {
                return t.rl( RABBIT_TURNING_RIGHT_TO_LEFT, RABBIT_TURNING_LEFT_TO_RIGHT );
            } else if ( t.isUpSlope( next ) ) {
                return t.rl( RABBIT_RISING_RIGHT_START, RABBIT_RISING_LEFT_START );
            } else if ( t.isDownSlope( t.blockBelowNext() ) ) {
                if ( Blocking.blockerAt( t.world, nextX, t.behaviourExecutor.y + 1 ) ) {
                    return t.rl( RABBIT_TURNING_RIGHT_TO_LEFT, RABBIT_TURNING_LEFT_TO_RIGHT );
                } else {
                    return t.rl( RABBIT_LOWERING_RIGHT_START, RABBIT_LOWERING_LEFT_START );
                }
            } else {
                return t.rl( RABBIT_WALKING_RIGHT, RABBIT_WALKING_LEFT );
            }
        }
    }

    @Override
    @SuppressWarnings("fallthrough")
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_WALKING_LEFT:
            {
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_WALKING_RIGHT:
            {
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_LOWERING_LEFT_END:
            {
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_RISING_LEFT_START:
            case RABBIT_LOWERING_AND_RISING_LEFT:
            case RABBIT_RISING_AND_LOWERING_LEFT:
            {
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_LOWERING_RIGHT_END:
            {
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_RISING_RIGHT_START:
            case RABBIT_LOWERING_AND_RISING_RIGHT:
            case RABBIT_RISING_AND_LOWERING_RIGHT:
            {
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_RISING_LEFT_END:
            {
                --behaviourExecutor.y;
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_RISING_LEFT_CONTINUE:
            {
                --behaviourExecutor.y;
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_RISING_RIGHT_END:
            {
                --behaviourExecutor.y;
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(false);
                break;
            }
            case RABBIT_RISING_RIGHT_CONTINUE:
            {
                --behaviourExecutor.y;
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_LOWERING_LEFT_CONTINUE:
            case RABBIT_LOWERING_LEFT_START:
            {
                ++behaviourExecutor.y;
                --behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_LOWERING_RIGHT_CONTINUE:
            case RABBIT_LOWERING_RIGHT_START:
            {
                ++behaviourExecutor.y;
                ++behaviourExecutor.x;
                behaviourExecutor.setOnSlope(true);
                break;
            }
            case RABBIT_TURNING_LEFT_TO_RIGHT:
                behaviourExecutor.setOnSlope(false); // Intentional fall-through
            case RABBIT_TURNING_LEFT_TO_RIGHT_RISING:
            case RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING:
            {
                behaviourExecutor.setDirection(RIGHT);
                checkJumpOntoSlope( world, behaviourExecutor );
                break;
            }
            case RABBIT_TURNING_RIGHT_TO_LEFT:
                behaviourExecutor.setOnSlope(false); // Intentional fall-through
            case RABBIT_TURNING_RIGHT_TO_LEFT_RISING:
            case RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING:
            {
                behaviourExecutor.setDirection(LEFT);
                checkJumpOntoSlope( world, behaviourExecutor );
                break;
            }
            default:
            {
                throw new AssertionError(
                    "Should have handled all states in Walking or before,"
                    + " but we are in state " + state.name()
                );
            }
        }

        return false;
    }

    /**
     * If we turn around near a slope, we jump onto it
     */
    private void checkJumpOntoSlope( World world, BehaviourExecutor behaviourExecutor )
    {
        Block thisBlock = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y );
        if ( isBridge( thisBlock ) )
        {
            Block aboveBlock = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y - 1 );
            if ( behaviourExecutor.isOnSlope() && isBridge( aboveBlock ) )
            {
                behaviourExecutor.y--;
            }
            else
            {
                behaviourExecutor.setOnSlope(true);
            }
        }
    }

    private boolean isBridge( Block block )
    {
        return (
               block != null
            && (
                   block.shape == BRIDGE_UP_LEFT
                || block.shape == BRIDGE_UP_RIGHT
            )
        );
    }
}
