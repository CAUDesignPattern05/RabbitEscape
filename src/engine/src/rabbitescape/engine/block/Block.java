package rabbitescape.engine.block;

public class Block {
    protected final int x;
    protected final int y;
    protected final BlockMaterial material; // Material 계층 구조
    protected final BlockShape shape;       // Shape 계층 구조
    protected final int variant;            // 다양한 변형 가능
    protected final BlockBehaviour behaviour; // 행동 전략

    public Block(
        int x,
        int y,
        BlockMaterial material,
        BlockShape shape,
        int variant,
        BlockBehaviour behaviour
    ) {
        this.x = x;
        this.y = y;
        this.material = material;
        this.shape = shape;
        this.variant = variant;
        this.behaviour = behaviour;
    }

    public void interact() {
        behaviour.interact(this); // 동작 위임
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BlockMaterial getMaterial() {
        return material;
    }

    public BlockShape getShape() {
        return shape;
    }

    public int getVariant() {
        return variant;
    }
}
