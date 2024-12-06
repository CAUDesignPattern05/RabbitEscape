package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public abstract class BehaviourHandler {
    protected BehaviourHandler nextHandler;

    public abstract State newState(BehaviourTools tool);
    public abstract boolean behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

    public void setNextHandler( BehaviourHandler nextHandler )
    {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(World world,
        BehaviourExecutor behaviourExecutor,
        State state);
}
