package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

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
    public State getFallingState()
    {
        return State.TOKEN_CLIMB_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_CLIMB_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_BASH_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_CLIMB_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "climb";
    }
}
