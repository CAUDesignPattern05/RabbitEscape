package rabbitescape.engine.Block;

public abstract class BlockMaterial {
    private final String name;

    protected BlockMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class EarthMaterial extends BlockMaterial {
    public EarthMaterial() {
        super("Earth");
    }
}

class MetalMaterial extends BlockMaterial {
    public MetalMaterial() {
        super("Metal");
    }
}

class DecayMaterial extends BlockMaterial {
    public DecayMaterial() {
        super("Decay");
    }
}