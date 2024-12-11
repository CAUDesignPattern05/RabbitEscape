package rabbitescape.engine.testblock;

import org.junit.Test;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockmaterial.MetalMaterial;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBlockMaterial
{
    @Test
    public void testEarthMaterialProperties() {
        EarthMaterial material = new EarthMaterial();
        assertTrue(
            "EarthMaterial should be destructible",
            material.isDestructible()
        );
    }

    @Test
    public void testMetalMaterialProperties() {
        MetalMaterial material = new MetalMaterial();
        assertFalse(
            "MetalMaterial should not be destructible",
            material.isDestructible()
        );
    }
}
