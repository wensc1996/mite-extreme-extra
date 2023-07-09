package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.bhv;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bhv.class)
public class RenderWitchTrans {
    @ModifyConstant(method = {
            "a(Lnet/minecraft/EntityWitch;F)V",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
