package rabbitescape.engine;

public interface RabbitNotifier
{
    public void registerObserver( RabbitObserver observer, BehaviourExecutor behaviourExecutor );

    public void removeObserver( RabbitObserver observer, BehaviourExecutor behaviourExecutor );

    public void notifyBirth(BehaviourExecutor behaviourExecutor);

    public void notifyDeath(BehaviourExecutor behaviourExecutor);

    public void notifyExiting(BehaviourExecutor behaviourExecutor);
}
