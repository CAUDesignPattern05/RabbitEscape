package rabbitescape.engine.factory;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.Rabbot;

import static rabbitescape.engine.Direction.*;

public class RabbitFactory implements Factory<BehaviourExecutor> {
    @Override
    public BehaviourExecutor create(char c, int x, int y, Object... args) {
        switch (c) {
            case 'r': return new Rabbit(x, y, RIGHT);
            case 'j': return new Rabbit(x, y, LEFT);
            case 't': return new Rabbot(x, y, RIGHT);
            case 'y': return new Rabbot(x, y, LEFT);
            default: throw new IllegalArgumentException("Unknown rabbit character: " + c);
        }
    }
}
