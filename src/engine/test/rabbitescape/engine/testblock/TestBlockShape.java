package rabbitescape.engine.testblock;

import org.junit.Test;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.block.blockshape.FlatShape;
import rabbitescape.engine.block.blockshape.UpleftShape;
import rabbitescape.engine.block.blockshape.UprightShape;

import static org.junit.Assert.assertEquals;

public class TestBlockShape
{
    @Test
    public void testFlatShapeProperties() {
        FlatShape shape = new FlatShape();
        assertEquals(
            "Shape should be FLAT",
            BlockShape.Type.FLAT,
            shape.getShape()
        );
    }

    @Test
    public void testUpleftShapeProperties() {
        UpleftShape shape = new UpleftShape();
        assertEquals(
            "Shape should be UP_LEFT",
            BlockShape.Type.UP_LEFT,
            shape.getShape()
        );
    }

    @Test
    public void testUprightShapeProperties() {
        UprightShape shape = new UprightShape();
        assertEquals(
            "Shape should be UP_LEFT",
            BlockShape.Type.UP_RIGHT,
            shape.getShape()
        );
    }
}
