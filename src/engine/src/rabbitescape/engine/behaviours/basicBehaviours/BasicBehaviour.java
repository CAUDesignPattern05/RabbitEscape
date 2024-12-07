package rabbitescape.engine.behaviours.basicBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;

import java.util.Map;

public abstract class BasicBehaviour extends Behaviour
{
    BasicHandler basicHandler;

    public BasicBehaviour( BasicHandler basicHandler) {
        this.basicHandler = basicHandler;
    }

    public abstract boolean checkTriggered( BehaviourExecutor behaviourExecutor, World world );

    public abstract State newState( BehaviourTools t );

    public abstract boolean behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

    public void saveState( Map<String, String> saveState ) {}

    public void restoreFromState( Map<String, String> saveState ) {}

    public void clearMemberVariables()
    {
    }
}
