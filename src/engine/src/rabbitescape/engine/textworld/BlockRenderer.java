package rabbitescape.engine.textworld;

import rabbitescape.engine.block.Block;
import rabbitescape.engine.block.BridgeBlock;
import rabbitescape.engine.block.EarthBlock;
import rabbitescape.engine.block.MetalBlock;

public class BlockRenderer
{
    public static void render( Chars chars, Iterable<Block> blocks )
    {
        for ( Block block : blocks )
        {
            chars.set( block.getX(), block.getY(), charForBlock( block ) );
        }
    }

    public static char charForBlock(Block block) {
        if (block instanceof EarthBlock ) {
            switch (block.getShape()) {
                case FLAT:
                    return '#';
                case UP_RIGHT:
                    return '/';
                case UP_LEFT:
                    return '\\';
                default:
                    throw new AssertionError(
                        "Unknown shape for Earth block: " + block.getShape());
            }}else if(block instanceof BridgeBlock ){
                switch(block.getShape()){
                    case BRIDGE_UP_RIGHT:
                        return '(';
                    case BRIDGE_UP_LEFT:
                        return ')';
                }
            }
        else if (block instanceof MetalBlock ) {
            switch (block.getShape()) {
                case FLAT:
                    return 'M';
                default:
                    throw new AssertionError(
                        "Unknown shape for Metal block: " + block.getShape());
            }
        }

        throw new AssertionError(
            "Unknown material for block: " + block.getMaterial());
    }

}
