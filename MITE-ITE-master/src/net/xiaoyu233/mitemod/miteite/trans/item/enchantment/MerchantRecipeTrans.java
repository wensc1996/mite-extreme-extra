package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantRecipe.class)
public class MerchantRecipeTrans {
    @Shadow
    private ItemStack itemToBuy;
    @Shadow
    private ItemStack secondItemToBuy;
    @Shadow
    private ItemStack itemToSell;
    @Inject(method = "hasSameIDsAs", at=@At("HEAD"), cancellable = true)
    public void hasSameIDsAs(MerchantRecipe par1MerchantRecipe, CallbackInfoReturnable<Boolean> callback) {
        if((this.itemToSell.itemID == Items.enchantedBook.itemID) && (par1MerchantRecipe.getItemToSell().itemID == Items.enchantedBook.itemID)) {
            NBTTagList nbtTagList = this.itemToSell.getStoredEnchantmentTagList();
            NBTTagList nbtTagList2 = par1MerchantRecipe.getItemToSell().getStoredEnchantmentTagList();
            if(nbtTagList != null && nbtTagList2 != null) {
                for(int var5 = 0; var5 < nbtTagList.tagCount(); ++var5) {
                    NBTTagCompound var6 = (NBTTagCompound)nbtTagList.tagAt(var5);
                    for(int var7 = 0; var7 < nbtTagList2.tagCount(); ++var7) {
                        NBTTagCompound var8 = (NBTTagCompound) nbtTagList2.tagAt(var7);
                        if (var6.getShort("id") == var8.getShort("id")) {
                            if (var6.getShort("lvl") == var8.getShort("lvl")) {
                                callback.setReturnValue(true);
                                callback.cancel();
                                return;
                            }
                        }
                    }
                }
            }
            callback.setReturnValue(false);
            callback.cancel();
        }
    }
}
