package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Bridging;

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
    protected State getFallingState()
    {
        return State.TOKEN_BRIDGE_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_BRIDGE_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_BRIDGE_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_BRIDGE_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "bridge";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Bridging( actionHandler ); }
}
