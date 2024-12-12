package rabbitescape.engine.testblock;

import org.junit.Test;
import rabbitescape.engine.*;
import rabbitescape.engine.block.*;
import rabbitescape.engine.block.blockshape.FlatShape;
import rabbitescape.engine.textworld.Comment;
import rabbitescape.engine.token.Token;
import rabbitescape.engine.util.Dimension;
import rabbitescape.engine.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestBlock
{
    @Test
    public void testDecayBlockOnStepped() {
        DecayBlock decayBlock = new DecayBlock(0, 0, 0, 3);
        World world = new World(
                new Dimension(3, 3),
                new ArrayList<Block>(),
                new ArrayList<BehaviourExecutor>(),
                new ArrayList<Thing>(),
                new HashMap<Position, Integer>(),
                new HashMap<Token.Type, Integer>(),
                "Empty World", // name
                "", // description
                "", // author_name
                "", // author_url
                new String[] {}, // hints
                new String[] {}, // solutions
                0, // num_rabs
                1, // num_to_save
                new int[] { 4 }, // rabbit_delay
                "", // music
                0, // num_saved
                0, // num_killed
                0, // num_waiting
                0, // rabbit_index_count
                false,
                new Comment[] {},
                new IgnoreWorldStatsListener(),
                VoidMarkerStyle.Style.HIGHLIGHTER);// Mock or set up a test world
        BehaviourExecutor executor = new Rabbit(0, 0, Direction.LEFT);

        decayBlock.onStepped(world, executor, 1);
        assertNull(world.getBlockAt(0, 1));
    }

    @Test
    public void testSpringBoardBlockOnStepped() {
        SpringBoardBlock springBoardBlock = new SpringBoardBlock(0, 0, 0);
        World world = new World(
            new Dimension(3, 3),
            new ArrayList<Block>(),
            new ArrayList<BehaviourExecutor>(),
            new ArrayList<Thing>(),
            new HashMap<Position, Integer>(),
            new HashMap<Token.Type, Integer>(),
            "Empty World", // name
            "", // description
            "", // author_name
            "", // author_url
            new String[] {}, // hints
            new String[] {}, // solutions
            0, // num_rabs
            1, // num_to_save
            new int[] { 4 }, // rabbit_delay
            "", // music
            0, // num_saved
            0, // num_killed
            0, // num_waiting
            0, // rabbit_index_count
            false,
            new Comment[] {},
            new IgnoreWorldStatsListener(),
            VoidMarkerStyle.Style.HIGHLIGHTER);; // Mock or set up a test world
        BehaviourExecutor executor = new Rabbit(0, 0, Direction.LEFT);
        springBoardBlock.onStepped(world, executor, 1);
        assertEquals(  -2 , executor.y);
    }

    @Test
    public void EarthBlockOnStepped() {
        EarthBlock earthBlock = new EarthBlock(0, 0, new FlatShape(),0);
        World world = new World(
            new Dimension(3, 3),
            new ArrayList<Block>(),
            new ArrayList<BehaviourExecutor>(),
            new ArrayList<Thing>(),
            new HashMap<Position, Integer>(),
            new HashMap<Token.Type, Integer>(),
            "Empty World", // name
            "", // description
            "", // author_name
            "", // author_url
            new String[] {}, // hints
            new String[] {}, // solutions
            0, // num_rabs
            1, // num_to_save
            new int[] { 4 }, // rabbit_delay
            "", // music
            0, // num_saved
            0, // num_killed
            0, // num_waiting
            0, // rabbit_index_count
            false,
            new Comment[] {},
            new IgnoreWorldStatsListener(),
            VoidMarkerStyle.Style.HIGHLIGHTER);; // Mock or set up a test world
        BehaviourExecutor executor = new Rabbit(0, 0, Direction.LEFT);

        earthBlock.onStepped(world, executor, 1);
        assertEquals(  0 , executor.y);
    }

    @Test
    public void MetalBlockOnStepped() {
        MetalBlock metalBlock = new MetalBlock(0, 0, new FlatShape(),0);
        World world = new World(
            new Dimension(3, 3),
            new ArrayList<Block>(),
            new ArrayList<BehaviourExecutor>(),
            new ArrayList<Thing>(),
            new HashMap<Position, Integer>(),
            new HashMap<Token.Type, Integer>(),
            "Empty World", // name
            "", // description
            "", // author_name
            "", // author_url
            new String[] {}, // hints
            new String[] {}, // solutions
            0, // num_rabs
            1, // num_to_save
            new int[] { 4 }, // rabbit_delay
            "", // music
            0, // num_saved
            0, // num_killed
            0, // num_waiting
            0, // rabbit_index_count
            false,
            new Comment[] {},
            new IgnoreWorldStatsListener(),
            VoidMarkerStyle.Style.HIGHLIGHTER);; // Mock or set up a test world
        BehaviourExecutor executor = new Rabbit(0, 0, Direction.LEFT);
        metalBlock.onStepped(world, executor, 1);
        assertEquals(  0 , executor.y);
    }
}
