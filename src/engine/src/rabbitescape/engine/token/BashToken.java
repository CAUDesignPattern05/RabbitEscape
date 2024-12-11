package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Bashing;

public class BashToken extends Token
{
    public BashToken( int x, int y )
    {
        super( x, y, Type.bash );
    }

    public BashToken( int x, int y, World world )
    {
        super( x, y, Type.bash, world );
    }

    @Override
    protected State getFallingState()
    {
        return State.TOKEN_BASH_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_BASH_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_BASH_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_BASH_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "bash";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Bashing( actionHandler ); }
}
