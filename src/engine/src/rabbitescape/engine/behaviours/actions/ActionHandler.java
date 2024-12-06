package rabbitescape.engine.behaviours.actions;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.*;

import java.util.Map;

public class ActionHandler extends BehaviourHandler {
    private final Behaviour bashing;
    private final Behaviour blocking;
    private final Behaviour bridging;
    private final Behaviour brollychuting;
    private final Behaviour climbing;
    private final Behaviour digging;
    private final Behaviour exploding;
    private final Behaviour walking;
    private final Behaviour falling;

    private Behaviour action;
    private boolean climbingAbility;
    private boolean brollychutingAbility;

    public ActionHandler()
    {
        bashing = new Bashing( this );
        blocking = new Blocking( this );
        bridging = new Bridging( this );
        brollychuting = new Brollychuting( this );
        climbing = new Climbing( this );
        digging = new Digging( this );
        exploding = new Exploding( this );
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

    public void behave(
        World world,
        BehaviourExecutor behaviourExecutor,
        State state
    )
    {
        action.behave( world, behaviourExecutor, state );
    }

    @Override
    public void handleRequest(World world,
        BehaviourExecutor behaviourExecutor,
        State state) {

        BehaviourTools tool = new BehaviourTools(behaviourExecutor, world);
        State newState = this.newState(tool);
        if (newState != null) behaviourExecutor.setState(newState);

        this.behave(world, behaviourExecutor, state);
        if (nextHandler != null) {
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

        switch ( item.getType() )
        {
            case bash:
                action = bashing;
                break;
            case block:
                action = blocking;
                break;
            case bridge:
                action = bridging;
                break;
            case brolly:
                brollychutingAbility = true;
                break;
            case climb:
                climbingAbility = true;
                break;
            case dig:
                action = digging;
                break;
            case explode:
                action = exploding;
                break;
            default:
                break;
        }
        action.clearMemberVariables();
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
}
