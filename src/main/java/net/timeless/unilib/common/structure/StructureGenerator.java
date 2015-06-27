package net.timeless.unilib.common.structure;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public abstract class StructureGenerator {

    public abstract void generate(World world, int x, int y, int z, Random random);

    public abstract StructureGenerator rotateClockwise();

    public abstract StructureGenerator rotateTowards(EnumFacing facing);
}
