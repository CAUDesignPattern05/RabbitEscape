package rabbitescape.engine.block.blockmaterial;

public class EarthMaterial implements BlockMaterial {
    @Override
    public boolean isDestructible() {
        return true;  // 흙 블록은 부술 수 있음
    }
}