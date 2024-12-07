package rabbitescape.engine.behaviours;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;


public abstract class Behaviour implements RabbitNotifier
{
    /**
     * Subclasses examine the rabbit's situation using BehaviourTools and
     * return the state (see ChangeDescription) for the next time step.
     * This method may return null indicating that a different Behaviour
     * must take over.
     * Note that the state determines the animation used.
     */
    public abstract State newState( BehaviourTools t );

    /**
     * Move the rabbit in the world. Kill it, or record its safe exit.
     */
    public abstract void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    );

    /**
     * Examine the rabbit's situation and return true if this Behaviour must
     * take control.
     */

    public abstract void saveState( Map<String, String> saveState );

    public abstract void restoreFromState( Map<String, String> saveState );

    public abstract void clearMemberVariables();



    @Override
    public void registerObserver( RabbitObserver observer, BehaviourExecutor behaviourExecutor)
    {
        ArrayList<RabbitObserver> observers = behaviourExecutor.getObservers();
        observers.add( observer );
        behaviourExecutor.setObservers( observers );
    }

    @Override
    public void removeObserver( RabbitObserver observer, BehaviourExecutor behaviourExecutor)
    {
        ArrayList<RabbitObserver> observers = behaviourExecutor.getObservers();
        observers.remove( observer );
        behaviourExecutor.setObservers( observers );
    }

    @Override
    public void notifyDeath(BehaviourExecutor behaviourExecutor)
    {
        ArrayList<RabbitObserver> observers = behaviourExecutor.getObservers();
        for ( RabbitObserver observer : observers )
        {
            observer.updateDeath( behaviourExecutor );
        }
    }

    @Override
    public void notifyExiting(BehaviourExecutor behaviourExecutor)
    {
        ArrayList<RabbitObserver> observers = behaviourExecutor.getObservers();
        for ( RabbitObserver observer : observers )
        {
            observer.updateExiting( behaviourExecutor );
        }
    }
}
