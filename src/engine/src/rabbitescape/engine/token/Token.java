package rabbitescape.engine.token;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

import java.util.HashMap;
import java.util.Map;

public abstract class Token extends Thing
{
    public static enum Type
    {
        bash,
        dig,
        bridge,
        block,
        climb,
        explode,
        brolly
    }

    public Type type;

    protected abstract State getFallingState();

    protected abstract State getStillState();

    protected abstract State getFallToSlopeState();

    protected abstract State getOnSlopeState();

    protected abstract String getTokenName();

    public Token( int x, int y, Type type )
    {
        super( x, y, null );
        this.state = chooseState( false, false, true );
        this.type = type;
    }

    public Token( int x, int y, Type type, World world )
    {
        this( x, y, type );
        boolean onSlope = BehaviourTools.isSlope( world.getBlockAt( x, y ) );
        // Can't use calcNewState here since we have just been created, so
        // can't be moving (until a time step passes).
        this.state = chooseState( false, false, onSlope );
    }

    private State chooseState(
        boolean moving,
        boolean slopeBelow,
        boolean onSlope
    )
    {
        if ( onSlope )
        {
            return getOnSlopeState();
        }
        if ( !moving )
        {
            return getStillState();
        }
        if ( slopeBelow )
        {
            return getFallToSlopeState();
        }
        return getFallingState();
    }

    @Override
    public void calcNewState( World world )
    {
        Block onBlock = world.getBlockAt( x, y );
        Block belowBlock = world.getBlockAt( x, y + 1 );
        boolean still = (
            BehaviourTools.s_isFlat( belowBlock )
                || ( onBlock != null )
                || BridgeTools.someoneIsBridgingAt( world, x, y )
        );

        state = chooseState(
            !still,
            BehaviourTools.isSlope( belowBlock ),
            BehaviourTools.isSlope( onBlock )
        );
    }

    @Override
    public void step( World world )
    {
        if ( isFallingState() )
        {
            ++y;

            if ( y >= world.size.height )
            {
                world.changes.removeToken( this );
            }
        }
    }

    protected boolean isFallingState()
    {
        return state == getFallingState() || state == getFallToSlopeState();
    }

    @Override
    public Map<String, String> saveState( boolean runtimeMeta )
    {
        return new HashMap<>();
    }

    @Override
    public void restoreFromState( Map<String, String> state )
    {
    }

    @Override
    public String overlayText()
    {
        return getTokenName();
    }

    public static String name( Type ability )
    {
        String n = ability.name();
        return n.substring( 0, 1 ).toUpperCase() + n.substring( 1 );
    }

    public Type getType() { return type; }
}
