package rabbitescape.engine.block;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.World;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.FlatShape;

public class SpringBoardBlock extends Block
{

    public SpringBoardBlock( int x, int y, int variant)
    {
        super( x, y, new EarthMaterial(), new FlatShape(), variant );
    }

    @Override
    public void onStepped( World world, BehaviourExecutor behaviourExecutor,Integer dir)
    {
        behaviourExecutor.y -=2;
    }
}

