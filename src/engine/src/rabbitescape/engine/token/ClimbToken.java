package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Climbing;

public class ClimbToken extends Token
{
    public ClimbToken( int x, int y )
    {
        super( x, y, Type.climb );
    }

    public ClimbToken( int x, int y, World world )
    {
        super( x, y, Type.climb, world );
    }

    @Override
    protected State getFallingState()
    {
        return State.TOKEN_CLIMB_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_CLIMB_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_CLIMB_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_CLIMB_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "climb";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Climbing( actionHandler ); }
}
