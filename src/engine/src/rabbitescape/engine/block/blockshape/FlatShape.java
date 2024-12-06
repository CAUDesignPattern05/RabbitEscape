package rabbitescape.engine.block.blockshape;

import rabbitescape.engine.Direction;

public class FlatShape extends BlockShape {
    public FlatShape() {
        super(Direction.DOWN, false,Type.FLAT);
    }
    @Override
    public boolean isFlat() {
        return true; // FlatShape는 항상 flat
    }
}