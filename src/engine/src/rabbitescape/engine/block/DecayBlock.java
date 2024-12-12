package rabbitescape.engine.block;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.World;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.FlatShape;

public class DecayBlock extends Block {
    private int durability;

    public DecayBlock(int x, int y, int variant, int initialDurability) {
        super(x, y, new EarthMaterial(), new FlatShape(), variant);
        this.durability = initialDurability;
    }

    @Override
    public void onStepped( World world, BehaviourExecutor behaviourExecutor,Integer dir) {
        if (--this.durability <= 0)
            world.changes.removeBlockAt( behaviourExecutor.x, behaviourExecutor.y + 1 );
    }
}

