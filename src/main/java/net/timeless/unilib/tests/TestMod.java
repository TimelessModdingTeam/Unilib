package net.timeless.unilib.tests;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.timeless.unilib.Unilib;
import net.timeless.unilib.common.BaseMod;
import net.timeless.unilib.common.BlockProvider;
import net.timeless.unilib.common.CommonProxy;
import net.timeless.unilib.common.ItemProvider;
import net.timeless.unilib.common.blocks.BaseBlock;

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
        logger.info("Loading Unilib test mod, using Unilib "+ Unilib.getVersion());
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
