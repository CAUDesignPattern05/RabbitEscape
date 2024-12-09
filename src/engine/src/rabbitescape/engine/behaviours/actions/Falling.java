package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Block.Shape.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Falling extends Action {
    public Falling(ActionHandler actionHandler) {
        super(actionHandler);
    }

    private int heightFallen;

    @Override
    public State newState(BehaviourTools t) {
        if (actionHandler.isBrollychutingAbility() && t.blockBelow() == null) {
            actionHandler.setBehaviour(actionHandler.getBrollychutingBehaviour());
            return actionHandler.newState(t);
        } // Brollychuting exchange

        switch (t.behaviourExecutor.state) {
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT:
                return RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2;
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT:
                return RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT_2;
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT:
                return RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT_2;
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT:
                return RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT_2;
        } // part 2 of animation always comes next (Decided dying)

        int fatalHeight = t.getFatalHeight();

        if (heightFallen > fatalHeight &&
                (t.isFlat(t.blockBelow()) || t.isOnSlope())) {
            if (heightFallen % 2 == 0)
                return RABBIT_DYING_OF_FALLING;
            else
                return RABBIT_DYING_OF_FALLING_2;
        } else if (heightFallen + 1 > fatalHeight &&
                (t.isFlat(t.block2Below()) || t.blockBelow() != null)) { // Going to die during step
            if (BehaviourTools.isRightRiseSlope(t.blockBelow()))
                return RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT;
            else if (BehaviourTools.isLeftRiseSlope(t.blockBelow()))
                return RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT;
            else
                return RABBIT_FALLING_1_TO_DEATH;
        } else {
            Block below = t.blockBelow();

            if (below != null) {
                if (t.isUpSlope(below)) {
                    return t.rl(
                            RABBIT_FALLING_1_ONTO_RISE_RIGHT,
                            RABBIT_FALLING_1_ONTO_RISE_LEFT);
                } else { // Must be a slope in the opposite direction
                    return t.rl(
                            RABBIT_FALLING_1_ONTO_LOWER_RIGHT,
                            RABBIT_FALLING_1_ONTO_LOWER_LEFT);
                }
            }

            Block twoBelow = t.block2Below();
            if (twoBelow != null) {
                if (heightFallen + 1 > fatalHeight) {
                    if (BehaviourTools.isRightRiseSlope(twoBelow))
                        return RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT;
                    else if (BehaviourTools.isLeftRiseSlope(twoBelow))
                        return RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT;
                } else if (t.isFlat(twoBelow)) {
                    return RABBIT_FALLING_1;
                } else if (t.isUpSlope(twoBelow)) {
                    return t.rl(
                            RABBIT_FALLING_ONTO_RISE_RIGHT,
                            RABBIT_FALLING_ONTO_RISE_LEFT);
                } else {
                    return t.rl(
                            RABBIT_FALLING_ONTO_LOWER_RIGHT,
                            RABBIT_FALLING_ONTO_LOWER_LEFT);
                }
            }

            Block threeBelow = t.block3Below();
            if (threeBelow != null && t.isFlat(threeBelow)) {
                return t.rl(
                        RABBIT_FALLING_ONTO_LOWER_RIGHT,
                        RABBIT_FALLING_ONTO_LOWER_LEFT);
            }

            return State.RABBIT_FALLING;
        }
    }

    @Override
    public boolean behave(World world, BehaviourExecutor behaviourExecutor, State state) {
        boolean handled = moveRabbit(world, behaviourExecutor, state);

        // Whenever we fall onto a slope, we are on top of it
        Block thisBlock = world.getBlockAt(behaviourExecutor.x, behaviourExecutor.y);
        behaviourExecutor.setOnSlope( thisBlock != null );

        return handled;
    }

    private boolean moveRabbit(World world, BehaviourExecutor behaviourExecutor, State state) {
        switch (state) {
            case RABBIT_DYING_OF_FALLING:
            case RABBIT_DYING_OF_FALLING_2:
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT_2:
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT_2:
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2:
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT_2: {
                notifyDeath(behaviourExecutor);
                return true;
            }
            case RABBIT_FALLING:
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT:
            case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT: {
                heightFallen += 2;
                behaviourExecutor.y = behaviourExecutor.y + 2;
                return false;
            }
            case RABBIT_FALLING_ONTO_LOWER_RIGHT:
            case RABBIT_FALLING_ONTO_LOWER_LEFT:
            case RABBIT_FALLING_ONTO_RISE_RIGHT:
            case RABBIT_FALLING_ONTO_RISE_LEFT: {
                heightFallen += 2;
                behaviourExecutor.y = behaviourExecutor.y + 2;
                actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
                return false;
            }
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT:
            case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT:
            case RABBIT_FALLING_1_TO_DEATH: {
                heightFallen += 1;
                behaviourExecutor.y = behaviourExecutor.y + 1;
                return false;
            }
            case RABBIT_FALLING_1:
            case RABBIT_FALLING_1_ONTO_LOWER_RIGHT:
            case RABBIT_FALLING_1_ONTO_LOWER_LEFT:
            case RABBIT_FALLING_1_ONTO_RISE_RIGHT:
            case RABBIT_FALLING_1_ONTO_RISE_LEFT: {
                heightFallen += 1;
                behaviourExecutor.y = behaviourExecutor.y + 1;
                actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
                return false;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public void saveState(Map<String, String> saveState) {
        BehaviourState.addToStateIfGtZero(
                saveState, "Falling.heightFallen", heightFallen);
    }

    @Override
    public void restoreFromState(Map<String, String> saveState) {
        heightFallen = BehaviourState.restoreFromState(
                saveState, "Falling.heightFallen", heightFallen);
    }

    @Override
    public void clearMemberVariables() {
        heightFallen = 0;
    }
}
