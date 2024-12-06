package rabbitescape.engine.block;

import rabbitescape.engine.Direction;
import rabbitescape.engine.block.blockmaterial.BlockMaterial;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.util.LookupItem2D;
import rabbitescape.engine.util.Position;

public class Block implements LookupItem2D {
    private final int x;
    private final int y;
    public final BlockMaterial material;
    private final BlockShape shape;
    private final int variant;

    public Block(int x, int y, BlockMaterial material, BlockShape shape, int variant) {
        this.x = x;
        this.y = y;
        this.material = material;
        this.shape = shape;
        this.variant = variant;
    }

    public boolean isDestructible() {
        return material.isDestructible();
    }
    public boolean isFlat() {return shape.isFlat();}
    public Direction riseDir() {
        return shape.riseDir();
    }
    public boolean isBridge() {
        return shape.isBridge();
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BlockShape.Type getShape()
    {
        return shape.getShape();
    }

    public BlockMaterial getMaterial()
    {
        return material;
    }

    public int getVariant() {
        return variant;
    }
}
