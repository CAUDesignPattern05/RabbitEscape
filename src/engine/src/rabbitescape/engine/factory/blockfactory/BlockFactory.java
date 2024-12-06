package rabbitescape.engine.block.blockfactory;

import rabbitescape.engine.block.Block;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockmaterial.MetalMaterial;
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
            case '#': return new Block(x, y, new EarthMaterial(), new FlatShape(), variantGen.next(4));
            case 'M': return new Block(x, y, new MetalMaterial(), new FlatShape(), variantGen.next(4));
            case '/': return new Block(x, y, new EarthMaterial(), new UprightShape(), variantGen.next(4));
            case '\\': return new Block(x, y, new EarthMaterial(), new UpleftShape(), variantGen.next(4));
            case '(': return new Block(x, y, new EarthMaterial(), new UprightShape(), variantGen.next(4));
            case ')': return new Block(x, y, new EarthMaterial(), new UpleftShape(), variantGen.next(4));
            default: throw new IllegalArgumentException("Unknown block character: " + c);
        }
    }
}
