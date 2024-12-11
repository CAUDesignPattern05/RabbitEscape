package rabbitescape.engine.testfactory;

import org.junit.Test;
import rabbitescape.engine.block.*;
import rabbitescape.engine.factory.blockfactory.BlockFactory;
import rabbitescape.engine.util.VariantGenerator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestBlockFactory {

    private BlockFactory factory = new BlockFactory();

    @Test
    public void testCreateEarthBlock() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Block block = factory.create('#', 1, 1, variantGen);

        assertNotNull( block.toString(), "Block should not be null");
        assertTrue(
            "Block should be of type EarthBlock",
            block instanceof EarthBlock
        );
    }

    @Test
    public void testCreateMetalBlock() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Block block = factory.create('D', 1, 1, variantGen);

        assertNotNull( block.toString(), "Block should not be null");
        assertTrue(
            "Block should be of type MetalBlock",
            block instanceof MetalBlock
        );
    }

    @Test
    public void testCreateDecayBlock() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Block block = factory.create('D', 1, 1, variantGen);

        assertNotNull( block.toString(), "Block should not be null");
        assertTrue(
            "Block should be of type DecayBlock",
            block instanceof DecayBlock
        );
    }

    @Test
    public void testCreateSpringBoardBlock() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Block block = factory.create('J', 2, 2, variantGen);

        assertNotNull( block.toString(), "Block should not be null");
        assertTrue(
            "Block should be of type SpringBoardBlock",
            block instanceof SpringBoardBlock
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBlockCharacter() {
        VariantGenerator variantGen = new VariantGenerator(4);

        factory.create('Z', 0, 0, variantGen);

    }
}
