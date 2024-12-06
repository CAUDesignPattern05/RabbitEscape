package rabbitescape.engine.behaviours.rabbotBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;
import rabbitescape.engine.behaviours.BehaviourHandler;

public class RabbotHandler extends BehaviourHandler
{
    private final Behaviour rabbotCrash;
    private final Behaviour rabbotWait;

    public RabbotHandler() {
        rabbotCrash = new RabbotCrash(this);
        rabbotWait = new RabbotWait(this);
    }

    public State newState( BehaviourTools tool ) {

    }

    public void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    ) {

    }
}
