package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;

public class SlotGem extends Slot {
    ContainerGemSetting containerGemSetting;
    int slotIndex;
    public SlotGem(ContainerGemSetting containerGemSetting, IInventory inventory, int slot_index, int display_x, int display_y)
    {

        super(inventory, slot_index, display_x, display_y, false);
        this.containerGemSetting = containerGemSetting;
        this.slotIndex = slot_index;
    }

    public void putStack(ItemStack par1ItemStack) {
        super.putStack(par1ItemStack);
        if(par1ItemStack != null) {
            if(slotIndex == 0) {
                this.initGems(par1ItemStack);
            } else {
                ItemStack source = containerGemSetting.getSlot(0).getStack();
                if(source != null) {
                    source.GemsList[slotIndex - 1] = par1ItemStack.copy();
                }
            }
        }
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        if(par2ItemStack != null) {
            if(slotIndex == 0) {
                this.containerGemSetting.getTileEntityFurnace().destroyInventory();
            } else {
                ItemStack source = containerGemSetting.getSlot(0).getStack();
                if(source != null) {
                    source.GemsList[slotIndex - 1] = null;
                }
            }
        }
    }

    public void initGems(ItemStack itemStack) {

        for (int var2 = 0; var2 < itemStack.GemsList.length; ++var2)
        {
            ItemStack temp = itemStack.GemsList[var2];
            containerGemSetting.getTileEntityFurnace().setInventorySlotContents(var2 + 1, temp != null ? itemStack.GemsList[var2].copy() : null);
        }
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        Item item = par1ItemStack.getItem();
        if(this.slotIndex == 0) {
            return item instanceof ItemTool || item instanceof ItemArmor;
        } else {
            if(containerGemSetting.getSlot(0).getStack() == null) {
                return false;
            }
            return item instanceof ItemEnhanceGem;
        }
    }

}
