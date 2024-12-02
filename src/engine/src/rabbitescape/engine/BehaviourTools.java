package rabbitescape.engine;

import static rabbitescape.engine.block.Shape.*;
import static rabbitescape.engine.Direction.RIGHT;
import static rabbitescape.engine.Direction.opposite;
import rabbitescape.engine.util.Position;

public class BehaviourTools
{
    public final Rabbit rabbit;
    public final World world;

    public BehaviourTools( Rabbit rabbit, World world )
    {
        this.rabbit = rabbit;
        this.world = world;
    }

    public ChangeDescription.State rl(
        ChangeDescription.State rightState,
        ChangeDescription.State leftState
    )
    {
        return rabbit.dir == RIGHT ? rightState : leftState;
    }

    public boolean pickUpToken( Token.Type type )
    {
        return pickUpToken( type, false );
    }

    public boolean rabbitIsFalling()
    {
        switch (rabbit.state)
        {
        case RABBIT_FALLING:
        case RABBIT_FALLING_1:
        case RABBIT_FALLING_1_TO_DEATH:
        case RABBIT_DYING_OF_FALLING_2:
        case RABBIT_DYING_OF_FALLING:
        case RABBIT_FALLING_ONTO_LOWER_RIGHT:
        case RABBIT_FALLING_ONTO_RISE_RIGHT:
        case RABBIT_FALLING_ONTO_LOWER_LEFT:
        case RABBIT_FALLING_ONTO_RISE_LEFT:
        case RABBIT_FALLING_1_ONTO_LOWER_RIGHT:
        case RABBIT_FALLING_1_ONTO_RISE_RIGHT:
        case RABBIT_FALLING_1_ONTO_LOWER_LEFT:
        case RABBIT_FALLING_1_ONTO_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT_2:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT_2:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT_2:
            return true;
        default:
            return false;
        }
    }

    public boolean rabbitIsClimbing()
    {
        switch( rabbit.state)
        {
        case RABBIT_ENTERING_EXIT_CLIMBING_RIGHT:
        case RABBIT_ENTERING_EXIT_CLIMBING_LEFT:
        case RABBIT_CLIMBING_LEFT_START:
        case RABBIT_CLIMBING_LEFT_CONTINUE_1:
        case RABBIT_CLIMBING_LEFT_CONTINUE_2:
        case RABBIT_CLIMBING_LEFT_END:
        case RABBIT_CLIMBING_LEFT_BANG_HEAD:
        case RABBIT_CLIMBING_RIGHT_START:
        case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
        case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
        case RABBIT_CLIMBING_RIGHT_END:
        case RABBIT_CLIMBING_RIGHT_BANG_HEAD:
            return true;
        default:
            return false;
        }
    }

    public boolean rabbitIsBashing()
    {
        switch( rabbit.state)
        {
        case RABBIT_BASHING_RIGHT:
        case RABBIT_BASHING_LEFT:
        case RABBIT_BASHING_UP_RIGHT:
        case RABBIT_BASHING_UP_LEFT:
        case RABBIT_BASHING_USELESSLY_RIGHT:
        case RABBIT_BASHING_USELESSLY_LEFT:
        case RABBIT_BASHING_USELESSLY_RIGHT_UP:
        case RABBIT_BASHING_USELESSLY_LEFT_UP:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks for the presence of a token. Removes token from the world
     * and returns true if a token is being picked up.
     */
    public boolean pickUpToken( Token.Type type, boolean evenIfNotOnGround )
    {
        if ( rabbitIsFalling() && rabbit.isFallingToDeath() )
        {
            return false; // Dying rabbits not allowed to consume tokens
        }

        if ( evenIfNotOnGround || onGround() )
        {
            Token token = world.getTokenAt( rabbit.x, rabbit.y );
            if ( token != null && token.type == type )
            {
                world.changes.removeToken( token );
                return true;
            }
        }
        return false;
    }

    public block blockHere()
    {
        return world.getBlockAt( rabbit.x, rabbit.y );
    }

    public block blockNext()
    {
        return world.getBlockAt( nextX(), rabbit.y );
    }

    public block blockBelow()
    {
        return world.getBlockAt( rabbit.x, rabbit.y + 1 );
    }

    public block block2Below()
    {
        return world.getBlockAt( rabbit.x, rabbit.y + 2 );
    }

    public block blockBelowNext()
    {
        return world.getBlockAt( nextX(), rabbit.y + 1 );
    }

    public block blockAbove()
    {
        return world.getBlockAt( rabbit.x, rabbit.y - 1 );
    }

    public block blockAboveNext()
    {
        return world.getBlockAt( nextX(), rabbit.y - 1 );
    }

    private boolean onGround()
    {
        return ( rabbit.onSlope || blockBelow() != null );
    }

    public boolean isWall( block block )
    {
        return (
               block != null
            && (
                   block.shape == FLAT
                || (
                    block.riseDir() == opposite( rabbit.dir )
                    && isSolid( block )
                )
            )
        );
    }


    public static boolean shapeEquals( block b, block.Shape s )
    {
        if ( null == b )
        {
            return false;
        }
        return s == b.shape;
    }

    public static boolean isRightRiseSlope( block b )
    {
        if( b == null )
        {
            return false;
        }
        return b.shape == UP_RIGHT
            || b.shape == BRIDGE_UP_RIGHT;
    }

    public static boolean isLeftRiseSlope( block b )
    {
        if( b == null )
        {
            return false;
        }
        return b.shape == UP_LEFT
            || b.shape == BRIDGE_UP_LEFT;
    }

    public static boolean isSlope( block b )
    {
        return isRightRiseSlope( b ) || isLeftRiseSlope( b );
    }

    public static boolean isBridge( block b )
    {
        if( b == null )
        {
            return false;
        }
        switch ( b.shape )
        {
        case BRIDGE_UP_LEFT:
        case BRIDGE_UP_RIGHT:
            return true;
        default:
            return false;
        }
    }

    public static boolean isSolid( block block )
    {
        return (
               block.shape == FLAT
            || block.shape == UP_LEFT
            || block.shape == UP_RIGHT
        );
    }

    public boolean isRoof( block block )
    {
        return
            (
                block != null
                && (
                       block.shape == FLAT
                    || block.shape == UP_LEFT
                    || block.shape == UP_RIGHT
                )
            );
    }

    public boolean isOnSlopeStateUnreliable()
    {
        block block = blockHere();
        return
            null != block &&
            (
                   block.shape == UP_LEFT
                || block.shape == UP_RIGHT
                || block.shape == BRIDGE_UP_LEFT
                || block.shape == BRIDGE_UP_RIGHT
            );
    }

    public boolean isFlat( block block )
    {
        return s_isFlat( block );
    }

    public static boolean s_isFlat( block block )
    {
        return ( block != null && block.shape == FLAT );
    }

    private boolean goingUpSlope()
    {
        if ( rabbit.onSlope )
        {
            if( isOnUpSlope() )
            {
                return true;
            }
        }
        return false;
    }

    public boolean isOnUpSlope()
    {
        return rabbit.onSlope && hereIsUpSlope();
    }

    /**
     * Check if rabbit is changing from an up slope directly to a down slope.
     */
    public boolean isCresting()
    {
        // block where slope would be if it continues
        block contBlock = world.getBlockAt( nextX(), nextY() );
        block belowContBlock = world.getBlockAt( nextX(), nextY() + 1 );

        return isOnUpSlope() &&
               null == contBlock &&
               isDownSlope( belowContBlock );
    }

    /**
     * Check if rabbit is changing from a down slope directly to an up slope.
     */
    public boolean isValleying()
    {
        // block where slope would be if it continues
        block alongBlock = world.getBlockAt( nextX(), rabbit.y );

        return isOnDownSlope() &&
               isUpSlope( alongBlock );
    }

    public boolean hereIsUpSlope()
    {
        return isUpSlope( blockHere() );
    }

    public boolean isUpSlope( block block )
    {
        return ( block != null && block.riseDir() == rabbit.dir );
    }

    public boolean isOnDownSlope()
    {
        return rabbit.onSlope && hereIsDownSlope();
    }

    private boolean hereIsDownSlope()
    {
        return isDownSlope( blockHere() );
    }

    public boolean isDownSlope( block block )
    {
        return ( block != null && block.riseDir() == opposite( rabbit.dir ) );
    }

    public static boolean isSlopeNotBridge( block b )
    {
        if ( null == b )
        {
            return false;
        }
        switch( b.shape )
        {
        case UP_LEFT:
        case UP_RIGHT:
            return true;
        default:
            return false;
        }
    }

    public int nextX()
    {
        return
            rabbit.x + (
                rabbit.dir == RIGHT ? 1 : -1
            );
    }

    public int nextY()
    {
        if ( goingUpSlope() )
        {
            return rabbit.y - 1;
        }
        else
        {
            return rabbit.y;
        }
    }

    /**
     * @brief A rabbit may be on a slope block as a digger
     *        or basher removes it. This is here to make sure
     *        they fall.
     */
    public boolean blockHereJustRemoved()
    {
        for ( Position p : world.changes.blocksJustRemoved )
        {
            if ( rabbit.x == p.x && rabbit.y == p.y )
            {
                return true;
            }
        }
        return false;
    }
}
