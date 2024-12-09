package rabbitescape.engine.factory;


public abstract class AbstractFactory<T> {
    public abstract T create(Object... args);
}
