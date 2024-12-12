package rabbitescape.engine;

import org.junit.Test;
import rabbitescape.engine.factory.blockfactory.BlockFactory;
import rabbitescape.engine.textworld.TextWorldManip;
import rabbitescape.engine.token.Token;
import rabbitescape.engine.util.VariantGenerator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBehaviourStrategyPattern
{
    @Test
    public void BehaviourStrategyPatternTest()
    {
        World testWorld = TextWorldManip.createWorldCanBehaviour( new IgnoreWorldStatsListener() );
        BlockFactory blockFactory = new BlockFactory();
        testWorld.changes.addBlock(blockFactory.create( '#', 1,2, new VariantGenerator(1) ));
        testWorld.changes.addToken(1, 1, Token.Type.bash);
        testWorld.changes.apply();
        BehaviourExecutor ri = new Rabbit( 1, 1, Direction.LEFT);
        ri.calcNewState( testWorld );
        assertThat(ri.stateName(), equalTo("rabbit_bashing_uselessly_left"));

        testWorld.changes.addToken(1, 1, Token.Type.explode);
        testWorld.changes.apply();
        ri.calcNewState( testWorld );
        assertThat(ri.stateName(), equalTo("rabbit_exploding"));
    }
}
