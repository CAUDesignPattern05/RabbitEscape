package rabbitescape.engine;

import rabbitescape.engine.behaviours.Drowning;
import rabbitescape.engine.behaviours.Exiting;
import rabbitescape.engine.behaviours.RabbotCrash;
import rabbitescape.engine.behaviours.RabbotWait;

public class NormalRabbit extends Rabbit
{
    public NormalRabbit( int x, int y, Direction dir)
    {
        super( x, y, dir );
        createBehaviours();
    }

    private void createBehaviours()
    {
        RabbotCrash rabbotCrash = new RabbotCrash(this);
        RabbotWait rabbotWait = new RabbotWait(this);

        super.addBehaviour( rabbotCrash );
        super.addBehaviour( rabbotWait );
    }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName;
    }

    /** Rabbots can fall further than rabbits. */
    @Override
    protected int getFatalHeight()
    {
        return 4;
    }

    @Override
    public NormalRabbit cloneRabbit() {
        return new NormalRabbit(this.x, this.y, this.dir);
    }
}
