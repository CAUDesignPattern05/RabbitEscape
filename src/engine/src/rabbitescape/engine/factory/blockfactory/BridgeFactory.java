package rabbitescape.engine.factory.blockfactory;

import rabbitescape.engine.Direction;
import rabbitescape.engine.block.Block;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.UpleftShape;
import rabbitescape.engine.block.blockshape.UprightShape;

public class BridgeFactory {
    public Block createBridge(int x, int y, Direction dir, int variant) {
        if (dir == Direction.LEFT) {
            return new Block(x, y, new EarthMaterial(), new UpleftShape(), variant);
        } else {
            return new Block(x, y, new EarthMaterial(), new UprightShape(), variant);
        }
    }
}
