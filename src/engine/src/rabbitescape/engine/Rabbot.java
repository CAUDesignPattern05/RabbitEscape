package rabbitescape.engine;

import rabbitescape.engine.behaviours.commonBehaviours.CommonHandler;
import rabbitescape.engine.behaviours.rabbotBehaviours.RabbotHandler;

public class Rabbot extends BehaviourExecutor
{
    public Rabbot(int x, int y, Direction dir) {
        super(x, y, dir);
        fatalHeight = 5;

        CommonHandler commonHandler = new CommonHandler();
        RabbotHandler rabbotHandler = new RabbotHandler();

        actionHandler.setNextHandler( commonHandler );
        commonHandler.setNextHandler( rabbotHandler );
    }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName.replaceFirst(
                "^rabbit", "rabbot" );
    }
}
