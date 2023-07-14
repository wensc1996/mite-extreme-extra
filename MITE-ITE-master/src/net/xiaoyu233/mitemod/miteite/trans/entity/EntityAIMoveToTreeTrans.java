package net.xiaoyu233.mitemod.miteite.trans.entity;


import net.minecraft.Block;
import net.minecraft.EntityAIMoveToTree;
import net.minecraft.PathEntity;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAIMoveToTree.class)
public class EntityAIMoveToTreeTrans {
    @Redirect(method = "getMovementPath", at = @At(value = "INVOKE", target = "Lnet/minecraft/World;getNearestBlockCandidates(DDDIII[I[I[I[I[D)I"))
    public int getNearestBlockCandidates(World world, double origin_x, double origin_y, double origin_z, int horizontal_radius, int vertical_radius, int max_candidates, int[] block_ids, int[] candidate_x, int[] candidate_y, int[] candidate_z, double[] candidate_distance_sq) {
        block_ids = new int[]{Block.wood.blockID, Blocks.wood1.blockID};
        return world.getNearestBlockCandidates(origin_x, origin_y, origin_z, horizontal_radius, vertical_radius, max_candidates, block_ids, candidate_x, candidate_y, candidate_z, candidate_distance_sq);
    }
}
