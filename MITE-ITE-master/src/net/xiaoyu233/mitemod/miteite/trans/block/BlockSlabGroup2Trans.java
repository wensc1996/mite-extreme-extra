package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BitHelper;
import net.minecraft.BlockSlabGroup2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockSlabGroup2.class)
public class BlockSlabGroup2Trans {
    private static String[] types = new String[] {"oak", "spruce", "birch", "jungle", "maple", "cherry"};

    @Overwrite
    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < types.length;
    }

    @Overwrite
    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 15;
    }
}
