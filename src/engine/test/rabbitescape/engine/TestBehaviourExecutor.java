package rabbitescape.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static rabbitescape.engine.ChangeDescription.State.*;

import org.junit.Test;

public class TestBehaviourExecutor
{
    @Test
    public void RabbitCreationTest()
    {
        BehaviourExecutor r = new Rabbit( 1, 1, Direction.LEFT);
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbit_walking_left"));
    }

    @Test
    public void RabbotCreationTest()
    {
        BehaviourExecutor r = new Rabbot( 1, 1, Direction.LEFT);
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbot_walking_left"));
    }
}
