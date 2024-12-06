package rabbitescape.engine.factory;

public interface Factory<T> {
    T create(char c, int x, int y, Object... args);
}
