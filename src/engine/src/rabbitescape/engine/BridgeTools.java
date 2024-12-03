package rabbitescape.engine;

import rabbitescape.engine.util.Position;

public class BridgeTools
{

    public static boolean someoneIsBridgingAt( World world, int x, int y )
    {
        for ( OldRabbit oldRabbit : world.oldRabbits )
        {
            if ( rabbitIsBridgingAt( oldRabbit, x, y ) )
            {
                return true;
            }
        }
        return false;
    }

    public static boolean rabbitIsBridgingAt( OldRabbit oldRabbit, int x, int y )
    {
        Position bridging = RabbitStates.whereBridging(
            new StateAndPosition( oldRabbit.state, oldRabbit.x, oldRabbit.y ) );

        if ( bridging == null )
        {
            return false;
        }
        else
        {
            return ( bridging.x == x && bridging.y == y );
        }
    }

}
