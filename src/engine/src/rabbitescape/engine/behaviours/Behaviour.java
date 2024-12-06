package rabbitescape.engine.behaviours;

import java.util.Map;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public abstract class Behaviour
{
    /**
     * Subclasses examine the rabbit's situation using BehaviourTools and
     * return the state (see ChangeDescription) for the next time step.
     * This method may return null indicating that a different Behaviour
     * must take over.
     * Note that the state determines the animation used.
     */
    public State newState(BehaviourTools t);

    /**
     * Move the rabbit in the world. Kill it, or record its safe exit.
     */
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state );

    /**
     * Examine the rabbit's situation and return true if this Behaviour must
     * take control.
     */

    public void saveState( Map<String, String> saveState );

    public void restoreFromState( Map<String, String> saveState );

    public void clearMemberVariables();
}
