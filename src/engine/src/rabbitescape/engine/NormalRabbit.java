package rabbitescape.engine;

import rabbitescape.engine.behaviours.Drowning;
import rabbitescape.engine.behaviours.Exiting;

public class NormalRabbit extends Rabbit
{
    public NormalRabbit( int x, int y, Direction dir)
    {
        super( x, y, dir );
        createBehaviours();
    }

    private void createBehaviours()
    {
        Drowning drowning = new Drowning();
        Exiting exiting = new Exiting();

        super.addBehaviour( drowning );
        super.addBehaviour( exiting );
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
