package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.Block.leaves;
import static net.xiaoyu233.mitemod.miteite.block.Blocks.leaves1;

@Mixin(BlockGrass.class)
public class BlockGrassTrans {
    @Overwrite
    public boolean isLegalAt(World world, int x, int y, int z, int metadata) {
        Block block_above = world.getBlock(x, y + 1, z);
        if (block_above != null && block_above != leaves && block_above != leaves1 && block_above.blockMaterial != Material.snow && block_above.blockMaterial != Material.craftedSnow) {
            if (block_above instanceof BlockPiston) {
                return false;
            } else if (!block_above.hidesAdjacentSide(world, x, y + 1, z, ReflectHelper.dyCast(BlockGrass.class,this), 1)) {
                return true;
            } else {
                return !block_above.isFaceFlatAndSolid(world.getBlockMetadata(x, y + 1, z), EnumFace.BOTTOM);
            }
        } else {
            return true;
        }
    }
}
