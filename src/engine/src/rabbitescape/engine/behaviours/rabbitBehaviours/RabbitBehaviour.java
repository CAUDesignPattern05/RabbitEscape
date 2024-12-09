package rabbitescape.engine.behaviours.rabbitBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;

import java.util.Map;

public abstract class RabbitBehaviour extends Behaviour
{
    RabbitHandler rabbitHandler;

    public  RabbitBehaviour(RabbitHandler rabbitHandler) {
        this.rabbitHandler = rabbitHandler;
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
