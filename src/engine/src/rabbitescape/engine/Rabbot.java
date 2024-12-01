package rabbitescape.engine;

import rabbitescape.engine.behaviours.*;

public class Rabbot extends Rabbit
{

    public Rabbot( int x, int y, Direction dir)
    {
        super( x, y, dir );
        createBehaviours();
    }

    private void createBehaviours()
    {
        RabbotCrash rabbotCrash = new RabbotCrash();
        RabbotWait rabbotWait = new RabbotWait();

        super.addBehaviour( rabbotCrash );
        super.addBehaviour( rabbotWait );
    }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName.replaceFirst(
            "^rabbit", "rabbot" );
    }

    /** Rabbots can fall further than rabbits. */
    @Override
    protected int getFatalHeight()
    {
        return 5;
    }

    @Override
    public Rabbot cloneRabbit() {
        return new Rabbot(this.x, this.y, this.dir);
    }
}
