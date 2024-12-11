package rabbitescape.engine.testfactory;

import org.junit.Test;
import rabbitescape.engine.factory.Factory;
import rabbitescape.engine.factory.FactoryManager;
import rabbitescape.engine.factory.blockfactory.BlockFactory;

import static org.junit.Assert.*;

public class TestFactoryManager
{

    @Test
    public void testRegisterAndRetrieveFactory() {
        FactoryManager manager = FactoryManager.getInstance();
        BlockFactory blockFactory = new BlockFactory();

        manager.registerFactory("Block", blockFactory);
        Factory<?> retrievedFactory = manager.getFactory("Block");

        System.out.println("Registered Factory: " + blockFactory);
        System.out.println("Retrieved Factory: " + retrievedFactory);

        assertNotNull( retrievedFactory.toString(), "Factory should not be null");
        // Compare references instead of using equals()
        assertSame(
            "Retrieved factory should match the registered factory",
            blockFactory,
            retrievedFactory
        );
    }


    @Test
    public void testSingletonInstance() {
        FactoryManager instance1 = FactoryManager.getInstance();
        FactoryManager instance2 = FactoryManager.getInstance();

        assertSame(
            "Both instances should be the same (singleton)",
            instance1,
            instance2
        );
    }
}
