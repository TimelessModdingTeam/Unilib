package net.timeless.unilib.common.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.timeless.unilib.common.structure.rules.PlaceRule;
import net.timeless.unilib.common.structure.rules.RepeatRule;

import java.util.HashMap;
import java.util.List;

public class LayerInfo {
    public HashMap<BlockCoords, BlockList> blocks;
    public List<RepeatRule> repeats;
    public EnumFacing facing;

    public LayerInfo() {
        facing = EnumFacing.NORTH;
        blocks = Maps.newHashMap();
        repeats = Lists.newArrayList();
        repeats.add(new PlaceRule());
    }
}
