package rabbitescape.engine.token;

import rabbitescape.engine.World;

public final class TokenFactory
{
    public static Token createToken( int x, int y, Token.Type type )
    {
        switch ( type )
        {
            case bash:
                return new BashToken( x, y );
            case block:
                return new BlockToken( x, y );
            case bridge:
                return new BridgeToken( x, y );
            case brolly:
                return new BrollyToken( x, y );
            case climb:
                return new ClimbToken( x, y );
            case dig:
                return new DigToken( x, y );
            case explode:
                return new ExplodeToken( x, y );
            default:
                throw new IllegalArgumentException(
                    "Unknown token type: " + type );
        }
    }

    public static Token createToken(
        int x,
        int y,
        Token.Type type,
        World world
    )
    {
        switch ( type )
        {
            case bash:
                return new BashToken( x, y, world );
            case block:
                return new BlockToken( x, y, world );
            case bridge:
                return new BridgeToken( x, y, world );
            case brolly:
                return new BrollyToken( x, y, world );
            case climb:
                return new ClimbToken( x, y, world );
            case dig:
                return new DigToken( x, y, world );
            case explode:
                return new ExplodeToken( x, y, world );
            default:
                throw new IllegalArgumentException(
                    "Unknown token type: " + type );
        }
    }
}
