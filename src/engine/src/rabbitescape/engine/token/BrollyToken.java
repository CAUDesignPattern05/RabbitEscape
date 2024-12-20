package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Brollychuting;

public class BrollyToken extends Token
{
    public BrollyToken( int x, int y )
    {
        super( x, y, Type.brolly );
    }

    public BrollyToken( int x, int y, World world )
    {
        super( x, y, Type.brolly, world );
    }

    @Override
    protected State getFallingState()
    {
        return State.TOKEN_BROLLY_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_BROLLY_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_BROLLY_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_BROLLY_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "brolly";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Brollychuting( actionHandler ); }
}
