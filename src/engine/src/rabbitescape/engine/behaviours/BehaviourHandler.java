package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

import java.util.Map;

public class BehaviourHandler {
    private final Behaviour bashing;
    private final Behaviour blocking;
    private final Behaviour bridging;
    private final Behaviour brollychuting;
    private final Behaviour climbing;
    private final Behaviour digging;
    private final Behaviour exploding;
    private final Behaviour walking;

    private Behaviour behaviour;
    private boolean climbingAbility;
    private boolean brollychutingAbility;
    private Token item;
    private Token oldItem;

    public BehaviourHandler() {
        bashing = new Bashing(this);
        blocking = new Blocking(this);
        bridging = new Bridging(this);
        brollychuting = new Brollychuting(this);
        climbing = new Climbing(this);
        digging = new Digging(this);
        exploding = new Exploding(this);
        walking = new Walking(this);

        behaviour = walking;
        climbingAbility = false;
        brollychutingAbility = false;
        item = null;
        oldItem = null;
    }

    public State newState(BehaviourTools tool) { return behaviour.newState(tool); }

    public void behave(World world, BehaviorExecutor behaviorExecutor, State state) {
        behaviour.behave(world, behaviorExecutor, state);
    }

    public void saveState(Map<String, String> saveState) { behaviour.saveState(saveState); }

    public void restoreFromState(Map<String, String> saveState) { behaviour.restoreFromState(saveState); }

    public void setItem( Token item ) { this.item = item; }

    public void setBehaviour() {
        if (item == null) return;

        switch (item.getType()) {
            case bash:
                behaviour = bashing;
                break;
            case block:
                behaviour = blocking;
                break;
            case bridge:
                behaviour = bridging;
                break;
            case brolly:
                brollychutingAbility = true;
                break;
            case climb:
                climbingAbility = true;
                break;
            case dig:
                behaviour = digging;
                break;
            case explode:
                behaviour = exploding;
                break;
            default:
                break;
        }
        oldItem = item;
        item = null;
        behaviour.clearMemberVariables();
    }
}
