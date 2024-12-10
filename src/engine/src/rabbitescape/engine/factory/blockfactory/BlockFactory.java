package rabbitescape.engine.factory.blockfactory;

import rabbitescape.engine.Direction;
import rabbitescape.engine.block.*;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockmaterial.MetalMaterial;
import rabbitescape.engine.block.blockshape.BridgeShape;
import rabbitescape.engine.block.blockshape.FlatShape;
import rabbitescape.engine.block.blockshape.UprightShape;
import rabbitescape.engine.block.blockshape.UpleftShape;
import rabbitescape.engine.factory.Factory;
import rabbitescape.engine.util.VariantGenerator;

public class BlockFactory implements Factory<Block> {
    @Override
    public Block create(char c, int x, int y, Object... args) {
        VariantGenerator variantGen = (VariantGenerator) args[0];

        switch (c) {
            case 'D': return new DecayBlock(x, y, variantGen.next(4), 3);
            case 'J': return new SpringBoardBlock(x, y,variantGen.next(4));
            case '#': return new EarthBlock(x, y, new FlatShape(), variantGen.next(4));
            case 'M': return new MetalBlock(x, y, new FlatShape(), variantGen.next(4));
            case '/': return new EarthBlock(x, y, new UprightShape(), variantGen.next(4));
            case '\\': return new EarthBlock(x, y, new UpleftShape(), variantGen.next(4));
            case '(': return new BridgeBlock(x, y, new BridgeShape( Direction.RIGHT), variantGen.next(4));
            case ')': return new BridgeBlock(x, y, new BridgeShape( Direction.LEFT), variantGen.next(4));
            default: throw new IllegalArgumentException("Unknown block character: " + c);
        }
    }
}
