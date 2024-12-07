package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.deathBehaviours.DeathBehaviour;
import rabbitescape.engine.behaviours.deathBehaviours.DeathHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BehaviourExecutor
    extends Thing implements Comparable<BehaviourExecutor>
{
    private Direction direction;
    protected ActionHandler actionHandler;
    private int index;
    private boolean onSlope;

    public BehaviourExecutor(int x, int y, Direction dir) {
        super( x, y, RABBIT_WALKING_LEFT );
        this.direction = dir;
        this.actionHandler = new ActionHandler();
        this.onSlope = false;
    }

    @Override
    public void calcNewState(World world) {
        BehaviourTools tool = new BehaviourTools(this, world);

        Token item = tool.pickUpToken();
        if (item != null) actionHandler.setBehaviour(item);

        actionHandler.handleRequest( world, this, state );
    }

    @Override
    public void step( World world ) {}

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



    public ArrayList<RabbitObserver> observers;

    public ArrayList<RabbitObserver> getObservers()
    {
        return observers;
    }

    public void setObservers( ArrayList<RabbitObserver> observers )
    {
        this.observers = observers;
    }
}
