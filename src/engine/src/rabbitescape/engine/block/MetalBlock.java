package rabbitescape.engine.block;

import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockmaterial.MetalMaterial;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.block.blockshape.FlatShape;

public class MetalBlock extends Block
{
    public MetalBlock(int x, int y, BlockShape shape, int variant) {
        super(x, y, new MetalMaterial(), shape, variant);
    }


    @Override
    public void onStepped() {
    }
}
