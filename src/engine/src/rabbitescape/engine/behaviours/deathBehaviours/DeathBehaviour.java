package rabbitescape.engine.behaviours.deathBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;

import java.util.Map;

public abstract class DeathBehaviour implements Behaviour
{
    DeathHandler deathHandler;

    public DeathBehaviour(DeathHandler deathHandler) {
        this.deathHandler = deathHandler;
    }


    public abstract State newState( BehaviourTools t );

    public abstract void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

}
