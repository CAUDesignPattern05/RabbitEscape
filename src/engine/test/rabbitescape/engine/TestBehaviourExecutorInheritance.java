package rabbitescape.engine;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBehaviourExecutorInheritance
{
    @Test
    public void RabbitCreationTest()
    {
        BehaviourExecutor r = new Rabbit( 1, 1, Direction.LEFT);
        assertThat(r.stateName(), equalTo("rabbit_walking_left"));
    }

    @Test
    public void RabbotCreationTest()
    {
        BehaviourExecutor r = new Rabbot( 1, 1, Direction.LEFT);
        assertThat(r.stateName(), equalTo("rabbot_walking_left"));
    }
}
