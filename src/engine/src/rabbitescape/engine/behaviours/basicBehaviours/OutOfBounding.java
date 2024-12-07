package rabbitescape.engine.behaviours.basicBehaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.behaviours.Behaviour;
import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.config.TapTimer;

public class OutOfBounding extends BasicBehaviour {
    public OutOfBounding(BasicHandler basicHandler) {
        super(basicHandler);
    }

    @Override
    public boolean checkTriggered(BehaviourExecutor behaviourExecutor, World world) {
        return (behaviourExecutor.x < 0
                || behaviourExecutor.x >= world.size.width
                || behaviourExecutor.y < 0
                || behaviourExecutor.y >= world.size.height);
    }

    @Override
    public State newState(BehaviourTools t) {
        boolean triggered = checkTriggered(t.behaviourExecutor, t.world);
        if (triggered) {
            return RABBIT_OUT_OF_BOUNDS;
        }

        return null;
    }

    @Override
    public boolean behave(World world, BehaviourExecutor behaviourExecutor, State state) {
        switch (state) {
            case RABBIT_OUT_OF_BOUNDS: {
                checkMars(world, behaviourExecutor);
                notifyDeath(behaviourExecutor);
                return true;
            }
            default:
                return false;
        }
    }

    /**
     * Test if mars mode has been triggered
     */
    private void checkMars(World world, BehaviourExecutor behaviourExecutor) {
        /*
         * The rabbit must leave the world at the correct coordinates,
         * the index count is likely to only be correct if this is the
         * first rabbit out of the entrance, and it must be the correct
         * level.
         */
        if (12 == behaviourExecutor.x && -1 == behaviourExecutor.y &&
                world.getRabbitIndexCount() == 2 &&
                world.name.equals("Ghost versus pie")) {
            TapTimer.setMars();
        }
    }
}
