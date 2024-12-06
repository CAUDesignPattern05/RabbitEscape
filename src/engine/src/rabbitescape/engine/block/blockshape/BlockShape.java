package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;

public abstract class BlockShape {
    private final Direction direction;
    private final boolean isBridge;
    private final Type shape;

    // Enum for shape types
    public enum Type {
        FLAT,
        UP_RIGHT,
        UP_LEFT,
        BRIDGE_UP_RIGHT,
        BRIDGE_UP_LEFT
    }

    protected BlockShape(Direction direction, boolean isBridge, Type shape) {
        this.direction = direction;
        this.isBridge = isBridge;
        this.shape = shape;
    }

    // Getter for direction
    public Direction riseDir() {
        return direction;
    }

    // Getter for bridge status
    public boolean isBridge() {
        return isBridge;
    }

    // Getter for shape type
    public Type getShape() {
        return shape;
    }

    public boolean isFlat() {
        return shape == Type.FLAT;
    }
}
