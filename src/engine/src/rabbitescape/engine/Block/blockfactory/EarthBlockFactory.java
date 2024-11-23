package rabbitescape.engine.blockfactory;

import rabbitescape.engine.Block;

public class EarthBlockFactory implements BlockFactory {
    @Override
    public Block createBlock(int x, int y, int variant) {
        return new Block(x, y, Block.Material.EARTH, Block.Shape.FLAT, variant);
    }
}
