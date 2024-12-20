package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;
public class UprightShape extends BlockShape {
    public UprightShape() {
        super(Direction.RIGHT, false, Type.UP_RIGHT); // Set the type as UP_RIGHT
    }
    public boolean isFlat() {
        return false; // FlatShape는 항상 flat
    }
}