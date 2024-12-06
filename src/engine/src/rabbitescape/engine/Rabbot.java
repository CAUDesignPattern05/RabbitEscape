package rabbitescape.engine;

import rabbitescape.engine.behaviours.deathBehaviours.DeathHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

public class Rabbot extends BehaviourExecutor
{
    public Rabbot(int x, int y, Direction dir) {
        super(x, y, dir);
        DeathHandler deathHandler = new DeathHandler();
        RabbotHandler rabbotHandler = new RabbotHandler();

        deathHandler.setNextHandler( rabbotHandler );
        actionHandler.setNextHandler( deathHandler );
    }
}
