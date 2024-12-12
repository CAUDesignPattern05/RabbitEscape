package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;

public class UpleftShape extends BlockShape {
    public UpleftShape() {
        super(Direction.LEFT, false, Type.UP_LEFT); // Set the type as UP_LEFT
    }
    public boolean isFlat() {
        return false; // FlatShape는 항상 flat
    }
}