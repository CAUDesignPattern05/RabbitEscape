package rabbitescape.engine.Block.blockfactory;

public abstract class BlockShape {
    private final String name;

    protected BlockShape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class FlatShape extends BlockShape {
    public FlatShape() {
        super("Flat");
    }
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
