package rabbitescape.engine.block;

public abstract class BlockMaterial {
    private final String name;

    protected BlockMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class DecayMaterial extends BlockMaterial {
    public DecayMaterial() {
        super("Decay");
    }
}