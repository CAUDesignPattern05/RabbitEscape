package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public class BridgeToken extends Token
{
    public BridgeToken( int x, int y )
    {
        super( x, y, Type.bridge );
    }

    public BridgeToken( int x, int y, World world )
    {
        super( x, y, Type.bridge, world );
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_BRIDGE_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_BRIDGE_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_BRIDGE_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_BRIDGE_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "bridge";
    }
}
