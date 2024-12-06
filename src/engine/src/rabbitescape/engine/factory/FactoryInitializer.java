package rabbitescape.engine.factory;

import rabbitescape.engine.block.blockfactory.BlockFactory;

public class FactoryInitializer {
    public static void initialize() {
        FactoryManager factoryManager = FactoryManager.getInstance();

        // Register Block factories
        factoryManager.registerFactory("Block", new BlockFactory());

        // Register Token factories
        factoryManager.registerFactory("Token", new TokenFactory());

        // Register Rabbit factories
        factoryManager.registerFactory("Rabbit", new RabbitFactory());

        // Register Thing factories (Entrances, Exits, etc.)
        factoryManager.registerFactory("Thing", new ThingFactory());
    }
}
