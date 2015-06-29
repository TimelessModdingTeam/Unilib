package net.timeless.unilib.common.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.timeless.unilib.common.structure.rules.FixedRule;
import net.timeless.unilib.common.structure.rules.RepeatRule;

import java.util.*;

public class StructureBuilder extends StructureGenerator {
    private final HashMap<BlockCoords, BlockList> blocks;
    private int offsetX;
    private int offsetY;
    private int offsetZ;
    private List<RepeatRule> repeats;
    private List<ComponentInfo> components;
    private ComponentInfo currentLayer;

    public StructureBuilder() {
        blocks = Maps.newHashMap();
        repeats = Lists.newArrayList();
        components = Lists.newArrayList();
    }

    @Override
    public void generate(World world, int x, int y, int z, Random random) {
        BlockCoords pos = new BlockCoords();
        for(ComponentInfo layer : components) {
            for(RepeatRule rule : layer.repeats) {
                rule.reset(world, random, pos);
            }
        }
        for(ComponentInfo layer : components) {
            int currentX = x;
            int currentY = y;
            int currentZ = z;
            pos.x = currentX;
            pos.y = currentY;
            pos.z = currentZ;
            for(RepeatRule rule : layer.repeats) {
                rule.init(world, random, pos);
                while(rule.continueRepeating(world, random, pos)) {
                    for (Map.Entry<BlockCoords, BlockList> e : layer.blocks.entrySet()) {
                        BlockCoords coords = e.getKey();
                        int blockX = coords.x + pos.x;
                        int blockY = coords.y + pos.y;
                        int blockZ = coords.z + pos.z;
                        world.setBlockState(new BlockPos(blockX, blockY, blockZ), e.getValue().getRandom(random));
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

    public ComponentInfo startComponent() {
        currentLayer = new ComponentInfo();
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

    public void endComponent() {
        currentLayer.blocks.putAll(blocks);
        currentLayer.repeats.addAll(repeats);
        components.add(currentLayer);
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
    public void setBlock(int x, int y, int z, IBlockState block) {
        setBlock(x, y, z, new BlockList(block));
    }

    public void setBlock(int x, int y, int z, BlockList list) {
        blocks.put(new BlockCoords(x + offsetX, y + offsetY, z + offsetZ), list);
    }

    public void cube(int startX, int startY, int startZ, int width, int height, int depth, IBlockState block) {
        cube(startX, startY, startZ, width, height, depth, new BlockList(block));
    }

    public void cube(int startX, int startY, int startZ, int width, int height, int depth, BlockList list) {
        if(depth > 1) {
            fillCube(startX, startY, startZ, width, height, 1, list);
            fillCube(startX, startY, startZ + depth - 1, width, height, 1, list);
        }

        if(width > 1) {
            fillCube(startX, startY, startZ, 1, height, depth, list);
            fillCube(startX + width - 1, startY, startZ, 1, height, depth, list);
        }

        if(height > 1) {
            fillCube(startX, startY, startZ, width, 1, depth, list);
            fillCube(startX, startY + height - 1, startZ, width, 1, depth, list);
        }
    }

    public void fillCube(int startX, int startY, int startZ, int width, int height, int depth, IBlockState block) {
        fillCube(startX, startY, startZ, width, height, depth, new BlockList(block));
    }

    public void fillCube(int startX, int startY, int startZ, int width, int height, int depth, BlockList list) {
        for(int x = startX;x<startX+width;x++) {
            for(int y = startY;y<startY+height;y++) {
                for(int z = startZ;z<startZ+depth;z++) {
                    setBlock(x, y, z, list);
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

    public void cube(int startX, int startY, int startZ, int width, int height, int depth, Block block) {
        cube(startX, startY, startZ, width, height, depth, block.getDefaultState());
    }

    public void fillCube(int startX, int startY, int startZ, int width, int height, int depth, Block block) {
        fillCube(startX, startY, startZ, width, height, depth, block.getDefaultState());
    }

    public void wireCube(int startX, int startY, int startZ, int width, int height, int depth, Block block) {
        wireCube(startX, startY, startZ, width, height, depth, block.getDefaultState());
    }

    public void wireCube(int startX, int startY, int startZ, int width, int height, int depth, IBlockState state) {
        wireCube(startX, startY, startZ, width, height, depth, new BlockList(state));
    }

    private void wireCube(int startX, int startY, int startZ, int width, int height, int depth, BlockList list) {
        fillCube(startX, startY, startZ, 1, height, 1, list);
        fillCube(startX+width-1, startY, startZ, 1, height, 1, list);
        fillCube(startX+width-1, startY, startZ+depth-1, 1, height, 1, list);
        fillCube(startX, startY, startZ+depth-1, 1, height, 1, list);

        fillCube(startX, startY, startZ, width, 1, 1, list);
        fillCube(startX, startY+height, startZ, width, 1, 1, list);
        fillCube(startX, startY, startZ+depth-1, width, 1, 1, list);
        fillCube(startX, startY+height, startZ+depth-1, width, 1, 1, list);

        fillCube(startX, startY, startZ, 1, 1, depth, list);
        fillCube(startX, startY+height, startZ, 1, 1, depth, list);
        fillCube(startX+width-1, startY, startZ, 1, 1, depth, list);
        fillCube(startX+width-1, startY+height, startZ, 1, 1, depth, list);
    }

    public void setBlock(int x, int y, int z, Block block) {
        setBlock(x, y, z, block.getDefaultState());
    }
}
