package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockFire;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFire.class)
public class BlockFireTrans {
    @Shadow
    private void setBurnRate(int par1, int par2, int par3) {
    }

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void injectInit(CallbackInfo callbackInfo) {
        this.setBurnRate(Blocks.wood1.blockID, 5, 5);
        this.setBurnRate(Blocks.leaves1.blockID, 30, 60);
    }
}
