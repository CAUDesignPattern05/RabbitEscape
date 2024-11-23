package rabbitescape.engine.Block;

public class StaticBehaviour implements BlockBehaviour {
    @Override
    public void interact(Block block) {
        System.out.println(block.getMaterial().getName() + " block at (" + block.getX() + ", " + block.getY() + ") does nothing.");
    }
}

