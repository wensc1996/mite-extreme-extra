package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;
import java.util.List;

@Mixin(awy.class)
public class GuiCraftingTrans {
    @Shadow public Container e;
    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "drawItemStackTooltip",at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockWorkbench;getToolMaterial(I)Lnet/minecraft/Material;", shift = At.Shift.AFTER))
    public void judgeGemListExistThenPrevent(ItemStack par1ItemStack, int par2, int par3, Slot slot, CallbackInfo ci, List var4, Item item, aah recipe, Material material_to_check_tool_bench_hardness_against, boolean upper_body_in_web, List tooltips, ContainerWorkbench container_workbench) {
        for (int i = 0; i < container_workbench.craft_matrix.getInventory().length; i++) {
            if(container_workbench.craft_matrix.getInventory() != null) {
                ItemStack itemStack = container_workbench.craft_matrix.getInventory()[i];
                if(itemStack != null) {
                    for (int i1 = 0; i1 < itemStack.GemsList.length; i1++) {
                        if(itemStack.GemsList[i1] != null) {
                            tooltips.add(EnumChatFormat.RED + Translator.get("container.crafting.needsRemoveGems"));
                            this.e.crafting_result_shown_but_prevented = true;
                        }
                    }
                }
            }
        }
    }

    @Redirect(method = "drawItemStackTooltip",at = @At(value = "INVOKE",target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
    private boolean redirectRemoveDamageLimitation(InventoryCrafting caller){
        aah recipe = ((MITEContainerCrafting) this.e).current_crafting_result.recipe;
        if (recipe instanceof ShapedRecipes){
            return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
        }else if (recipe instanceof ShapelessRecipes){
            return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
        }
        return caller.hasDamagedItem();
    }
}
