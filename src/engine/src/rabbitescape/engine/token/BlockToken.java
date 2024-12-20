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
    protected State getFallingState()
    {
        return State.TOKEN_BLOCK_FALLING;
    }

    @Override
    protected State getStillState()
    {
        return State.TOKEN_BLOCK_STILL;
    }

    @Override
    protected State getFallToSlopeState()
    {
        return State.TOKEN_BLOCK_FALL_TO_SLOPE;
    }

    @Override
    protected State getOnSlopeState()
    {
        return State.TOKEN_BLOCK_ON_SLOPE;
    }

    @Override
    protected String getTokenName()
    {
        return "block";
    }

    @Override
    public Action createAction( ActionHandler actionHandler ) { return new Blocking( actionHandler ); }
}
