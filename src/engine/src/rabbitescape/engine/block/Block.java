package rabbitescape.engine.block;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.Direction;
import rabbitescape.engine.World;
import rabbitescape.engine.block.blockmaterial.BlockMaterial;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.util.LookupItem2D;
import rabbitescape.engine.util.Position;

public abstract class Block implements LookupItem2D
{
    protected final int x;
    protected final int y;
    protected final BlockMaterial material;
    protected final BlockShape shape;
    protected final int variant;

    public Block(int x, int y, BlockMaterial material, BlockShape shape, int variant) {
        this.x = x;
        this.y = y;
        this.material = material;
        this.shape = shape;
        this.variant = variant;
    }

    // 고유 행동 정의
    public abstract void onStepped( World world, BehaviourExecutor behaviourExecutor, Integer dir);

    public int getX() { return x; }
    public int getY() { return y; }
    public BlockMaterial getMaterial() { return material; }
    public BlockShape.Type getShape() { return shape.getShape(); }
    public int getVariant() { return variant; }
    public boolean isFlat() {return shape.getShape() == BlockShape.Type.FLAT;}
    @Override
    public Position getPosition() {
        return new Position(x, y);
    }
    public Direction riseDir() {
        return shape.riseDir();
    }
    public boolean isBridge() {
        return shape.isBridge();
    }
    public boolean isDestructible() {
        return material.isDestructible();
    }
}
