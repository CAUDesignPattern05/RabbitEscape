package rabbitescape.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static rabbitescape.engine.ChangeDescription.State.*;

import org.junit.Before;
import org.junit.Test;
//import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ObserverPatternTest {

    public class TestRabbitObserver implements RabbitObserver {
        public int total_rabbit_count = 0;
        public int current_rabbit_count = 0;
        public int rabbit_death_count = 0;
        public int rabbit_exit_count = 0;

        @Override
        public void updateBirth(BehaviourExecutor behaviourExecutor) {
            total_rabbit_count++;
            current_rabbit_count++;
        }

        @Override
        public void updateDeath(BehaviourExecutor behaviourExecutor) {
            current_rabbit_count--;
            rabbit_death_count++;
        }

        @Override
        public void updateExiting(BehaviourExecutor behaviourExecutor) {
            current_rabbit_count--;
            rabbit_exit_count++;
        }

        public int getTotalRabbitCount() {
            return total_rabbit_count;
        }

        public int getCurrentRabbitCount() {
            return current_rabbit_count;
        }

        public int getRabbitDeathCount() {
            return rabbit_death_count;
        }

        public int getRabbitExitCount() {
            return rabbit_exit_count;
        }
    }

    private TestRabbitObserver testObserver;
    private TestRabbitObserver testObserver2;
    private BehaviourExecutor rabbit1;
    private BehaviourExecutor rabbit2;
    private BehaviourExecutor rabbit3;
    private Thing entrance;

    @Before
    public void setUp() {
        // 테스트 환경을 초기화하는 코드


        testObserver = new TestRabbitObserver();
        testObserver2 = new TestRabbitObserver();

        entrance = new Entrance( 0, 0 );
        entrance.registerObserver( testObserver, entrance );

        rabbit1 = new Rabbit( 0,0,Direction.RIGHT );
        rabbit1.registerObserver( testObserver, rabbit1 );
        rabbit2 = new Rabbit( 0,0,Direction.RIGHT );
        rabbit2.registerObserver( testObserver, rabbit2 );
        rabbit3 = new Rabbit( 0,0,Direction.RIGHT );
        rabbit3.registerObserver( testObserver, rabbit3 );


    }

    @Test
    public void testRegisterObserver()
    {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.registerObserver( testObserver2, entrance );
        entrance.notifyBirth( entrance, rabbit1 );
        assertThat( testObserver.getTotalRabbitCount(), equalTo( 1 ) );
        assertThat( testObserver.getCurrentRabbitCount(), equalTo( 1 ) );
        assertThat( testObserver.getRabbitExitCount(), equalTo( 0 ) );
        assertThat( testObserver.getRabbitDeathCount(), equalTo( 0 ) );

        assertThat( testObserver2.getTotalRabbitCount(), equalTo( 1 ) );
        assertThat( testObserver2.getCurrentRabbitCount(), equalTo( 1 ) );
        assertThat( testObserver2.getRabbitExitCount(), equalTo( 0 ) );
        assertThat( testObserver2.getRabbitDeathCount(), equalTo( 0 ) );

    }

    @Test
    public void testRemoveObserver()
    {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.removeObserver( testObserver, entrance );
        entrance.notifyBirth( entrance, rabbit1 );
        assertThat( testObserver.getTotalRabbitCount(), equalTo( 0 ) );
        assertThat( testObserver.getCurrentRabbitCount(), equalTo( 0 ) );
        assertThat( testObserver.getRabbitExitCount(), equalTo( 0 ) );
        assertThat( testObserver.getRabbitDeathCount(), equalTo( 0 ) );

    }

    @Test
    public void testNotifyBirth1() {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.notifyBirth(entrance, rabbit1);
        assertThat(testObserver.getTotalRabbitCount(), equalTo(1));
        assertThat(testObserver.getCurrentRabbitCount(), equalTo(1));
        assertThat(testObserver.getRabbitExitCount(), equalTo(0));
        assertThat(testObserver.getRabbitDeathCount(), equalTo(0));
    }

    @Test
    public void testNotifyBirth2() {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.notifyBirth(entrance, rabbit1);
        entrance.notifyBirth(entrance, rabbit2);
        assertThat(testObserver.getTotalRabbitCount(), equalTo(2));
        assertThat(testObserver.getCurrentRabbitCount(), equalTo(2));
        assertThat(testObserver.getRabbitExitCount(), equalTo(0));
        assertThat(testObserver.getRabbitDeathCount(), equalTo(0));    }

    @Test
    public void testNotifyDeath1() {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.notifyBirth(entrance, rabbit1);
        rabbit1.notifyDeath(rabbit1);
        assertThat(testObserver.getTotalRabbitCount(), equalTo(1));
        assertThat(testObserver.getCurrentRabbitCount(), equalTo(0));
        assertThat(testObserver.getRabbitExitCount(), equalTo(0));
        assertThat(testObserver.getRabbitDeathCount(), equalTo(1));    }

    @Test
    public void testNotifyExit1() {
        // Rabbit이 태어날 때 observer가 updateBirth()를 호출하는지 확인
        entrance.notifyBirth(entrance, rabbit1);
        rabbit1.notifyExiting(rabbit1);
        assertThat(testObserver.getTotalRabbitCount(), equalTo(1));
        assertThat(testObserver.getCurrentRabbitCount(), equalTo(0));
        assertThat(testObserver.getRabbitExitCount(), equalTo(1));
        assertThat(testObserver.getRabbitDeathCount(), equalTo(0));    }


    @Test
    public void complexTest(){
        entrance.notifyBirth(entrance, rabbit1);
        entrance.notifyBirth(entrance, rabbit2);
        rabbit1.notifyDeath(rabbit1);
        entrance.notifyBirth(entrance, rabbit3);
        rabbit3.notifyExiting(rabbit3);
        assertThat(testObserver.getTotalRabbitCount(), equalTo(3));
        assertThat(testObserver.getCurrentRabbitCount(), equalTo(1));
        assertThat(testObserver.getRabbitExitCount(), equalTo(1));
        assertThat(testObserver.getRabbitDeathCount(), equalTo(1));
    }





}
