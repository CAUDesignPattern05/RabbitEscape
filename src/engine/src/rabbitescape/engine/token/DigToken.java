package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Digging;

public class DigToken extends Token
{
    public DigToken( int x, int y )
    {
        super( x, y, Type.dig );
    }

    public DigToken( int x, int y, World world )
    {
        super( x, y, Type.dig, world );
    }

    @Override
    protected State getFallingState()
    {
        return State.TOKEN_DIG_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_DIG_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_DIG_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_DIG_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "dig";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Digging( actionHandler ); }
}
