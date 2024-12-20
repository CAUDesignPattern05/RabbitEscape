package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

public class Exploding extends Action
{

    public Exploding( ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState(BehaviourTools t) {
        return RABBIT_EXPLODING;
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        if ( state == RABBIT_EXPLODING ) {
            notifyDeath(behaviourExecutor);
        }

        return true;
    }
}
