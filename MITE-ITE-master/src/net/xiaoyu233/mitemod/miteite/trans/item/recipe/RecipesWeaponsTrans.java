package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.CraftingManager;
import net.minecraft.RecipesWeapons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipesWeapons.class)
public class RecipesWeaponsTrans {
    @Redirect(method = "addRecipes", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/CraftingManager;addRecipes([[Ljava/lang/Object;I)V"))
    public void removeToolRecipe(CraftingManager instance, Object[][] line, int item_or_block) {

    }
}
