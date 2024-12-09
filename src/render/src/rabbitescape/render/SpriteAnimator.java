package rabbitescape.render;

import java.util.ArrayList;
import java.util.List;

import rabbitescape.engine.*;
import rabbitescape.engine.block.Block;
import rabbitescape.engine.block.DecayBlock;
import rabbitescape.engine.block.blockshape.BlockShape;
import rabbitescape.engine.block.blockmaterial.BlockMaterial;
import rabbitescape.engine.block.blockmaterial.EarthMaterial;
import rabbitescape.engine.block.blockmaterial.MetalMaterial;
import rabbitescape.engine.util.*;

public class SpriteAnimator {
    private final World world;
    private final AnimationCache animationCache;

    private static final String[] decay_block = new String[] {
            "block",
            "block",
            "block",
            "block"
    };

    private static final String[] metal_block = new String[] {
            "metal_block_1",
            "metal_block_2",
            "metal_block_3",
            "metal_block_4",
    };

    private static final String[] land_block = new String[] {
            "land_block_1",
            "land_block_2",
            "land_block_3",
            "land_block_4",
    };

    private static final String[] land_rising_right = new String[] {
            "land_rising_right_1",
            "land_rising_right_2",
            "land_rising_right_3",
            "land_rising_right_4"
    };

    private static final String[] land_rising_left = new String[] {
            "land_rising_left_1",
            "land_rising_left_2",
            "land_rising_left_3",
            "land_rising_left_4"
    };

    private static final String bridge_rising_right = "bridge_rising_right";
    private static final String bridge_rising_left = "bridge_rising_left";

    public SpriteAnimator(
            World world,
            AnimationCache animationCache) {
        this.world = world;
        this.animationCache = animationCache;
    }

    public List<Sprite> getSprites(int frameNum) {
        List<Sprite> ret = new ArrayList<>();

        for (Block block : world.blockTable) {
            addBlock(block, ret);
        }

        for (Thing thing : Util.filterOut(world.things, Fire.class)) {
            addThing(frameNum, thing, null, ret);
        }

        for (BehaviourExecutor behaviourExecutor : world.behaviourExecutors) {
            addThing(frameNum, behaviourExecutor, null, ret);
        }

        for (Thing thing : Util.filterIn(world.things, Fire.class)) {
            addThing(frameNum, thing, null, ret);
        }

        for (Thing thing : world.changes.tokensAboutToAppear()) {
            addThing(frameNum, thing, null, ret);
        }

        // TODO: probably easier if we just had a rabbit entering animation
        if (frameNum == 0) {
            for (Thing thing : world.changes.rabbitsJustEntered()) {
                addThing(-1, thing, "rabbit_entering", ret);
            }
        }

        VoidMarker.mark(world, ret, world.voidStyle);

        return ret;
    }

    private void addBlock(Block block, List<Sprite> ret) {
        ret.add(
                new Sprite(
                        bitmapNameForBlock(block),
                        null,
                        block.getX(),
                        block.getY(),
                        0,
                        0));
    }

    private void addThing(
            int frameNum,
            Thing thing,
            String soundEffectOverride,
            List<Sprite> ret) {
        Frame frame = frameForThing(frameNum, thing);

        String bitmapName = null;
        int offsetX = 0;
        int offsetY = 0;
        if (frame != null) {
            bitmapName = frame.name;
            offsetX = frame.offsetX;
            offsetY = frame.offsetY;
        }

        ret.add(
                new Sprite(
                        bitmapName,
                        soundEffectForFrame(soundEffectOverride, frame),
                        thing.x,
                        thing.y,
                        offsetX,
                        offsetY));
    }

    private String soundEffectForFrame(
            String soundEffectOverride, Frame frame) {
        if (soundEffectOverride != null) {
            return soundEffectOverride;
        }

        if (frame != null) {
            return frame.soundEffect;
        }

        return null;
    }

    private Frame frameForThing(int frameNum, Thing thing) {
        if (frameNum == -1) {
            return null;
        }

        Animation animation = animationCache.get(thing.stateName());

        if (animation == null) {
            System.out.println(
                    "Missing animation for state " + thing.stateName());
            return null;
        }

        return animation.get(frameNum);
    }

    private String bitmapNameForBlock(Block block) {
        BlockMaterial material = block.getMaterial();
        if (block instanceof DecayBlock) {
            return decay_block[block.getVariant()];
        }
        if (material instanceof EarthMaterial) {
            switch (block.getShape()) {
                case FLAT:
                    return land_block[block.getVariant()];
                case UP_RIGHT:
                    return land_rising_right[block.getVariant()];
                case UP_LEFT:
                    return land_rising_left[block.getVariant()];
                case BRIDGE_UP_RIGHT:
                    return bridge_rising_right;
                case BRIDGE_UP_LEFT:
                    return bridge_rising_left;
            }
        }
        if (material instanceof MetalMaterial && block.isFlat()) {
            return metal_block[block.getVariant()];
        }

        throw new RuntimeException(
                "Unknown Block type: ");
    }
}
