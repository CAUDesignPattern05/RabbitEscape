package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Exploding;

public class ExplodeToken extends Token
{
    public ExplodeToken( int x, int y )
    {
        super( x, y, Type.explode );
    }

    public ExplodeToken( int x, int y, World world )
    {
        super( x, y, Type.explode, world );
    }

    @Override
    protected State getFallingState()
    {
        return State.TOKEN_EXPLODE_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_EXPLODE_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_EXPLODE_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_EXPLODE_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "explode";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Exploding( actionHandler ); }
}
