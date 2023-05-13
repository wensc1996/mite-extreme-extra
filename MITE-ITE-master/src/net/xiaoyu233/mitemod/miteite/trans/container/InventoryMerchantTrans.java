package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryMerchant.class)
public class InventoryMerchantTrans {
    @Shadow
    private final IMerchant theMerchant;
    @Shadow
    private final EntityPlayer thePlayer;
    @Shadow
    private ItemStack[] theInventory = new ItemStack[3];
    @Shadow
    private MerchantRecipe currentRecipe;
    @Shadow
    private int currentRecipeIndex;

    public InventoryMerchantTrans(IMerchant theMerchant, EntityPlayer thePlayer) {
        this.theMerchant = theMerchant;
        this.thePlayer = thePlayer;
    }

    @Shadow
    public ItemStack getStackInSlot(int par1) {
        return this.theInventory[par1];
    }

    @Shadow
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {}

    @Overwrite
    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack var1 = this.theInventory[0];
        ItemStack var2 = this.theInventory[1];
        if (var1 == null) {
            var1 = var2;
            var2 = null;
        }

        if (var1 == null) {
            this.setInventorySlotContents(2, (ItemStack)null);
        } else {
            MerchantRecipeList var3 = this.theMerchant.getRecipes(this.thePlayer);
            if (var3 != null) {
                MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, this.currentRecipeIndex);
                if (var4 != null && !var4.func_82784_g()) {
                    this.currentRecipe = var4;
                    this.setInventorySlotContents(2, var4.getItemToSell().copy());
                } else {
                    this.setInventorySlotContents(2, (ItemStack)null);
                }
            }
        }

        this.theMerchant.func_110297_a_(this.getStackInSlot(2));
    }
}
