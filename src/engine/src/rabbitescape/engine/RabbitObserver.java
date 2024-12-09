package rabbitescape.engine;

public interface RabbitObserver
{
    public void updateBirth(BehaviourExecutor behaviourExecutor);

    public void updateDeath( BehaviourExecutor behaviourExecutor);

    public void updateExiting( BehaviourExecutor behaviourExecutor);
}
