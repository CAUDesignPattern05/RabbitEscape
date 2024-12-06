package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;

public class BridgeShape extends BlockShape {
    public BridgeShape(Direction direction) {
        super(direction, true, determineType(direction));
    }

    private static Type determineType(Direction direction) {
        if (direction == Direction.LEFT) {
            return Type.BRIDGE_UP_LEFT;
        } else if (direction == Direction.RIGHT) {
            return Type.BRIDGE_UP_RIGHT;
        } else {
            throw new IllegalArgumentException("Invalid direction for BridgeShape: " + direction);
        }
    }
}
