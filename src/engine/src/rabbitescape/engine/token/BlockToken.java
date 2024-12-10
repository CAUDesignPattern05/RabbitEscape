package rabbitescape.engine.token;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.actions.Action;
import rabbitescape.engine.behaviours.actions.ActionHandler;
import rabbitescape.engine.behaviours.actions.Blocking;

import javax.swing.text.html.HTMLDocument;

public class BlockToken extends Token
{
    public BlockToken( int x, int y )
    {
        super( x, y, Type.block );
    }

    public BlockToken( int x, int y, World world )
    {
        super( x, y, Type.block, world );
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_BLOCK_FALLING;
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_BLOCK_STILL;
    }

    @Override
    public State getFallToSlopeState()
    {
        return State.TOKEN_BLOCK_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_BLOCK_ON_SLOPE;
    }

    @Override
    public String getTokenName()
    {
        return "block";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Blocking( actionHandler ); }
}
