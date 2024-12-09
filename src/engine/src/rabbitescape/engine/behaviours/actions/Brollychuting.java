package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.block.Block;

public class Brollychuting extends Action
{
    private boolean hasAbility = true;

    public Brollychuting( ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState(BehaviourTools t)
    {
        Block below = t.blockBelow();
        
        if ( below != null )
        {
            actionHandler.setBehaviour(actionHandler.getFallingBehaviour());
            return actionHandler.newState(t);
        }

        return RABBIT_BROLLYCHUTING;
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        behaviourExecutor.y = behaviourExecutor.y + 1;
        return false;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Brollychuting.hasAbility", hasAbility
        );

    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        hasAbility = BehaviourState.restoreFromState(
            saveState, "Brollychuting.hasAbility", hasAbility
        );
    }
}
