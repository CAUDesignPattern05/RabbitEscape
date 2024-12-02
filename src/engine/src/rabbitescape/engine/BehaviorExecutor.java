package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Walking;
import sun.awt.geom.Crossings;

public abstract class BehaviorExecutor extends Thing implements Comparable<BehaviorExecutor>
{
    private Direction direction;
    private Token item;
    private Behaviour behaviour;
    private int index;

    public BehaviorExecutor(int x, int y, Direction dir) {
        super( x, y, RABBIT_WALKING_LEFT );
        direction = dir;
        item = null;
        behaviour = new Walking();
        index = -1;
    }

    @Override
    public void calcNewState(World world) {

    }

    @Override
    public void step( World world ) {

    }

    @Override
    public String overlayText() {
        if (direction == Direction.LEFT) {
            return String.format("<[%d] ", index);
        } else if (direction == Direction.RIGHT) {
            return String.format(" [%d]>", index);
        } else {
            throw new RuntimeException( "Rabbit should only be left or right");
        }
    }

    @Override
    public int compareTo(BehaviorExecutor r) {
        return this.index - r.index;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BehaviorExecutor)) {
            return false;
        }
        return ((BehaviorExecutor) o).index == index;
    }

    @Override
    public int hashCode() { return index; }

    @Override
    public abstract String stateName();
}
