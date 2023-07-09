package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.bho;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bho.class)
public class RenderSnowManTrans {
    @ModifyConstant(method = {
            "a(Lnet/minecraft/EntitySnowman;F)V",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
