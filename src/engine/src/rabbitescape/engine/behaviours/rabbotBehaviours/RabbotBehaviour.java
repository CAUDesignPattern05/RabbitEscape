package rabbitescape.engine.behaviours.rabbotBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;

public abstract class RabbotBehaviour extends Behaviour
{
    RabbotHandler rabbotHandler;

    public RabbotBehaviour(RabbotHandler rabbotHandler) {
        this.rabbotHandler = rabbotHandler;
    }

    public abstract State newState( BehaviourTools t );

    public abstract void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

}
