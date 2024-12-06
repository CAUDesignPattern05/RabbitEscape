package rabbitescape.engine.behaviours.deathBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;
import rabbitescape.engine.behaviours.BehaviourHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

public class DeathHandler extends BehaviourHandler
{
    private final Behaviour burning;
    private final Behaviour drowning;
    private final Behaviour exiting;
    private final Behaviour outOfBounding;

    public DeathHandler() {
        burning = new Burning(this);
        drowning = new Drowning(this);
        exiting = new Exiting(this);
        outOfBounding = new OutOfBounding(this);
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
