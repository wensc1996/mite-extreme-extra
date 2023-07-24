package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockLeaves1;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockMounted.class)
public abstract class BlockMountedTrans extends Block {

    protected BlockMountedTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    public boolean canMountToBlock(int metadata, Block neighbor_block, int neighbor_block_metadata, EnumFace face) {
        if (neighbor_block == tilledField) {
            return true;
        } else if (face.isTop() && neighbor_block == jukebox) {
            return false;
        } else if (!(neighbor_block instanceof BlockLeaves || neighbor_block instanceof BlockLeaves1) && !neighbor_block.neverHidesAdjacentFaces() && neighbor_block.blockMaterial != Material.cloth && !(neighbor_block instanceof BlockSponge)) {
            if (!(neighbor_block instanceof BlockPiston) && !(neighbor_block instanceof BlockPistonMoving)) {
                if (!(neighbor_block instanceof BlockFurnace) && !(neighbor_block instanceof BlockDispenser)) {
                    return neighbor_block.isFaceFlatAndSolid(neighbor_block_metadata, face);
                } else {
                    return face != ((BlockDirectional)neighbor_block).getFrontFace(neighbor_block_metadata);
                }
            } else {
                return face != ((BlockDirectional)neighbor_block).getFrontFace(neighbor_block_metadata);
            }
        } else {
            return false;
        }
    }

}
