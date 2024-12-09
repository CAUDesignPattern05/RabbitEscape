package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

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
    public State getFallingState()
    {
        return State.TOKEN_EXPLODE_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_EXPLODE_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_EXPLODE_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_EXPLODE_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "explode";
    }
}
