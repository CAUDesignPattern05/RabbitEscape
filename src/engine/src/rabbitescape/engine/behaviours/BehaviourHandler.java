package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;

import java.util.Map;

public class BehaviourHandler {
    Behaviour bashing;
    Behaviour blocking;
    Behaviour bridging;
    Behaviour brollychuting;
    Behaviour climbing;
    Behaviour digging;
    Behaviour exploding;
    Behaviour walking;

    Behaviour behaviour;

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
    }

    public State newState(BehaviourTools tool) { behaviour.newState(tool); }

    public void behave(World world, Rabbit rabbit, State state) { behaviour.behave(world, rabbit, state); }

    public void saveState(Map<String, String> saveState) { behaviour.saveState(saveState); }

    public void restoreFromState(Map<String, String> saveState) { behaviour.restoreFromState(saveState); }

    public void setBehaviour(Token item) {
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
                behaviour = brollychuting;
                break;
            case climb:
                behaviour = climbing;
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
        behaviour.clearMemberVariables();
    }
}
