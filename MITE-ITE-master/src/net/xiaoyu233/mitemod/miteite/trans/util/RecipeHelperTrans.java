package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.RecipeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RecipeHelper.class)
public class RecipeHelperTrans {
    @ModifyConstant(method = "addRecipe", constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
