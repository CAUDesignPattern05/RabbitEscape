package rabbitescape.engine;

import rabbitescape.engine.behaviours.commonBehaviours.CommonHandler;
import rabbitescape.engine.behaviours.rabbitBehaviours.RabbitHandler;

public class Rabbit extends BehaviourExecutor {
    public Rabbit(int x, int y, Direction dir) {
        super(x, y, dir);
        fatalHeight = 4;

        setAffectedByRabbot(true);
        CommonHandler commonHandler = new CommonHandler();
        RabbitHandler rabbitHandler = new RabbitHandler();

        actionHandler.setNextHandler(commonHandler);
        commonHandler.setNextHandler(rabbitHandler);
    }
}
