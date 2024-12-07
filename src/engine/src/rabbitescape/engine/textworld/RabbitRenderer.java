package rabbitescape.engine.textworld;

import static rabbitescape.engine.Direction.*;

import java.util.List;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.*;

public class RabbitRenderer
{
    public static void render( 
        Chars chars, 
        List<BehaviourExecutor> behaviourExecutors,
        boolean runtimeMeta 
    )
    {
        for ( BehaviourExecutor behaviourExecutor : behaviourExecutors )
        {
            if ( State.RABBIT_OUT_OF_BOUNDS == behaviourExecutor.state )
            {
                continue;
            }
            chars.set(
                behaviourExecutor.x,
                behaviourExecutor.y,
                charForRabbit( behaviourExecutor ),
                behaviourExecutor.saveState( runtimeMeta )
            );
        }
    }

    private static char charForRabbit( BehaviourExecutor BehaviourExecutor )
    {
        if ( BehaviourExecutor.getDirection() == RIGHT )
        {
            if ( BehaviourExecutor instanceof Rabbit)
            {
                return 'r';
            }
            else
            {
                return 't';
            }
        }
        else
        {
            if ( BehaviourExecutor instanceof Rabbit)
            {
                return 'j';
            }
            else
            {
                return 'y';
            }
        }
    }
}
