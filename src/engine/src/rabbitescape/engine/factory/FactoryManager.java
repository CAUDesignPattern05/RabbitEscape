package rabbitescape.engine.factory;

import java.util.HashMap;
import java.util.Map;

public class FactoryManager {
    private static FactoryManager instance;
    private final Map<String, Factory<?>> factories = new HashMap<>();

    private FactoryManager() {}

    public static FactoryManager getInstance() {
        if (instance == null) {
            instance = new FactoryManager();
        }
        return instance;
    }

    public void registerFactory(String name, Factory<?> factory) {
        factories.put(name, factory);
    }

    @SuppressWarnings("unchecked")
    public <T> Factory<T> getFactory(String name) {
        return (Factory<T>) factories.get(name);
    }
}
