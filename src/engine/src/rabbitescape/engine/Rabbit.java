package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.*;

public abstract class Rabbit extends Thing implements Comparable<Rabbit>
{
    public final static int NOT_INDEXED = 0;
    private final List<Behaviour> behaviours;
    private final List<Behaviour> behavioursTriggerOrder;

    public int index;

    private Falling falling;

    public Direction dir;
    public boolean onSlope;
    /** Rabbits move up 1 cell to bash from a slope.
     *  Keep a note, so it can be undone.  */
    public boolean slopeBashHop = false;

    public Rabbit( int x, int y, Direction dir)
    {
        super( x, y, RABBIT_WALKING_LEFT );
        this.dir = dir;
        this.onSlope = false;
        behaviours = new ArrayList<>();
        behavioursTriggerOrder = new ArrayList<>();
        createBehaviours();
        index = NOT_INDEXED;
    }

    private void createBehaviours()
    {
        Climbing climbing = new Climbing();
        Digging digging = new Digging();
        Exploding exploding = new Exploding();
        Burning burning = new Burning();
        OutOfBounds outOfBounds = new OutOfBounds();
//        Drowning drowning = new Drowning();
//        Exiting exiting = new Exiting();
        Brollychuting brollychuting = new Brollychuting( climbing, digging );
        falling = new Falling( climbing, brollychuting, getFatalHeight() );
        Bashing bashing = new Bashing();
        Bridging bridging = new Bridging();
        Blocking blocking = new Blocking();
        Walking walking = new Walking();
//        RabbotCrash rabbotCrash = new RabbotCrash();
//        RabbotWait rabbotWait = new RabbotWait();

        behavioursTriggerOrder.add( exploding );
        behavioursTriggerOrder.add( outOfBounds );
        behavioursTriggerOrder.add( burning );
//        behavioursTriggerOrder.add( drowning );
//        behavioursTriggerOrder.add( rabbotCrash );
        behavioursTriggerOrder.add( falling );
//        behavioursTriggerOrder.add( exiting );
        behavioursTriggerOrder.add( brollychuting );
        behavioursTriggerOrder.add( climbing );
        behavioursTriggerOrder.add( bashing );
        behavioursTriggerOrder.add( digging );
        behavioursTriggerOrder.add( bridging );
        behavioursTriggerOrder.add( blocking );
//        behavioursTriggerOrder.add( rabbotWait );
        behavioursTriggerOrder.add( walking );

        behaviours.add( exploding );
        behaviours.add( outOfBounds );
        behaviours.add( burning );
//        behaviours.add( drowning );
//        behaviours.add( rabbotCrash );
        behaviours.add( falling );
//        behaviours.add( exiting );
        behaviours.add( brollychuting );
        behaviours.add( bashing );
        behaviours.add( digging );
        behaviours.add( bridging );
        behaviours.add( blocking );
        behaviours.add( climbing );
//        behaviours.add( rabbotWait );
        behaviours.add( walking );

        assert behavioursTriggerOrder.size() == behaviours.size();
    }

    // register observer
    public void addBehaviour(Behaviour behaviour)
    {
        addTobehavioursTriggerOrder(behaviour);
        addToBehaviours(behaviour);
    }

    // 로직 변경 필요...
    // 순서를 어떻게 정하는게 맞을까....
    private void addTobehavioursTriggerOrder(Behaviour behaviour)
    {
        behavioursTriggerOrder.add(behaviour);
    }

    private void addToBehaviours(Behaviour behaviour)
    {
        behaviours.add(behaviour);
    }

    public void deleteBehaviour(Behaviour behaviour)
    {
        deleteFrombehavioursTriggerOrder(behaviour);
        deleteFrombehaviours(behaviour);
    }

    private void deleteFrombehavioursTriggerOrder(Behaviour behaviour)
    {
        int idx = behavioursTriggerOrder.indexOf( behaviour );
        if (idx >= 0)   behavioursTriggerOrder.remove(idx);
    }

    private void deleteFrombehaviours(Behaviour behaviour)
    {
        int idx = behaviours.indexOf( behaviour );
        if (idx >= 0)   behaviours.remove(idx);
    }

    public boolean isFallingToDeath()
    {
        return falling.isFallingToDeath();
    }

    @Override
    public void calcNewState( World world )
    {
        for ( Behaviour behaviour : behavioursTriggerOrder )
        {
            behaviour.triggered = false;
        }

        for ( Behaviour behaviour : behavioursTriggerOrder )
        {
            behaviour.triggered = behaviour.checkTriggered( this, world );
            if ( behaviour.triggered )
            {
                cancelAllBehavioursExcept( behaviour );
            }
        }

        boolean done = false;
        for ( Behaviour behaviour : behaviours )
        {

            State thisState = behaviour.newState(
                new BehaviourTools( this, world ), behaviour.triggered );

            if ( thisState != null && !done )
            {
                state = thisState;
                done = true;
            }
        }

    }

    private void cancelAllBehavioursExcept( Behaviour exception )
    {
        for ( Behaviour behaviour : behaviours )
        {
            if ( behaviour != exception )
            {
                behaviour.cancel();
            }
        }
    }

    public void possiblyUndoSlopeBashHop( World world )
    {
        if ( !slopeBashHop )
        {
            return;
        }
        BehaviourTools t = new BehaviourTools( this, world );
        if ( t.blockHere() != null ||
            !BehaviourTools.isSlope( t.blockBelow() ) )
        {
            return;
        }
        ++y;
        slopeBashHop = false;
    }

    @Override
    public void step( World world )
    {
        for ( Behaviour behaviour : behaviours )
        {
            boolean handled = behaviour.behave( world, this, state );
            if ( handled )
            {
                break;
            }
        }
    }

    @Override
    public Map<String, String> saveState( boolean runtimeMeta )
    {
        Map<String, String> ret = new HashMap<String, String>();
        if ( !runtimeMeta )
        {
            return ret;
        }

        BehaviourState.addToStateIfGtZero( ret, "index", index );
        BehaviourState.addToStateIfTrue( ret, "onSlope", onSlope );

        for ( Behaviour behaviour : behaviours )
        {
            behaviour.saveState( ret );
        }

        return ret;
    }

    @Override
    public void restoreFromState( Map<String, String> state )
    {
        index = BehaviourState.restoreFromState( state, "index", -1 );

        onSlope = BehaviourState.restoreFromState(
            state, "onSlope", false
        );

        for ( Behaviour behaviour : behaviours )
        {
            behaviour.restoreFromState( state );
        }
    }

    @Override
    public String overlayText()
    {
        String fmt;
        switch ( dir )
        {
        case LEFT:
            fmt = "<[%d] ";
            break;
        case RIGHT:
            fmt = " [%d]>";
            break;
        default:
            throw new RuntimeException( "Rabbit should only be left or right");
        }
        return String.format( fmt, index ) ;
    }

    @Override
    public int compareTo( Rabbit r )
    {
        return this.index - r.index;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( null == o || !( o instanceof Rabbit ) )
        {
            return false;
        }
        return ( (Rabbit)o ).index == this.index;
    }

    @Override
    public int hashCode()
    {
        return index;
    }

    /** Rabbots can fall further than rabbits. */
    protected abstract int getFatalHeight();

    public abstract Rabbit cloneRabbit(); // 각 subclass에서 cloneRabbit 메서드를 오버라이드하도록 강제

}
