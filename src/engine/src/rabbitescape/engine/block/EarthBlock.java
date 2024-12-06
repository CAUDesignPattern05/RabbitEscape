package rabbitescape.engine.block;

import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.block.blockshape.FlatShape;
import rabbitescape.engine.util.Position;

public class EarthBlock extends Block {
    public EarthBlock(int x, int y, BlockShape shape, int variant) {
        super(
            x, y, new EarthMaterial(), shape, variant
        );
    }

    @Override
    public void onStepped() {
    }
}
