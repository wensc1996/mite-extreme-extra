package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.bgu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bgu.class)
public class RenderBipedTrans {
    @ModifyConstant(method = {
            "a(Lnet/minecraft/EntityInsentient;F)V",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
