package rabbitescape.engine.behaviours;

import static rabbitescape.engine.Token.Type.*;
import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Exploding extends Behaviour
{

    public Exploding(BehaviourHandler behaviourHandler) { super(behaviourHandler); }


    @Override
    public State newState(BehaviourTools t) {
        return RABBIT_EXPLODING;
    }

    @Override
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        if ( state == RABBIT_EXPLODING ) {
            world.changes.killRabbit(behaviourExecutor);
        }
    }
}
