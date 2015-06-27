package net.timeless.unilib.common.structure.rules;

import net.minecraft.world.World;
import net.timeless.unilib.common.structure.BlockCoords;

import java.util.Random;

public class PlaceRule extends RepeatRule {
    private boolean placed;

    @Override
    public boolean continueRepeating(World world, Random rand, BlockCoords position) {
        return !placed;
    }

    @Override
    public void repeat(World world, Random rand, BlockCoords position) {
        placed = true;
    }

    @Override
    public void reset(World world, Random random, BlockCoords pos) {
        placed = false;
    }
}
