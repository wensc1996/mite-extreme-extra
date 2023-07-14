package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.bfl;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(bfl.class)
public class RenderGlobalTrans {
    @Shadow
    private Minecraft t;

    @Inject(method = "a()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockLeaves;a(Z)V", shift = At.Shift.AFTER))
    public void injectLeaves1Render(CallbackInfo callbackInfo) {
        Blocks.leaves1.a(this.t.u.isFancyGraphicsEnabled());
    }
}
