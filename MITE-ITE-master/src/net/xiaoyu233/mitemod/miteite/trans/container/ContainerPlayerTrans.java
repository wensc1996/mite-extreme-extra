package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerPlayer.class)
public abstract class ContainerPlayerTrans extends MITEContainerCrafting {

    public ContainerPlayerTrans(EntityPlayer player) {
        super(player);
    }

    @Inject(method = "createSlots", at=@At("RETURN"))
    public void createSlotsInject(CallbackInfo callbackInfo) {
        int hotbar_index;
        int x;
        for(hotbar_index = 0; hotbar_index < 4; ++hotbar_index) {
            this.addSlotToContainer(new Slot(this.player.inventory, 36 + hotbar_index, 184, 8 + hotbar_index * 18));
        }
        for(hotbar_index = 0; hotbar_index < 3; ++hotbar_index) {
            this.addSlotToContainer(new Slot(this.player.inventory, 40 + hotbar_index, 184, 84 + hotbar_index * 18));
        }
        this.addSlotToContainer(new Slot(this.player.inventory, 43, 184 , 142));
    }

    @Overwrite
    public ItemStack transferStackInSlot(EntityPlayer entity_player, int slot_index) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(slot_index);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (slot_index == 0) {
                if (!this.mergeItemStack(var5, 9, 45, true)) {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            } else if (slot_index >= 1 && slot_index < 5) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            } else if (slot_index >= 5 && slot_index < 9) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            } else if (var3.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)var3.getItem()).armorType)).getHasStack() && !this.player.hasCurse(Curse.cannot_wear_armor, true)) {
                int var6 = 5 + ((ItemArmor)var3.getItem()).armorType;
                if (!this.mergeItemStack(var5, var6, var6 + 1, false)) {
                    return null;
                }
            } else if (slot_index >= 9 && slot_index < 36) {
                if (!this.mergeItemStack(var5, 36, 45, false)) {
                    return null;
                }
            } else if (slot_index >= 36 && slot_index < 45) {
                if (!this.mergeItemStack(var5, 9, 36, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var5, 9, 45, false)) {
                return null;
            }

            if (var5.stackSize == 0) {
                var4.putStack((ItemStack)null);
            } else {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize) {
                return null;
            }

            var4.onPickupFromSlot(entity_player, var5);
        }

        return var3;
    }
}
