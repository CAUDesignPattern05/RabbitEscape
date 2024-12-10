package rabbitescape.engine.behaviours.actions;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.*;
import rabbitescape.engine.token.BrollyToken;
import rabbitescape.engine.token.ClimbToken;
import rabbitescape.engine.token.Token;

import java.util.Map;

public class ActionHandler extends BehaviourHandler {
    private final Behaviour brollychuting;
    private final Behaviour climbing;
    private final Behaviour walking;
    private final Behaviour falling;

    private Behaviour action;
    private boolean climbingAbility;
    private boolean brollychutingAbility;

    public ActionHandler()
    {
        brollychuting = new Brollychuting( this );
        climbing = new Climbing( this );
        walking = new Walking( this );
        falling = new Falling( this );

        action = walking;
        climbingAbility = false;
        brollychutingAbility = false;
    }

    public State newState( BehaviourTools tool )
    {
        return action.newState( tool );
    }

    public State newMoveState( BehaviourTools tool )
    {
        return walking.newState( tool );
    }

    public boolean behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    )
    {
        return action.behave( world, behaviourExecutor, state );
    }

    @Override
    public void handleRequest(World world,
        BehaviourExecutor behaviourExecutor,
        State state) {
        boolean handled = this.behave(world, behaviourExecutor, behaviourExecutor.getState());
        if (nextHandler != null && !handled) {
            nextHandler.handleRequest(world, behaviourExecutor, behaviourExecutor.getState());
        }
    }

    public void moveBehave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    )
    {
        walking.behave( world, behaviourExecutor, state );
    }

    public void saveState( Map<String, String> saveState )
    {
        action.saveState( saveState );
    }

    public void restoreFromState( Map<String, String> saveState )
    {
        action.restoreFromState( saveState );
    }

    public void setBehaviour( Token item )
    {
        if ( item == null )
        {
            return;
        }
        else if ( item instanceof BrollyToken )
        {
            brollychutingAbility = true;
            setBehaviour( getWalkingBehaviour() );
        }
        else if ( item instanceof ClimbToken )
        {
            climbingAbility = true;
            setBehaviour( getWalkingBehaviour() );
        }
        else
        {
            action = item.createAction(this);
        }
    }

    protected void setBehaviour( Behaviour behaviour )
    {
        this.action = behaviour;
        this.action.clearMemberVariables();
    }

    protected Behaviour getWalkingBehaviour() { return walking; }

    protected Behaviour getFallingBehaviour()
    {
        return falling;
    }

    protected Behaviour getBrollychutingBehaviour() { return brollychuting; }

    protected Behaviour getClimbingBehaviour() { return climbing; }

    public boolean isClimbingAbility() { return climbingAbility; }

    public boolean isBrollychutingAbility()
    {
        return brollychutingAbility;
    }

    public boolean isExploding() { return action instanceof Exploding; }
}
