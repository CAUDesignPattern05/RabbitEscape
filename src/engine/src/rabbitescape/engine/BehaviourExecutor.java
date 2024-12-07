package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.ActionHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class BehaviourExecutor
    extends Thing implements Comparable<BehaviourExecutor>
{
    private Direction direction;
    protected ActionHandler actionHandler;
    private int index;
    protected int fatalHeight;
    private boolean onSlope;
    private boolean isAffectedByRabbot;

    public BehaviourExecutor(int x, int y, Direction dir) {
        super( x, y, RABBIT_WALKING_LEFT );
        this.direction = dir;
        this.actionHandler = new ActionHandler();
        this.onSlope = false;
        this.isAffectedByRabbot = false;
    }

    @Override
    public void calcNewState(World world) {
        BehaviourTools tool = new BehaviourTools(this, world);

        Token item = tool.pickUpToken();
        if (item != null) actionHandler.setBehaviour(item);

        State newState = actionHandler.newState(tool);
        if (newState != null) this.setState(newState);
    }

    @Override
    public void step( World world ) {
        actionHandler.handleRequest(world, this, this.getState());
    }

    @Override
    public Map<String, String> saveState(boolean runtimeMeta) {
        Map<String, String> ret = new HashMap<String, String>();
        if (!runtimeMeta) return ret;

        BehaviourState.addToStateIfGtZero( ret, "index", index );
        BehaviourState.addToStateIfTrue( ret, "onSlope", onSlope );

        actionHandler.saveState(ret);
        return ret;
    }

    @Override
    public void restoreFromState( Map<String, String> state ) {
        index = BehaviourState.restoreFromState( state, "index", -1 );

        onSlope = BehaviourState.restoreFromState(
            state, "onSlope", false
        );

        actionHandler.restoreFromState(state);
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
    public int compareTo( BehaviourExecutor r) {
        return this.index - r.index;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BehaviourExecutor )) {
            return false;
        }
        return (( BehaviourExecutor ) o).index == index;
    }

    @Override
    public int hashCode() { return index; }

    public int getIndex() { return index; }

    public void setIndex( int index ) {
        this.index = index;
    }

    public Direction getDirection() { return direction; }

    public void setDirection( Direction direction ) { this.direction = direction; }

    public boolean isOnSlope() { return onSlope; }

    public void setOnSlope( boolean onSlope ) { this.onSlope = onSlope; }

    public void setState(State state) { this.state = state; }

    public State getState() { return state; }

    public void setAffectedByRabbot( boolean isAffectedByRabbot) { this.isAffectedByRabbot = isAffectedByRabbot; }

    public boolean getAffectedByRabbot() { return isAffectedByRabbot; }

    public int getFatalHeight() { return fatalHeight; }
}
