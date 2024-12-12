package rabbitescape.engine.testfactory;

import org.junit.Test;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.Rabbot;
import rabbitescape.engine.factory.RabbitFactory;
import rabbitescape.engine.util.VariantGenerator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestRabbitFactory
{
    private RabbitFactory factory = new RabbitFactory();

    @Test
    public void testCreateRabbit() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Rabbit rabbit = ( Rabbit )factory.create('r', 1, 1, variantGen);

        assertNotNull( rabbit.toString(), "Rabbit should not be null");
        assertTrue(
            "Rabbit should be of type rabbit",
            rabbit instanceof Rabbit
        );
    }

    @Test
    public void testCreateRabbot() {
        VariantGenerator variantGen = new VariantGenerator(4);
        Rabbot rabbot = ( Rabbot )factory.create('t', 1, 1, variantGen);

        assertNotNull( rabbot.toString(), "Rabbit should not be null");
        assertTrue(
            "Rabbot should be of type rabbot",
            rabbot instanceof Rabbot
        );
    }
}
