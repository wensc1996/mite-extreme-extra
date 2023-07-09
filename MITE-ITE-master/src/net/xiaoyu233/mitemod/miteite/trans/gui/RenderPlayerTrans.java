package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.bhj;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bhj.class)
public class RenderPlayerTrans {
    @ModifyConstant(method = {
            "a(Lnet/minecraft/beu;F)V",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
