package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockLeaves1;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RaycastPolicies.class)
public abstract class RaycastPoliciesTrans {
    @Accessor("for_third_person_view")
    public static RaycastPolicies getThirdPersonView() {
        throw new AssertionError();
    }

    @Shadow
    private int glass_and_ice_policy = -1;
    @Shadow
    private int all_portals_policy = -1;
    @Shadow
    private int open_portals_policy = -1;
    @Shadow
    private int open_gates_policy = -1;
    @Shadow
    private int tall_grass_policy = -1;
    @Shadow
    private int leaves_policy = -1;
    @Shadow
    private int reeds_policy = -1;
    @Shadow
    private int vines_policy = -1;
    @Shadow
    private int fence_policy = -1;
    @Shadow
    private int metal_bars_policy = -1;
    @Shadow
    private int non_solid_block_policy = -1;
    @Shadow
    private int uncovered_cauldron_policy = -1;

    @Overwrite
    public boolean ignoreBlock(Block block, World world, int x, int y, int z, Raycast raycast) {
        if (this.glass_and_ice_policy != -1 && (block.blockMaterial == Material.glass || block.blockMaterial == Material.ice) && !raycast.isFullyImpeded(this.glass_and_ice_policy)) {
            return ReflectHelper.dyCast(RaycastPolicies.class,this) != getThirdPersonView() || block.blockMaterial != Material.ice;
        } else {
            if (block.isPortal()) {
                if (this.open_gates_policy != -1 && block instanceof BlockFenceGate && BlockFenceGate.isFenceGateOpen(world.getBlockMetadata(x, y, z)) && !raycast.isFullyImpeded(this.open_gates_policy)) {
                    return true;
                }

                if (this.open_portals_policy != -1 && block.isOpenPortal(world, x, y, z) && !raycast.isFullyImpeded(this.open_portals_policy)) {
                    return true;
                }

                if (this.all_portals_policy != -1 && !raycast.isFullyImpeded(this.all_portals_policy)) {
                    return true;
                }
            }

            if (this.tall_grass_policy != -1 && (block == Block.tallGrass || block == Block.plantRed || block == Block.plantYellow || block instanceof BlockCrops || block instanceof BlockWeb) && !raycast.isFullyImpeded(this.tall_grass_policy)) {
                return true;
            } else if (this.non_solid_block_policy != -1 && !block.is_always_solid && (block.is_never_solid || !block.isSolid(world.getBlockMetadata(x, y, z)) || block.getCollisionBoundsCombined(world, x, y, z, (Entity)null, true) == null) && !raycast.isFullyImpeded(this.non_solid_block_policy)) {
                return true;
            } else if (this.uncovered_cauldron_policy != -1 && (block == Block.cauldron || block == Block.hopperBlock) && !world.isBlockFaceFlatAndSolid(x, y + 1, z, EnumFace.BOTTOM) && !raycast.isFullyImpeded(this.uncovered_cauldron_policy)) {
                return true;
            } else if (this.leaves_policy != -1 && (block instanceof BlockLeaves || block instanceof BlockLeaves1) && !raycast.isFullyImpeded(this.leaves_policy)) {
                return true;
            } else if (this.reeds_policy != -1 && block instanceof BlockReed && !raycast.isFullyImpeded(this.reeds_policy)) {
                return true;
            } else if (this.vines_policy != -1 && block instanceof BlockVine && !raycast.isFullyImpeded(this.vines_policy)) {
                return true;
            } else if (this.fence_policy != -1 && block instanceof BlockFence && !raycast.isFullyImpeded(this.fence_policy)) {
                return true;
            } else {
                return this.metal_bars_policy != -1 && block instanceof BlockThinFence && block.blockMaterial.isMetal() && !raycast.isFullyImpeded(this.metal_bars_policy);
            }
        }
    }
}
