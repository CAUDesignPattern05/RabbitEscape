package rabbitescape.engine.block;

import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockshape.FlatShape;

public class DecayBlock extends Block {
    private int durability;

    public DecayBlock(int x, int y, int variant, int initialDurability) {
        super(x, y, new EarthMaterial(), new FlatShape(), variant);
        this.durability = initialDurability;
    }

    @Override
    public void onStepped() {
        durability--;
        if (durability <= 0) {
            System.out.println("Decay block at (" + this.getX() + ", " + this.getY() + ") is destroyed!");
            // Add destruction logic here
        }
    }
}
