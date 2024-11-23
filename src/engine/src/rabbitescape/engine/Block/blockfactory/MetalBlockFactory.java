package rabbitescape.engine.blockfactory;

import rabbitescape.engine.Block;

public class MetalBlockFactory implements BlockFactory {
    @Override
    public Block createBlock(int x, int y, int variant) {
        return new Block(x, y, Block.Material.METAL, Block.Shape.FLAT, variant);
    }
}