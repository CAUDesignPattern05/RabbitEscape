package rabbitescape.engine;

import java.util.ArrayList;

public abstract class ObservableSubject implements RabbitNotifier
{
    private ArrayList<RabbitObserver> observers;

    public ArrayList<RabbitObserver> getObservers()
    {
        return observers;
    }

    public void setObservers( ArrayList<RabbitObserver> observers )
    {
        this.observers = observers;
    }

    @Override
    public void registerObserver( RabbitObserver observer, ObservableSubject observableSubject )
    {
        ArrayList<RabbitObserver> observers = observableSubject.getObservers();
        observers.add( observer );
        observableSubject.setObservers( observers );
    }

    @Override
    public void removeObserver( RabbitObserver observer, ObservableSubject observableSubject)
    {
        ArrayList<RabbitObserver> observers = observableSubject.getObservers();
        observers.remove( observer );
        observableSubject.setObservers( observers );
    }

    @Override
    public void notifyBirth( ObservableSubject observableSubject, BehaviourExecutor behaviourExecutor )
    {
        ArrayList<RabbitObserver> observers = observableSubject.getObservers();
        for ( RabbitObserver observer : observers )
        {
            observer.updateBirth( behaviourExecutor );
        }
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
