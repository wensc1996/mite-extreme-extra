package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.BlockLeaves;
import net.minecraft.BlockVine;
import net.xiaoyu233.mitemod.miteite.block.BlockLeaves1;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockVine.class)
public class BlockVineTrans {
    @Overwrite
    private final boolean canBePlacedOn(int par1) {
        if (par1 == 0) {
            return false;
        } else {
            Block var2 = Block.blocksList[par1];
            return var2.isAlwaysSolidOpaqueStandardFormCube() || var2 instanceof BlockLeaves || var2 instanceof BlockLeaves1;
        }
    }
}
