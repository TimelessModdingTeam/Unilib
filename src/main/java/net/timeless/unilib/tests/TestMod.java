package net.timeless.unilib.tests;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.timeless.unilib.Unilib;
import net.timeless.unilib.common.BaseMod;
import net.timeless.unilib.common.CommonProxy;

@Mod(modid = TestMod.MODID, version = "1.0", name = "Test Unilib")
public class TestMod extends BaseMod {

    public static final String MODID = "unilib_test";

    @Mod.Instance(MODID)
    public static TestMod instance;
    @SidedProxy(serverSide = "net.timeless.unilib.common.CommonProxy", clientSide = "net.timeless.unilib.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent evt) {
        setProxy(proxy);
        super.preInitMod(evt);
        logger.info("Loading Unilib test mod, using Unilib "+ Unilib.getVersion());
    }

}
