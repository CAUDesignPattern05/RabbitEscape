package rabbitescape.engine.block.blockfactory;

import rabbitescape.engine.block.BlockFactory;

import java.util.HashMap;
import java.util.Map;

public class BlockFactoryRegistry {
    private static final Map<Character, BlockFactory> factoryMap = new HashMap<>();

    static {
        factoryMap.put('#', new EarthBlockFactory());
        factoryMap.put('M', new MetalBlockFactory());
        // 다른 블록 팩토리 추가 가능
    }

    public static BlockFactory getFactory(char symbol) {
        return factoryMap.get(symbol);
    }
}
