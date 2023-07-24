package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(bfr.class)
public class RenderBlocksTrans {
    @Shadow
    public void a(Block par1Block, int par2, double par3, double par5, double par7, float par9) {}

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "a(Lnet/minecraft/BlockFlowerPot;III)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/bfq;c(FFF)V", ordinal = 2, shift = At.Shift.AFTER))
    public void renderBlockFlowerpot(BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4, CallbackInfoReturnable<Boolean> cir, bfq var5, float var6, int var7, IIcon var8, float var9, float var10, float var11, float var12, float var14, int var19, float var15, float var16, BlockPlant var17) {
        if(var17 == null) {
            if (var19 == 13) {
                this.a(Blocks.sapling1, 0, (double)par2, (double)par3, (double)par4, 0.75F);
            } else if (var19 == 14) {
                this.a(Blocks.sapling1, 1, (double)par2, (double)par3, (double)par4, 0.75F);
            } else if (var19 == 15) {
                this.a(Blocks.sapling1, 2, (double) par2, (double) par3, (double) par4, 0.75F);
            }
        }
    }
}
