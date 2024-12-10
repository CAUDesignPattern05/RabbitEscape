package rabbitescape.engine.block;

import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.FlatShape;

public class SpringBoardBlock extends Block
{

    public SpringBoardBlock( int x, int y, int variant)
    {
        super( x, y, new EarthMaterial(), new FlatShape(), variant );
    }

    @Override
    public void onStepped()
    {
        //something happpens!
    }
}
