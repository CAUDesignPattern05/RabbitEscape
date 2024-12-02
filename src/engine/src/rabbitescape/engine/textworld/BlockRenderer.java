package rabbitescape.engine.textworld;

public class BlockRenderer
{
    public static void render( Chars chars, Iterable<block> blocks )
    {
        for ( rabbitescape.engine.block block : blocks )
        {
            chars.set( block.x, block.y, charForBlock( block ) );
        }
    }

    public static char charForBlock( block block )
    {
        switch ( block.material )
        {
            case EARTH:
                switch ( block.shape )
                {
                    case FLAT:            return '#';
                    case UP_RIGHT:        return '/';
                    case UP_LEFT:         return '\\';
                    case BRIDGE_UP_RIGHT: return '(';
                    case BRIDGE_UP_LEFT:  return ')';
                }
                break;
            case METAL:
                switch ( block.shape )
                {
                    case FLAT:            return 'M';
                    default:
                        break;
                }
                break;
        }
        throw new AssertionError(
            "Unknown Block type: " + block.material + " " + block.shape );
    }
}
