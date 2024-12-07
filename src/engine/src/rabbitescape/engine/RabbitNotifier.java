package rabbitescape.engine;

public interface RabbitNotifier
{
    public void registerObserver( RabbitObserver observer, ObservableSubject observableSubject );

    public void removeObserver( RabbitObserver observer, ObservableSubject observableSubject );

    public void notifyBirth(ObservableSubject observableSubject, BehaviourExecutor behaviourExecutor);

    public void notifyDeath(BehaviourExecutor behaviourExecutor);

    public void notifyExiting(BehaviourExecutor behaviourExecutor);
}
