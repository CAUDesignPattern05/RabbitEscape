package rabbitescape.engine.block;

public class EarthBlock extends Block {
    public EarthBlock(int x, int y, int variant) {
        super(x, y, new EarthMaterial(), new FlatShape(), variant, new StaticBehaviour());
    }
}
