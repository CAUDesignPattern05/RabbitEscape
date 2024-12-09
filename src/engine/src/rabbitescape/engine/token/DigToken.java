package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

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
    public State getFallingState()
    {
        return State.TOKEN_DIG_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_DIG_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_DIG_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_DIG_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "dig";
    }
}
