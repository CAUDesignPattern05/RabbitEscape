package rabbitescape.engine.factory;

import rabbitescape.engine.Entrance;
import rabbitescape.engine.Exit;
import rabbitescape.engine.Fire;
import rabbitescape.engine.Pipe;
import rabbitescape.engine.Thing;
import rabbitescape.engine.factory.Factory;
import rabbitescape.engine.util.VariantGenerator;

public class ThingFactory implements Factory<Thing> {
    @Override
    public Thing create(char c, int x, int y, Object... args) {
        VariantGenerator variantGen = (VariantGenerator) args[0];

        switch (c) {
            case 'Q': return new Entrance(x, y);
            case 'O': return new Exit(x, y);
            case 'A': return new Fire(x, y, variantGen.next(4));
            case 'P': return new Pipe(x, y);
            default: throw new IllegalArgumentException("Unknown thing character: " + c);
        }
    }
}
