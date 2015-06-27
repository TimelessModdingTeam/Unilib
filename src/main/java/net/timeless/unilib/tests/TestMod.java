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

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent evt) {
        setProxy(proxy);
        super.preInitMod(evt);
        logger.info("Loading Unilib test mod, using Unilib " + Unilib.getVersion());
        StructureBuilder builder = StructureRegistry.getInstance().createStructure("testStructure");
      /*  builder.startLayer();
        {
            builder.fill(Blocks.brick_block, 0, 0, 0, 6, 6, 6);
            builder.fill(Blocks.bookshelf, 1, 1, 1, 4, 4, 4);
        }
        builder.endLayer();*/
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
        return Lists.newArrayList();
    }

    @Override
    public String getModID() {
        return MODID;
    }
}
