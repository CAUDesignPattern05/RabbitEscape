package rabbitescape.engine;

import rabbitescape.engine.behaviours.basicBehaviours.BasicHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

public class Rabbot extends BehaviourExecutor
{
    public Rabbot(int x, int y, Direction dir) {
        super(x, y, dir);
        fatalHeight = 5;

        BasicHandler deathHandler = new BasicHandler();
        RabbotHandler rabbotHandler = new RabbotHandler();

        deathHandler.setNextHandler( rabbotHandler );
        actionHandler.setNextHandler( deathHandler );
    }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName.replaceFirst(
                "^rabbit", "rabbot" );
    }
}
