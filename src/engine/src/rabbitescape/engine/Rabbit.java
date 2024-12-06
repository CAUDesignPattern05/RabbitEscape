package rabbitescape.engine;

import rabbitescape.engine.behaviours.deathBehaviours.DeathHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

public class Rabbit extends BehaviourExecutor
{
    public Rabbit(int x, int y, Direction dir) {
        super(x, y, dir);
        DeathHandler deathHandler = new DeathHandler();
        actionHandler.setNextHandler( deathHandler );
    }

    @Override
    public void step( World world ) {
    }
}
