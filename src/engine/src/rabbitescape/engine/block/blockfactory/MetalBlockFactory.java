package rabbitescape.engine.block.blockfactory;

import rabbitescape.engine.block.*;

public class MetalBlockFactory extends BlockFactory {

    @Override
    public Block createBlock(int x, int y, int variant) {
        // MetalMaterial, FlatShape, StaticBehaviour는 해당 역할을 구현한 클래스여야 합니다.
        BlockMaterial material = new MetalMaterial();   // Metal Material 클래스
        BlockShape shape = new FlatShape();             // FLAT Shape 클래스
        BlockBehaviour behaviour = new StaticBehaviour(); // Static Behaviour 클래스

        return new Block(x, y, material, shape, variant, behaviour);
    }
}