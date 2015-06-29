package net.timeless.unilib.tests;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.timeless.unilib.Unilib;
import net.timeless.unilib.common.*;
import net.timeless.unilib.common.blocks.BaseBlock;
import net.timeless.unilib.common.structure.BlockList;
import net.timeless.unilib.common.structure.StructureBuilder;
import net.timeless.unilib.common.structure.rules.RandomRule;
import net.timeless.unilib.common.structure.rules.RepeatRule;

import java.util.Collection;

@Mod(modid = TestMod.MODID, version = "1.0", name = "Test Unilib")
public class TestMod extends BaseMod implements BlockProvider, ItemProvider {

    public static final String MODID = "unilib_test";

    @Mod.Instance(MODID)
    public static TestMod instance;
    @SidedProxy(serverSide = "net.timeless.unilib.common.CommonProxy", clientSide = "net.timeless.unilib.client.ClientProxy")
    public static CommonProxy proxy;
    private Block test0;
    private Block test1;
    private Item structureTest;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent evt) {
        setProxy(proxy);
        super.preInitMod(evt);
        logger.info("Loading Unilib test mod, using Unilib " + Unilib.getVersion());
        StructureBuilder builder = StructureRegistry.getInstance().createStructure("testStructure");
        RepeatRule repeatRule = new RandomRule(2, 4, true);
        BlockList outsideBlocks = new BlockList(new IBlockState[] {
                Blocks.stonebrick.getDefaultState(),
                Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED),
                Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY)
        }, new float[] { 0.7f, 0.2f, 0.1f });
        int width = 5;
        int height = 4;
        int depth = 5;
        builder.startComponent(); {

            // Base structure
            builder.cube(-width/2, 0, -depth/2, width, height+1, depth, Blocks.planks);
            builder.fillCube(-width/2, 0, -depth/2, width, 1, depth, Blocks.cobblestone);
            builder.wireCube(-width/2, 0, -depth/2, width, height, depth, Blocks.cobblestone);

            //builder.fillCube(-width/2, height, -depth/2, width, 1, depth, Blocks.planks);
            builder.cube(-width/2, height, -depth/2, width, 1, depth, Blocks.log);

            builder.cube(-width/2, height+1, -depth/2, width, 1, depth, Blocks.oak_fence);

            // Door
            builder.fillCube(0, 1, -depth/2, 1, 2, 1, Blocks.air);
            builder.setBlock(0, 0, -depth / 2 - 1, Blocks.stone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));

            // Windows
            builder.setBlock(-width/2, 2, 0, Blocks.glass_pane);
            builder.setBlock(width/2, 2, 0, Blocks.glass_pane);
            builder.setBlock(0, 2, depth/2, Blocks.glass_pane);

            // Ladder
            IBlockState ladder = Blocks.ladder.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.NORTH);
            builder.fillCube(-1, 1, 1, 1, height, 1, ladder);
        }
        builder.repeat(0, 8, 0, repeatRule);
        builder.endComponent();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        super.initMod(evt);
        System.out.println(test0+"/"+test1);
    }

    @Override
    public Collection<Block> createBlocks() {
        return Lists.newArrayList(test0 = new BaseBlock("test0", Material.rock), test1 = new BaseBlock("test1", Material.rock));
    }

    @Override
    public Collection<Item> createItems() {
        return Lists.newArrayList(structureTest = new TestItem("structureTest"));
    }

    @Override
    public String getModID() {
        return MODID;
    }
}
