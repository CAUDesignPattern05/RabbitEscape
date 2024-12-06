package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public abstract class BehaviourHandler {
    private BehaviourHandler nextHandler;

    public abstract State newState(BehaviourTools tool);
    public abstract void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

    public BehaviourHandler setNextHandler( BehaviourHandler nextHandler )
    {
        this.nextHandler = nextHandler;
    }
}
