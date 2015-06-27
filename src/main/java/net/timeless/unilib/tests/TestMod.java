package net.timeless.unilib.tests;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.timeless.unilib.Unilib;
import net.timeless.unilib.common.*;
import net.timeless.unilib.common.blocks.BaseBlock;
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
        builder.startLayer(); {
            builder.fillCube(Blocks.bookshelf.getDefaultState(), -3, 0, -3, 6, 6, 6);
            builder.cube(Blocks.brick_block.getDefaultState(), -3, 0, -3, 6, 6, 6);
        }
        builder.repeat(0, 8, 0, repeatRule);
        builder.endLayer();

        builder.startLayer(); {
            builder.cube(Blocks.water.getDefaultState(), -3, 6, -3, 6, 1, 6);
        }
        builder.addBakedRepeatRule(repeatRule);
        builder.endLayer();
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
