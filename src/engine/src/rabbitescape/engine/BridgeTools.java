package rabbitescape.engine;

import rabbitescape.engine.util.Position;

public class BridgeTools
{

    public static boolean someoneIsBridgingAt( World world, int x, int y )
    {
        for ( BehaviourExecutor BehaviourExecutor : world.behaviourExecutors )
        {
            if ( rabbitIsBridgingAt( BehaviourExecutor, x, y ) )
            {
                return true;
            }
        }
        return false;
    }

    public static boolean rabbitIsBridgingAt( BehaviourExecutor BehaviourExecutor, int x, int y )
    {
        Position bridging = RabbitStates.whereBridging(
            new StateAndPosition( BehaviourExecutor.state, BehaviourExecutor.x, BehaviourExecutor.y ) );

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
