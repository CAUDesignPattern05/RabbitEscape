package rabbitescape.engine.block;

public abstract class BlockFactory {
    public static Block createBlock(String type, int x, int y, int variant) {
        switch (type.toLowerCase()) {
            case "earth":
                return new EarthBlock(x, y, variant);
            case "metal":
                return new MetalBlock(x, y, variant);
            // case "decay":
            //     return new DecayBlock(x, y, variant, 3); // 내구도 3으로 초기화
            default:
                throw new IllegalArgumentException("Unknown block type: " + type);
        }
    }

    public abstract Block createBlock( int x, int y, int variant );
}
