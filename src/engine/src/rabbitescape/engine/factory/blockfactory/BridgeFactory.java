package rabbitescape.engine.factory.blockfactory;

import rabbitescape.engine.Direction;
import rabbitescape.engine.block.Block;
import rabbitescape.engine.block.BridgeBlock;
import rabbitescape.engine.block.EarthBlock;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.BridgeShape;
import rabbitescape.engine.block.blockshape.UpleftShape;
import rabbitescape.engine.block.blockshape.UprightShape;

public class BridgeFactory {
    public Block createBridge(int x, int y, Direction dir, int variant) {
        return new BridgeBlock(x, y, new BridgeShape( dir), variant);
    }
}
