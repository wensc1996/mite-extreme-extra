package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Arrays;

@Mixin(PathfinderGoalJumpOnBlock.class)
public class PathfinderGoalJumpOnBlockTrans {

    @Overwrite
    private boolean isSittableBlock(World par1World, int par2, int par3, int par4) {
        int var5 = par1World.getBlockId(par2, par3, par4);
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if (var5 == Block.chest.blockID) {
            TileEntityChest var7 = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);
            if (var7.numUsingPlayers < 1) {
                return true;
            }
        } else {
            if (var5 > 0) {
                Block block = Block.blocksList[var5];
                if (block instanceof BlockFurnace && !((BlockFurnace)block).canBeCarried()) {
                    return true;
                }
            }

            if ( Arrays.stream(Constant.bedBlockTypes).anyMatch(e -> e.blockID == var5) && !BlockBed.isBlockHeadOfBed(var6))
            {
                return true;
            }
        }

        return false;
    }
}
