package rabbitescape.engine.behaviours.commonBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;

import java.util.Map;

public abstract class CommonBehaviour extends Behaviour
{
    CommonHandler commonHandler;

    public CommonBehaviour( CommonHandler commonHandler) {
        this.commonHandler = commonHandler;
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
