package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Block.Material.*;
import static rabbitescape.engine.Block.Shape.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Behaviour;

public class Bridging extends Action
{
    enum BridgeType
    {
        ALONG,
        UP,
        DOWN_UP
    }

    private int smallSteps;
    private int bigSteps;
    private BridgeType bridgeType = BridgeType.ALONG;

    public Bridging(ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState( BehaviourTools t)
    {
        State ret = bridgingState( t, bigSteps, smallSteps, bridgeType );
        if ( ret == null ) {
            bigSteps = 0;
        }

        if ( bigSteps <= 0 ) {
            smallSteps = 0;
            actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
            return actionHandler.newState(t);   // Finished bridging
        } else {
            return ret;
        }
    }

    private State bridgingState(
        BehaviourTools t,
        int bs,
        int ss,
        BridgeType bt
    )
    {
        Block hereBlock = t.blockHere();

        BehaviourExecutor behaviourExecutor = t.behaviourExecutor;
        World world = t.world;

        if ( startingIntoToWall( world, behaviourExecutor, bs ) )
        {
            return stateIntoWall( t, behaviourExecutor, world, ss );
        }

        boolean slopeUp = isSlopeUp( behaviourExecutor, hereBlock );
        int nx = nextX( behaviourExecutor );
        int ny = nextY( behaviourExecutor, slopeUp );

        Block nextBlock = world.getBlockAt( nx, ny );

        Block belowNextBlock = world.getBlockAt( nx, behaviourExecutor.y );
        Block twoAboveHereBlock = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y - 2 );
        Block aboveNextBlock = world.getBlockAt( nx, ny - 1 );

        if (
            (
                   // Something in the way
                   nextBlock != null
                && nextBlock.riseDir() != behaviourExecutor.getDirection()
            ) || (
                   Blocking.blockerAt( t.world, nx, ny )
            ) || (
                   // Clip land
                   belowNextBlock != null
                && belowNextBlock.riseDir() != behaviourExecutor.getDirection()
            ) || (
                    // Bang head next
                    aboveNextBlock != null
                 && BehaviourTools.isSolid( aboveNextBlock )
            ) || (
                    // Bang head here, mid-build
                    bs < 3
                 && BehaviourTools.s_isFlat( twoAboveHereBlock )
            )
        )
        {
            actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
            return actionHandler.newState(t); // Stop bridging
        }

        boolean slopeDown = (
               ( hereBlock != null )
            && ( hereBlock.riseDir() == Direction.opposite( behaviourExecutor.getDirection() ) )
        );

        switch( ss )
        {
            case 3:
            {
                if ( slopeUp )
                {
                    return t.rl(
                        RABBIT_BRIDGING_UP_RIGHT_1,
                        RABBIT_BRIDGING_UP_LEFT_1
                    );
                }
                else if ( slopeDown )
                {
                    return t.rl(
                        RABBIT_BRIDGING_DOWN_UP_RIGHT_1,
                        RABBIT_BRIDGING_DOWN_UP_LEFT_1
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_RIGHT_1,
                        RABBIT_BRIDGING_LEFT_1
                    );
                }
            }
            case 2:
            {
                switch( bt )
                {
                    case ALONG:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_RIGHT_2,
                            RABBIT_BRIDGING_LEFT_2
                        );
                    }
                    case UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_UP_RIGHT_2,
                            RABBIT_BRIDGING_UP_LEFT_2
                        );
                    }
                    case DOWN_UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_DOWN_UP_RIGHT_2,
                            RABBIT_BRIDGING_DOWN_UP_LEFT_2
                        );
                    }
                    default:
                    {
                        throw new AssertionError(
                            "Unexpected bridge type: " + bt );
                    }
                }
            }
            case 1:
            {
                switch( bt )
                {
                    case ALONG:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_RIGHT_3,
                            RABBIT_BRIDGING_LEFT_3
                        );
                    }
                    case UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_UP_RIGHT_3,
                            RABBIT_BRIDGING_UP_LEFT_3
                        );
                    }
                    case DOWN_UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_DOWN_UP_RIGHT_3,
                            RABBIT_BRIDGING_DOWN_UP_LEFT_3
                        );
                    }
                    default:
                    {
                        throw new AssertionError(
                            "Unexpected bridge type: " + bt );
                    }
                }
            }
            default:
            {
                actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
                return actionHandler.newState(t); // Stop bridging
            }
        }
    }

    private State stateIntoWall(
        BehaviourTools t, BehaviourExecutor behaviourExecutor, World world, int ss )
    {
        // We are facing a wall.  This makes us a bit keener to
        // bridge.
        Block thisBlock = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y );

        boolean slopeUp = isSlopeUp( behaviourExecutor, thisBlock );
        int bx = behindX( behaviourExecutor );
        int ny = nextY( behaviourExecutor, slopeUp );

        // Don't bridge if there is no block behind us (we're not in a hole)
        if ( isSlope( thisBlock ) && world.getBlockAt( bx, ny ) == null )
        {
            return null;
        }

        switch( ss )
        {
            case 3:
            {
                if ( isSlope( thisBlock ) )
                {
                    // Special behaviour where we bridge higher up because we
                    // are already on top of a slope.

                    Block twoAbove = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y - 2 );

                    if ( twoAbove == null || twoAbove.isBridge() ) {
                        return t.rl(
                            RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_1,
                            RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1
                        );
                    }
                    else
                    {
                        // We would hit our head, so don't bridge.
                        return null;
                    }
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_1,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_1
                    );
                }
            }
            case 2:
            {
                if ( isSlope( thisBlock ) )
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_2,
                        RABBIT_BRIDGING_IN_CORNER_UP_LEFT_2
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_2,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_2
                    );
                }
            }
            case 1:
            {
                if ( isSlope( thisBlock ) )
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_3,
                        RABBIT_BRIDGING_IN_CORNER_UP_LEFT_3
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_3,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_3
                    );
                }
            }
            default:
            {
                actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
                return actionHandler.newState(t);
            }
        }
    }

    private static boolean startingIntoToWall(
        World world,
        BehaviourExecutor behaviourExecutor,
        int bs
    )
    {
        Block hereBlock = world.getBlockAt( behaviourExecutor.x, behaviourExecutor.y );

        boolean slopeUp = isSlopeUp( behaviourExecutor, hereBlock );
        int nx = nextX( behaviourExecutor );
        int ny = nextY( behaviourExecutor, slopeUp );

        Block nextBlock = world.getBlockAt( nx, ny );

        return (
           bs == 3
        )
        &&
        (
               nextBlock != null
            &&
            (
                   nextBlock.riseDir() != behaviourExecutor.getDirection()
                || nextBlock.shape == FLAT
            )
         );
    }

    private static boolean isSlopeUp( BehaviourExecutor behaviourExecutor, Block hereBlock )
    {
        return ( hereBlock != null )
          && ( hereBlock.riseDir() == behaviourExecutor.getDirection() );
    }

    private static int nextY( BehaviourExecutor behaviourExecutor, boolean slopeUp )
    {
        int ret = behaviourExecutor.y;
        ret += slopeUp ? -1 : 0;
        return ret;
    }

    private static int nextX( BehaviourExecutor behaviourExecutor )
    {
        int ret = behaviourExecutor.x;
        ret += behaviourExecutor.getDirection() == Direction.RIGHT ? 1 : -1;
        return ret;
    }

    private static int behindX( BehaviourExecutor behaviourExecutor )
    {
        int ret = behaviourExecutor.x;
        ret += behaviourExecutor.getDirection() == Direction.RIGHT ? -1 : 1;
        return ret;
    }

    private void nextStep()
    {
        --smallSteps;
        if ( smallSteps <= 0 )
        {
            --bigSteps;
            smallSteps = 3;
        }
    }

    private static boolean isSlope( Block thisBlock )
    {
        return ( thisBlock != null && thisBlock.shape != FLAT );
    }

    @Override
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        boolean handled = moveRabbit( world, behaviourExecutor, state );

        if ( handled )
        {
            // If we're bridging, we're on a slope
            behaviourExecutor.setOnSlope(true);
        }
    }

    private boolean moveRabbit( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        switch ( state )
        {
            case RABBIT_BRIDGING_RIGHT_1:
            case RABBIT_BRIDGING_RIGHT_2:
            case RABBIT_BRIDGING_LEFT_1:
            case RABBIT_BRIDGING_LEFT_2:
            {
                bridgeType = BridgeType.ALONG;
                return true;
            }
            case RABBIT_BRIDGING_UP_RIGHT_1:
            case RABBIT_BRIDGING_UP_RIGHT_2:
            case RABBIT_BRIDGING_UP_LEFT_1:
            case RABBIT_BRIDGING_UP_LEFT_2:
            {
                bridgeType = BridgeType.UP;
                return true;
            }
            case RABBIT_BRIDGING_DOWN_UP_RIGHT_1:
            case RABBIT_BRIDGING_DOWN_UP_RIGHT_2:
            case RABBIT_BRIDGING_DOWN_UP_LEFT_1:
            case RABBIT_BRIDGING_DOWN_UP_LEFT_2:
            {
                bridgeType = BridgeType.DOWN_UP;
                return true;
            }
            case RABBIT_BRIDGING_IN_CORNER_RIGHT_1:
            case RABBIT_BRIDGING_IN_CORNER_LEFT_1:
            case RABBIT_BRIDGING_IN_CORNER_RIGHT_2:
            case RABBIT_BRIDGING_IN_CORNER_LEFT_2:
            case RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_1:
            case RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1:
            case RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_2:
            case RABBIT_BRIDGING_IN_CORNER_UP_LEFT_2:
            {
                bridgeType = BridgeType.ALONG;
                return true;
            }
            case RABBIT_BRIDGING_RIGHT_3:
            case RABBIT_BRIDGING_DOWN_UP_RIGHT_3:
            {
                behaviourExecutor.x++;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_RIGHT,
                        0
                    )
                );

                return true;
            }
            case RABBIT_BRIDGING_LEFT_3:
            case RABBIT_BRIDGING_DOWN_UP_LEFT_3:
            {
                behaviourExecutor.x--;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_LEFT,
                        0
                    )
                );

                return true;
            }
            case RABBIT_BRIDGING_UP_RIGHT_3:
            {
                behaviourExecutor.x++;
                behaviourExecutor.y--;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_RIGHT,
                        0
                    )
                );

                return true;
            }
            case RABBIT_BRIDGING_UP_LEFT_3:
            {
                behaviourExecutor.x--;
                behaviourExecutor.y--;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_LEFT,
                        0
                    )
                );

                return true;
            }
            case RABBIT_BRIDGING_IN_CORNER_RIGHT_3:
            {
                behaviourExecutor.setOnSlope(true);
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_RIGHT,
                        0
                    )
                );
                return true;
            }
            case RABBIT_BRIDGING_IN_CORNER_LEFT_3:
            {
                behaviourExecutor.setOnSlope(true);
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_LEFT,
                        0
                    )
                );
                return true;
            }
            case RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_3:
            {
                behaviourExecutor.setOnSlope(true);
                behaviourExecutor.y--;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_RIGHT,
                        0
                    )
                );
                return true;
            }
            case RABBIT_BRIDGING_IN_CORNER_UP_LEFT_3:
            {
                behaviourExecutor.setOnSlope(true);
                behaviourExecutor.y--;
                world.changes.addBlock(
                    new Block(
                        behaviourExecutor.x,
                        behaviourExecutor.y,
                        EARTH,
                        BRIDGE_UP_LEFT,
                        0
                    )
                );
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
        BehaviourState.addToStateIfNotDefault(
            saveState,
            "Bridging.bridgeType",
            bridgeType.toString(),
            BridgeType.ALONG.toString()
        );

        BehaviourState.addToStateIfGtZero(
            saveState, "Bridging.bigSteps", bigSteps
        );

        BehaviourState.addToStateIfGtZero(
            saveState, "Bridging.smallSteps", smallSteps
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        bridgeType = BridgeType.valueOf(
            BehaviourState.restoreFromState(
                saveState, "Bridging.bridgeType", bridgeType.toString()
            )
        );

        bigSteps = BehaviourState.restoreFromState(
            saveState, "Bridging.bigSteps", bigSteps
        );

        smallSteps = BehaviourState.restoreFromState(
            saveState, "Bridging.smallSteps", smallSteps
        );

        if ( smallSteps > 0 )
        {
            ++smallSteps;
        }
    }

    @Override
    public void clearMemberVariables() {
        smallSteps = 3;
        bigSteps = 3;
    }
}
