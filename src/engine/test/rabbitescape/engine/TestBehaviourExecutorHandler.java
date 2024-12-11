package rabbitescape.engine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import rabbitescape.engine.factory.RabbitFactory;
import rabbitescape.engine.factory.ThingFactory;
import rabbitescape.engine.factory.blockfactory.BlockFactory;
import rabbitescape.engine.util.VariantGenerator;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static rabbitescape.engine.CellularDirection.LEFT;
import static rabbitescape.engine.CellularDirection.UP;
import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.textworld.TextWorldManip.createWorld;
import static rabbitescape.engine.textworld.TextWorldManip.renderWorld;

public class TestBehaviourExecutorHandler
{
    @Test
    public void Rabbit_handler_Burning()
    {
        World world = createWorld(
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
        );
        BlockFactory blockFactory = new BlockFactory();
        ThingFactory thingFactory= new ThingFactory();
        world.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 2,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 3,2, new VariantGenerator(1) ));
        world.things.add(thingFactory.create( 'A', 0, 1,new VariantGenerator(1) ));
        world.changes.apply();

        BehaviourExecutor rabbit = new Rabbit( 1, 1, Direction.RIGHT);
        rabbit.step( world );
        assertThat(rabbit.getState(), equalTo(RABBIT_BURNING));

        BehaviourExecutor rabbot = new Rabbot( 1, 1, Direction.RIGHT);
        rabbot.step( world );
        assertThat(rabbot.getState(), equalTo(RABBIT_BURNING));
    }

    @Test
    public void Rabbit_handler_OutOfBounding()
    {
        World world = createWorld(
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
        );
        BlockFactory blockFactory = new BlockFactory();
        world.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 2,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 3,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 0,2, new VariantGenerator(1) ));
        world.changes.apply();

        BehaviourExecutor rabbit = new Rabbit( 0, 1, Direction.LEFT);
        rabbit.step( world );
        assertThat(rabbit.getState(), equalTo(RABBIT_OUT_OF_BOUNDS));

        BehaviourExecutor rabbot = new Rabbot( 0, 1, Direction.LEFT);
        rabbot.step( world );
        assertThat(rabbot.getState(), equalTo(RABBIT_OUT_OF_BOUNDS));
    }

    @Test
    public void Rabbit_handler_Exiting()
    {
        World world = createWorld(
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
        );
        BlockFactory blockFactory = new BlockFactory();
        ThingFactory thingFactory= new ThingFactory();
        world.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 2,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 3,2, new VariantGenerator(1) ));
        world.things.add(thingFactory.create( 'O', 0, 1,new VariantGenerator(1) ));
        world.changes.apply();

        BehaviourExecutor rabbit = new Rabbit( 1, 1, Direction.RIGHT);
        rabbit.step( world );
        assertThat(rabbit.getState(), equalTo(RABBIT_ENTERING_EXIT));

        BehaviourExecutor rabbot = new Rabbot( 1, 1, Direction.RIGHT);
        rabbot.step( world );
        assertThat(rabbot.getState(), equalTo(RABBIT_WALKING_LEFT));
    }

    @Test
    public void Rabbit_handler_RabbotCrashing()
    {
        World world = createWorld(
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
        );
        BlockFactory blockFactory = new BlockFactory();
        RabbitFactory rabbitFactory= new RabbitFactory();
        world.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 2,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 3,2, new VariantGenerator(1) ));
        world.changes.enterRabbit(rabbitFactory.create( 'r', 2, 1,new VariantGenerator(1) ));
        world.changes.apply();

        BehaviourExecutor rabbot = new Rabbot( 3, 1, Direction.LEFT);
        rabbot.step( world );
        assertThat(rabbot.getState(), equalTo(RABBIT_CRASHING));
    }

    @Test
    public void Rabbit_handler_RabbotWaiting()
    {
        World world = createWorld(
            "     ",
            "     ",
            "     ",
            "     ",
            "     "
        );
        BlockFactory blockFactory = new BlockFactory();
        RabbitFactory rabbitFactory= new RabbitFactory();
        world.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 2,2, new VariantGenerator(1) ));
        world.changes.addBlock(blockFactory.create( '#', 3,2, new VariantGenerator(1) ));
        world.changes.enterRabbit(rabbitFactory.create( 'r', 1, 1,new VariantGenerator(1) ));
        world.changes.apply();

        BehaviourExecutor rabbot = new Rabbot( 3, 1, Direction.LEFT);
        rabbot.step( world );
        assertThat(rabbot.getState(), equalTo(RABBIT_WAITING_LEFT));
    }
}
