package rabbitescape.engine.block;

public abstract class BlockShape {
    private final String name;

    protected BlockShape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getShape();
}

class UpRightShape extends BlockShape {
    public UpRightShape() {
        super("UpRight");
    }
}

class UpLeftShape extends BlockShape {
    public UpLeftShape() {
        super("UpLeft");
    }
}
