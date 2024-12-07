package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Behaviour;

public class Climbing extends Action
{
    private boolean hasAbility;
    private boolean abilityActive;

    public Climbing( ActionHandler actionHandler) { super(actionHandler); }

    @Override
    public State newState( BehaviourTools t)
    {
        switch ( t.behaviourExecutor.state )
        {
            case RABBIT_CLIMBING_RIGHT_START:
            case RABBIT_CLIMBING_LEFT_START:
                return newStateStart( t );
            case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
            case RABBIT_CLIMBING_LEFT_CONTINUE_1:
                return newStateCont1( t );
            case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
            case RABBIT_CLIMBING_LEFT_CONTINUE_2:
                return newStateCont2( t );
            default:
                return newStateNotClimbing( t );
        }
    }

    private State newStateStart( BehaviourTools t )
    {
        Block endBlock = t.blockAboveNext();

        if ( t.isWall( endBlock ) )
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_CONTINUE_2,
                RABBIT_CLIMBING_LEFT_CONTINUE_2
            );
        }
        else
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_END,
                RABBIT_CLIMBING_LEFT_END
            );
        }
    }

    private State newStateCont1( BehaviourTools t )
    {
        return t.rl(
            RABBIT_CLIMBING_RIGHT_CONTINUE_2,
            RABBIT_CLIMBING_LEFT_CONTINUE_2
        );
    }

    private State newStateCont2( BehaviourTools t )
    {
        Block aboveBlock = t.blockAbove();

        if ( t.isRoof( aboveBlock ) )
        {
            abilityActive = false;
            return t.rl(
                RABBIT_CLIMBING_RIGHT_BANG_HEAD,
                RABBIT_CLIMBING_LEFT_BANG_HEAD
            );
        }

        Block endBlock = t.blockAboveNext();

        if ( t.isWall( endBlock ) )
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_CONTINUE_1,
                RABBIT_CLIMBING_LEFT_CONTINUE_1
            );
        }
        else
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_END,
                RABBIT_CLIMBING_LEFT_END
            );
        }
    }

    private State newStateNotClimbing( BehaviourTools t )
    {
        int nextX = t.nextX();
        int nextY = t.nextY();
        Block nextBlock = t.world.getBlockAt( nextX, nextY );
        Block aboveBlock = t.world.getBlockAt( t.behaviourExecutor.x, t.behaviourExecutor.y - 1 );

        if ( !t.isRoof( aboveBlock ) && t.isWall( nextBlock ) )
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_START,
                RABBIT_CLIMBING_LEFT_START
            );
        }

        actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
        return actionHandler.newState(t);
    }

    @Override
    public boolean behave( World world, BehaviourExecutor behaviourExecutor, State state )
    {
        BehaviourTools t = new BehaviourTools( behaviourExecutor, world );

        if( t.rabbitIsClimbing() )
        { // Can't be both on a wall and on a slope.
            behaviourExecutor.setOnSlope(false);
        }

        switch ( state )
        {
            case RABBIT_CLIMBING_RIGHT_START:
            case RABBIT_CLIMBING_LEFT_START: {
                abilityActive = true;
                break;
            }
            case RABBIT_CLIMBING_RIGHT_END:
            case RABBIT_CLIMBING_LEFT_END: {
                behaviourExecutor.x = t.nextX();
                --behaviourExecutor.y;
                if ( t.hereIsUpSlope() ) {
                    behaviourExecutor.setOnSlope(true);
                }
                abilityActive = false;
                break;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
            case RABBIT_CLIMBING_LEFT_CONTINUE_1: {
                abilityActive = true;
                break;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
            case RABBIT_CLIMBING_LEFT_CONTINUE_2: {
                abilityActive = true;
                --behaviourExecutor.y;
                break;
            }
            case RABBIT_CLIMBING_RIGHT_BANG_HEAD:
            case RABBIT_CLIMBING_LEFT_BANG_HEAD: {
                behaviourExecutor.setDirection(opposite( behaviourExecutor.getDirection()));
                actionHandler.setBehaviour(actionHandler.getFallingBehaviour()); // start to fall
                break;
            }
            default: {
                actionHandler.setBehaviour(actionHandler.getWalkingBehaviour());
            }
        }

        return hasAbility;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.hasAbility", hasAbility
        );

        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        hasAbility = BehaviourState.restoreFromState(
            saveState, "Climbing.hasAbility", hasAbility
        );

        abilityActive = BehaviourState.restoreFromState(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public void clearMemberVariables() {
        hasAbility = true;
        abilityActive = true;
    }
}
