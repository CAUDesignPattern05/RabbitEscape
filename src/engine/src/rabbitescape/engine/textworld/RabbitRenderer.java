package rabbitescape.engine.textworld;

import static rabbitescape.engine.Direction.*;

import java.util.List;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.OldRabbit;

public class RabbitRenderer
{
    public static void render( 
        Chars chars, 
        List<OldRabbit> oldRabbits,
        boolean runtimeMeta 
    )
    {
        for ( OldRabbit oldRabbit : oldRabbits )
        {
            if ( State.RABBIT_OUT_OF_BOUNDS == oldRabbit.state )
            {
                continue;
            }
            chars.set(
                oldRabbit.x,
                oldRabbit.y,
                charForRabbit( oldRabbit ),
                oldRabbit.saveState( runtimeMeta )
            );
        }
    }

    private static char charForRabbit( OldRabbit oldRabbit )
    {
        if ( oldRabbit.dir == RIGHT )
        {
            if ( oldRabbit.type == OldRabbit.Type.RABBIT )
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
            if ( oldRabbit.type == OldRabbit.Type.RABBIT )
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
