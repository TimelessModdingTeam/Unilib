package net.timeless.unilib.common.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.*;

public class StructureBuilder extends StructureGenerator {
    private final HashMap<BlockCoords, Block> blocks;
    private int offsetX;
    private int offsetY;
    private int offsetZ;
    private List<RepeatRule> repeats;
    private List<LayerInfo> layers;
    private LayerInfo currentLayer;

    public StructureBuilder() {
        blocks = Maps.newHashMap();
        repeats = Lists.newArrayList();
        layers = Lists.newArrayList();
    }

    @Override
    public void generate(World world, int x, int y, int z, Random random) {
        for(LayerInfo layer : layers) {
            int currentX = x;
            int currentY = y;
            int currentZ = z;
            for(Map.Entry<BlockCoords, Block> e : layer.blocks.entrySet()) {

            }
        }
    }

    @Override
    public StructureGenerator rotateClockwise() {
        return null;
    }

    @Override
    public StructureGenerator rotateTowards() {
        return null;
    }

    public LayerInfo startLayer() {
        currentLayer = new LayerInfo();
        blocks.clear();
        repeats.clear();
        offsetX = 0;
        offsetY = 0;
        offsetZ = 0;
        return currentLayer;
    }

    public void endLayer() {
        currentLayer.blocks.putAll(blocks);
        currentLayer.repeats.addAll(repeats);
        layers.add(currentLayer);
    }

    public void setOffset(int x, int y, int z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
    }

    public void translate(int x, int y, int z) {
        offsetX += x;
        offsetY += y;
        offsetZ += z;
    }

    public void setBlock(Block block, int x, int y, int z) {
        blocks.put(new BlockCoords(x + offsetX, y + offsetY, z + offsetZ), block);
    }

    public void fill(Block block, int startX, int startY, int startZ, int width, int height, int depth) {
        for(int x = startX;x<startX+width;x++) {
            for(int y = startY;x<startY+height;y++) {
                for(int z = startZ;z<startZ+depth;z++) {
                    setBlock(block, x, y, z);
                }
            }
        }
    }

    public void repeat(int spacingX, int spacingY, int spacingZ, int times) {
        repeat(spacingX, spacingY, spacingZ, new FixedRule(times));
    }

    public void repeat(int spacingX, int spacingY, int spacingZ, RepeatRule repeatRule) {
        repeatRule.setSpacing(spacingX, spacingY, spacingZ);
        repeats.add(repeatRule);
    }
}
