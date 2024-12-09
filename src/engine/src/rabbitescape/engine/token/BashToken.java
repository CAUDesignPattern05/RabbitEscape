package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

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
    public State getFallingState()
    {
        return State.TOKEN_BASH_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_BASH_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_BASH_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_BASH_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "bash";
    }
}
