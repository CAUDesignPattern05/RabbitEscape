package rabbitescape.engine.behaviours.rabbotBehaviours;

import rabbitescape.engine.BehaviourExecutor;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Behaviour;
import rabbitescape.engine.behaviours.BehaviourHandler;
import rabbitescape.engine.behaviours.rabbitBehaviours.Burning;
import rabbitescape.engine.behaviours.rabbitBehaviours.Drowning;

import java.util.ArrayList;
import java.util.List;

public class RabbotHandler extends BehaviourHandler
{
    private final List<Behaviour> behaviours;

    public RabbotHandler() {
        behaviours = new ArrayList<>();
        createBehaviours();
    }

    private void createBehaviours() {
        Behaviour rabbotCrashing = new RabbotCrashing(this);
        Behaviour rabbotWaiting = new RabbotWaiting(this);

        behaviours.add( rabbotCrashing );
        behaviours.add( rabbotWaiting );
    }

    @Override
    public State newState( BehaviourTools tool ) {
        for ( Behaviour behaviour : behaviours )
        {
            State thisState = behaviour.newState( tool );
            if ( thisState != null)
            {
                return thisState;
            }
        }
        return null;
    }

    @Override
    public boolean behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    ) {
        for ( Behaviour behaviour : behaviours )
        {
            boolean handled = behaviour.behave( world, behaviourExecutor, state );
            if ( handled )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleRequest(World world,
        BehaviourExecutor behaviourExecutor,
        State state) {

        BehaviourTools tool = new BehaviourTools(behaviourExecutor, world);
        State newState = this.newState(tool);
        if (newState != null) behaviourExecutor.setState(newState);

        boolean handled = this.behave(world, behaviourExecutor, behaviourExecutor.getState());
        if (nextHandler != null && !handled) {
            nextHandler.handleRequest(world, behaviourExecutor, behaviourExecutor.getState());
        }
    }
}
