package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({PlayerInteractManager.class})
public abstract class PlayerInteractManagerTrans {
    @Shadow
    public ServerPlayer thisPlayerMP;

    @Shadow
    private boolean tree_felling_in_progress;
    @Shadow
    public World theWorld;
    @Shadow
    public abstract boolean tryHarvestBlock(int x, int y, int z);

    public PlayerInteractManagerTrans() {
    }

    @Redirect(
            method = {"setGameType", "getGameType", "isCreative", "initializeGameType"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private boolean redirectInDevMode() {
        return (this.thisPlayerMP != null && this.thisPlayerMP.isOp()) || Minecraft.inDevMode();
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "tryHarvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/ServerPlayer;addStat(Lnet/minecraft/Statistic;I)V", ordinal = 1, shift = At.Shift.AFTER))
    public void injectAnoterTree(int x, int y, int z, CallbackInfoReturnable<Boolean> cir, Block block, BlockBreakInfo block_break_info, boolean player_can_damage_block, int data, boolean block_was_removed, ItemStack held_item_stack) {
        if (block == Blocks.wood1 && !this.tree_felling_in_progress) {
            int felling = EnchantmentManager.getTreeFellingModifier(this.thisPlayerMP);
            this.tree_felling_in_progress = true;

            for(int dy = 1; dy <= felling && this.theWorld.getBlockId(x, y + dy, z) == Blocks.wood1.blockID; ++dy) {
                this.tryHarvestBlock(x, y + dy, z);
            }

            this.tree_felling_in_progress = false;
        }
    }
}