package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.CraftingManager;
import net.minecraft.RecipesTools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipesTools.class)
public class RecipesToolsTrans {
    @Redirect(method = "addRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/CraftingManager;addRecipes([[Ljava/lang/Object;I)V"))
    public void removeToolRecipe(CraftingManager instance, Object[][] line, int item_or_block) {

    }
}
