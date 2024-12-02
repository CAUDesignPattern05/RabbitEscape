package rabbitescape.engine.block;

public class MetalBlock extends Block {
    public MetalBlock(int x, int y, int variant) {
        super(x, y, new MetalMaterial(), new FlatShape(), variant, new StaticBehaviour());
    }
}
