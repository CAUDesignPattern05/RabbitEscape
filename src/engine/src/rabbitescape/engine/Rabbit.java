package rabbitescape.engine;

import rabbitescape.engine.behaviours.basicBehaviours.BasicHandler;
import rabbitescape.engine.behaviours.rabbitBehaviours.RabbitHandler;

public class Rabbit extends BehaviourExecutor
{
    public Rabbit(int x, int y, Direction dir) {
        super(x, y, dir);
        setAffectedByRabbot( true );
        BasicHandler deathHandler = new BasicHandler();
        RabbitHandler rabbitHandler = new RabbitHandler();

        deathHandler.setNextHandler( rabbitHandler );
        actionHandler.setNextHandler( deathHandler );
    }
}
