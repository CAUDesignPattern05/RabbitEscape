package rabbitescape.engine;

import java.util.Map;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.BehaviourHandler;

public abstract class Behaviour
{

    BehaviourHandler behaviourHandler;

    public Behaviour(BehaviourHandler behaviourHandler) {
        this.behaviourHandler = behaviourHandler;
    }

    /**
     * Subclasses examine the rabbit's situation using BehaviourTools and
     * return the state (see ChangeDescription) for the next time step.
     * This method may return null indicating that a different Behaviour
     * must take over.
     *
     * Note that the state determines the animation used.
     */
    public abstract State newState( BehaviourTools t);

    /**
     * Move the rabbit in the world. Kill it, or record its safe exit.
     *
     * @return true if this behaviour has done all the work needed for
     *         this time step
     */
    public abstract boolean behave( World world, OldRabbit oldRabbit, State state );

    /**
     * Examine the rabbit's situation and return true if this Behaviour must
     * take control.
     */

    public void saveState( Map<String, String> saveState ) {}

    public void restoreFromState( Map<String, String> saveState ) {}

    public void clearMemberVariables() {}
}
