package rabbitescape.engine.token;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;

import org.mockito.junit.MockitoJUnitRunner;
import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.block.Block;
import rabbitescape.engine.util.Dimension;

@RunWith( MockitoJUnitRunner.class )
public class TokenTest {

    private World world;
    private Token token;

    private MockedStatic<BehaviourTools> behaviourToolsMock;
    private MockedStatic<BridgeTools> bridgeToolsMock;

    @Before
    public void setUp() {
        world = mock(World.class);

        behaviourToolsMock = mockStatic(BehaviourTools.class);
        bridgeToolsMock = mockStatic(BridgeTools.class);
    }

    @Test
    public void construct_withoutWorld() {
        token = new BashToken( 1, 1 );

        assertEquals(1, token.x);
        assertEquals(1, token.y);
        assertEquals(Token.Type.bash, token.type);
        assertEquals(State.TOKEN_BASH_ON_SLOPE, token.state);
    }

    @Test
    public void construct_withWorld_blockOnSlope() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn( true );

        token = new BashToken( 1, 1, world );

        assertEquals(1, token.x);
        assertEquals(1, token.y);
        assertEquals(Token.Type.bash, token.type);
        assertEquals(State.TOKEN_BASH_ON_SLOPE, token.state);
    }

    @Test
    public void construct_withWorld_blockNotOnSlope() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn(false);

        token = new BashToken(1, 1, world);

        assertEquals(1, token.x);
        assertEquals(1, token.y);
        assertEquals(Token.Type.bash, token.type);
        assertEquals(State.TOKEN_BASH_STILL, token.state);
    }

    @Test
    public void calcNewState_belowBlockFlat_onBlockFlat() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn(false);
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(true);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(belowBlock)).thenReturn(false);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_STILL, token.state);
    }

    @Test
    public void calcNewState_belowBlockFlat_onBlockSlope() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn(true);
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(true);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(belowBlock)).thenReturn(false);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_ON_SLOPE, token.state);
    }

    @Test
    public void calcNewState_belowBlockSlope_onBlockFlat() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn(false);
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(false);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(belowBlock)).thenReturn(true);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_STILL, token.state);
    }

    @Test
    public void calcNewState_belowBlockSlope_onBlockSlope() {
        Block onBlock = mock(Block.class);
        when(world.getBlockAt(1, 1)).thenReturn(onBlock);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(onBlock)).thenReturn(true);
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(false);
        behaviourToolsMock.when(() -> BehaviourTools.isSlope(belowBlock)).thenReturn(true);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_ON_SLOPE, token.state);
    }

    @Test
    public void calcNewState_belowBlockSlope_onBlockNotExisting_someoneBridging() {
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(false);
        bridgeToolsMock.when(() -> BridgeTools.someoneIsBridgingAt(world, 1, 1)).thenReturn(true);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_STILL, token.state);
    }

    @Test
    public void calcNewState_belowBlockSlope_onBlockNotExisting_someoneNotBridging() {
        Block belowBlock = mock(Block.class);
        when(world.getBlockAt(1, 2)).thenReturn(belowBlock);
        behaviourToolsMock.when(() -> BehaviourTools.s_isFlat(belowBlock)).thenReturn(false);
        bridgeToolsMock.when(() -> BridgeTools.someoneIsBridgingAt(world, 1, 1)).thenReturn(false);

        token = new BashToken(1, 1);
        token.calcNewState(world);

        assertEquals(State.TOKEN_BASH_FALLING, token.state);
    }

    @Test
    public void step_fallingState_yOutOfBounds() {
        when(world.getSize()).thenReturn(new Dimension(2, 2));
        WorldChanges changes = mock(WorldChanges.class);
        when(world.getChanges()).thenReturn(changes);
        token = new BashToken(1, 1);
        token.state = State.TOKEN_BASH_FALLING;

        token.step(world);

        assertEquals(2, token.y);
        verify(world, atLeastOnce()).getChanges();
        verify(changes, atLeastOnce()).removeToken(token);
    }

    @Test
    public void step_fallingState_yInBounds() {
        when(world.getSize()).thenReturn(new Dimension(3, 3));
        token = new BashToken(1, 1);
        token.state = State.TOKEN_BASH_FALLING;

        token.step(world);

        assertEquals(2, token.y);
        verify(world, never()).getChanges();
    }

    @Test
    public void step_notFallingState() {
        token = new BashToken(1, 1);
        token.state = State.TOKEN_BASH_STILL;

        token.step(world);

        assertEquals(1, token.y);
    }

    @Test
    public void name() {
        assertEquals("Bash", Token.name(Token.Type.bash));
    }

    @After
    public void tearDown() {
        behaviourToolsMock.close();
        bridgeToolsMock.close();
    }
}