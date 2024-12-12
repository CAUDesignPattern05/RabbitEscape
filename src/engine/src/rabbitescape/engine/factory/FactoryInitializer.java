package rabbitescape.engine.factory;

import rabbitescape.engine.factory.blockfactory.BlockFactory;

public class FactoryInitializer {
    public static void initialize() {
        FactoryManager factoryManager = FactoryManager.getInstance();

        // Register Block factories
        factoryManager.registerFactory("Block", new BlockFactory());

        // Register Rabbit factories
        factoryManager.registerFactory("Rabbit", new RabbitFactory());

        // Register Thing factories (Entrances, Exits, etc.)
        factoryManager.registerFactory("Thing", new ThingFactory());

        factoryManager.registerFactory("Token", new ThingFactory());
    }
}
