package rabbitescape.engine.factory;

import rabbitescape.engine.Rabbit;

import static rabbitescape.engine.Direction.*;

public class RabbitFactory implements Factory<Rabbit> {
    @Override
    public Rabbit create(char c, int x, int y, Object... args) {
        switch (c) {
            case 'r': return new Rabbit(x, y, RIGHT, Rabbit.Type.RABBIT);
            case 'j': return new Rabbit(x, y, LEFT, Rabbit.Type.RABBIT);
            case 't': return new Rabbit(x, y, RIGHT, Rabbit.Type.RABBOT);
            case 'y': return new Rabbit(x, y, LEFT, Rabbit.Type.RABBOT);
            default: throw new IllegalArgumentException("Unknown rabbit character: " + c);
        }
    }
}
