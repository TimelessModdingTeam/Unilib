package net.timeless.unilib.common.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.List;

public class LayerInfo {
    public HashMap<BlockCoords, Block> blocks;
    public List<RepeatRule> repeats;

    public LayerInfo() {
        blocks = Maps.newHashMap();
        repeats = Lists.newArrayList();
    }
}
