package net.timeless.unilib.common.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.timeless.unilib.common.structure.rules.FixedRule;
import net.timeless.unilib.common.structure.rules.RepeatRule;

import java.util.*;

public class StructureBuilder extends StructureGenerator {
    private final HashMap<BlockCoords, IBlockState> blocks;
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
        BlockCoords pos = new BlockCoords();
        for(LayerInfo layer : layers) {
            for(RepeatRule rule : layer.repeats) {
                rule.reset(world, random, pos);
            }
        }
        for(LayerInfo layer : layers) {
            int currentX = x;
            int currentY = y;
            int currentZ = z;
            pos.x = currentX;
            pos.y = currentY;
            pos.z = currentZ;
            for(RepeatRule rule : layer.repeats) {
                rule.init(world, random, pos);
                while(rule.continueRepeating(world, random, pos)) {
                    for (Map.Entry<BlockCoords, IBlockState> e : layer.blocks.entrySet()) {
                        BlockCoords coords = e.getKey();
                        int blockX = coords.x + pos.x;
                        int blockY = coords.y + pos.y;
                        int blockZ = coords.z + pos.z;
                        world.setBlockState(new BlockPos(blockX, blockY, blockZ), e.getValue());
                    }
                    rule.repeat(world, random, pos);
                }
            }
        }
    }

    @Override
    public StructureGenerator rotateClockwise() {
        return null;
    }

    @Override
    public StructureGenerator rotateTowards(EnumFacing facing) {
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

    public void setOrientation(EnumFacing facing) {
        currentLayer.facing = facing;
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

    // TODO: Overload to specify block instead of block state
    public void setBlock(IBlockState block, int x, int y, int z) {
        blocks.put(new BlockCoords(x + offsetX, y + offsetY, z + offsetZ), block);
    }

    public void cube(IBlockState block, int startX, int startY, int startZ, int width, int height, int depth) {
        if(depth > 1) {
            fillCube(block, startX, startY, startZ, width, height, 1);
            fillCube(block, startX, startY, startZ + depth - 1, width, height, 1);
        }

        if(width > 1) {
            fillCube(block, startX, startY, startZ, 1, height, depth);
            fillCube(block, startX + width - 1, startY, startZ, 1, height, depth);
        }

        if(height > 1) {
            fillCube(block, startX, startY, startZ, width, 1, depth);
            fillCube(block, startX, startY + height - 1, startZ, width, 1, depth);
        }
    }

    public void fillCube(IBlockState block, int startX, int startY, int startZ, int width, int height, int depth) {
        for(int x = startX;x<startX+width;x++) {
            for(int y = startY;y<startY+height;y++) {
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
        addBakedRepeatRule(repeatRule);
    }

    public void addBakedRepeatRule(RepeatRule repeatRule) {
        repeats.add(repeatRule);
    }
}
