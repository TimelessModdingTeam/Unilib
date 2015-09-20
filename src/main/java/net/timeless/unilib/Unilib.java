package net.timeless.unilib;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timeless.unilib.common.BaseMod;
import net.timeless.unilib.common.BlockProvider;
import net.timeless.unilib.common.ItemProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Mod(modid = "net/timeless/unilib", name = "Unilib", version = "${unilib_version}")
public class Unilib extends BaseMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        super.preInitMod(evt);
        logger.info("Loading Unilib "+ Unilib.getVersion());
        logger.info("Loading content handlers...");
        /*
        try {
            // Get the FML mod controller in order to change the current container later
            LoadController controller = ObfuscationReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), 16);
            ModContainer container = ObfuscationReflectionHelper.getPrivateValue(LoadController.class, controller, 7);

            // List of all the packages in which not to search for providers.
            // Loading a class from them at this stage might crash the game!
            List<String> forbiddenPackages = Lists.newArrayList("com.google.", "com.ibm", "akka.", "org.apache.",
                    "net.minecraft.", "net.minecraftforge.", "com.intellij.", "com.mojang.", "com.oracle.", "com.sun.",
                    "io.netty.", "ibxm.", "gnu.trove.", "com.typesafe.", "com.jcraft.", "javaw.", "javafx.",
                    "org.eclipse.", "java.", "org.lwjgl.", "scala.", "paulscode.", "org.xml.", "org.w3c.", "org.omg.",
                    "org.objectweb.asm.", "org.jcp.xml.", "org.ietf.jgss.", "oracle.", "netscape.", "sun.",
                    "net.java.games.", "joptsimple.", "jdk.internal.", "javax.", "tv.twitch.", "sunw.", "jdk.", "LZMA.",
                    "com.apple.", "apple.");
            // Get all the classes from the classloader and look for providers
            ClassPath path = ClassPath.from(ClassLoader.getSystemClassLoader());
            for(ClassPath.ResourceInfo info : path.getResources()) {
                if(info instanceof ClassPath.ClassInfo) {
                    handleClass((ClassPath.ClassInfo) info, controller, container, forbiddenPackages);
                } else {
                    String name = info.getResourceName();
                    if(name.startsWith("assets")) {
                        String[] parts = name.split("/");
                        String modid = parts[1];
                        if(modid.equals("minecraft"))
                            continue;
                        String type = parts[2];
                        if(type.equals("sounds")) {

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void handleClass(ClassPath.ClassInfo info, LoadController controller, ModContainer container, List<String> forbiddenPackages) {
        try {
            boolean isAllowed = true;
            for(String s : forbiddenPackages) { // Checks the full class name against the forbidden packages' list
                if(info.getName().startsWith(s)) {
                    isAllowed = false;
                    break;
                }
            }
            if(!isAllowed) {
                return;
            }
            Side currentSide = FMLCommonHandler.instance().getEffectiveSide();
            if((info.getName().toLowerCase().contains("client") && currentSide != Side.CLIENT)
                    || (info.getName().toLowerCase().contains("server") && currentSide != Side.SERVER)) {
                System.out.println(">> "+info.getName().toLowerCase());
                return;
            }
            Class<?> clazz = Class.forName(info.getName(), false, getClass().getClassLoader());
            Object instance = null;
            if(clazz.isAnnotationPresent(Mod.class)) { // Find the mod instance
                Mod mod = clazz.getAnnotation(Mod.class);
                List<ModContainer> list = Loader.instance().getModList();
                for(ModContainer c : list) {
                    Object modInstance = c.getMod();
                    if(modInstance != null) {
                        if (modInstance.getClass() == clazz) {
                            instance = modInstance;
                            break;
                        }
                    }
                }
            }
            if(clazz.isAnnotationPresent(SideOnly.class)) {
                Side side = clazz.getAnnotation(SideOnly.class).value();
                if(!side.equals(FMLCommonHandler.instance().getSide())) { // We are on the wrong side for loading
                    return;
                }
            }
            if(instance == null) {
                try {
                    instance = clazz.newInstance();

                } catch(Exception e) {
                    return; // Shhhhh, nothing happened here...
                }
            }
            ModMetadata metadata = new ModMetadata();
            ModContainer modContainer = new DummyModContainer(metadata); // Mod container used to change the modid of registred items and blocks
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> in : interfaces) { // Search for possible providers, such as BlockProvider or ItemProvider
                setContainer(modContainer, controller);
                if (in == BlockProvider.class) {
                    BlockProvider provider = ((BlockProvider)instance);
                    logger.info("Found block provider: " + clazz.getName());
                    Collection<Block> blocks = provider.createBlocks();
                    metadata.modId = provider.getModID();
                    for(Block b : blocks) {
                        GameRegistry.registerBlock(b, b.getUnlocalizedName().replaceFirst("tile\\.", ""));
                    }
                } else if (in == ItemProvider.class) {
                    ItemProvider provider = ((ItemProvider)instance);
                    logger.info("Found item provider: " + clazz.getName());
                    Collection<Item> items = provider.createItems();
                    metadata.modId = provider.getModID();
                    for(Item i : items) {
                        GameRegistry.registerItem(i, i.getUnlocalizedName().replaceFirst("item\\.", ""));
                    }
                }
                setContainer(container, controller);
            }
        }
        catch (Exception e) {
            logger.error(e);
            // Shhh, nothing but dreams now
        }
    }

    private void setContainer(ModContainer container, LoadController controller) {
        ObfuscationReflectionHelper.setPrivateValue(LoadController.class, controller, container, 7);
    }

    public static String getVersion() {
        return "${unilib_version}";
    }
}
