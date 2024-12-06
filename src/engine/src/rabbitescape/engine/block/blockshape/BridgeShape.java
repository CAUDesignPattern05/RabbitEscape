package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;

public class BridgeShape extends BlockShape {
    public BridgeShape( Direction direction) {
            super( Direction.LEFT, true, Type.UP_LEFT );
    }
}