package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.BehaviourHandler;
import sun.awt.geom.Crossings;

import java.util.HashMap;
import java.util.Map;

public abstract class BehaviorExecutor extends Thing implements Comparable<BehaviorExecutor>
{
    private Direction direction;
    private BehaviourHandler behaviourHandler;
    private int index;
    private boolean onSlope;

    public BehaviorExecutor(int x, int y, Direction dir) {
        super( x, y, RABBIT_WALKING_LEFT );
        this.direction = dir;
        this.behaviourHandler = new BehaviourHandler();
        this.onSlope = false;
    }

    @Override
    public void calcNewState(World world) {
        BehaviourTools tool = new BehaviourTools(this, world);

        Token item = tool.pickUpToken();
        if (item != null) behaviourHandler.setItem(item);
        behaviourHandler.setBehaviour();

        State newState = behaviourHandler.newState(tool);
        if (newState != null) state = newState;
    }

    @Override
    public abstract void step( World world );

    @Override
    public Map<String, String> saveState(boolean runtimeMeta) {
        Map<String, String> ret = new HashMap<String, String>();
        if (!runtimeMeta) return ret;

        BehaviourState.addToStateIfGtZero( ret, "index", index );
        BehaviourState.addToStateIfTrue( ret, "onSlope", onSlope );

        behaviourHandler.saveState(ret);
        return ret;
    }

    @Override
    public void restoreFromState( Map<String, String> state ) {
        index = BehaviourState.restoreFromState( state, "index", -1 );

        onSlope = BehaviourState.restoreFromState(
            state, "onSlope", false
        );

        behaviourHandler.restoreFromState(state);
    }

    @Override
    public String overlayText() {
        if (direction == Direction.LEFT) {
            return String.format("<[%d] ", index);
        } else if (direction == Direction.RIGHT) {
            return String.format(" [%d]>", index);
        } else {
            throw new RuntimeException( "Rabbit should only be left or right");
        }
    }

    @Override
    public int compareTo(BehaviorExecutor r) {
        return this.index - r.index;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BehaviorExecutor)) {
            return false;
        }
        return ((BehaviorExecutor) o).index == index;
    }

    @Override
    public int hashCode() { return index; }

    public void setIndex( int index ) {
        this.index = index;
    }

    public Direction getDirection() { return direction; }

    public boolean isOnSlope() { return onSlope; }
}
