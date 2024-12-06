package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.behaviours.Behaviour;

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
            if ( t.isUpSlope( below ) )
            {
                return t.rl(
                    RABBIT_FALLING_1_ONTO_RISE_RIGHT,
                    RABBIT_FALLING_1_ONTO_RISE_LEFT
                );
            }
            else // Must be a slope in the opposite direction
            {
                return t.rl(
                    RABBIT_FALLING_1_ONTO_LOWER_RIGHT,
                    RABBIT_FALLING_1_ONTO_LOWER_LEFT
                );
            }
        }

        return RABBIT_BROLLYCHUTING;
    }

    @Override
    public void behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        if ( state == RABBIT_BROLLYCHUTING ) {
            behaviourExecutor.y = behaviourExecutor.y + 1;
        }
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
