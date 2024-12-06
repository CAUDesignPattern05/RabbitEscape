package rabbitescape.engine.block.blockmaterial;

public class MetalMaterial implements BlockMaterial
{
    @Override
    public boolean isDestructible() {
        return false; // 금속 블록은 파괴 불가
    }
}