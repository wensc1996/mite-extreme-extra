package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;

public class SlotShop extends Slot {
    int slotIndex;
    ContainerShop containerShop;
    public SlotShop(ContainerShop containerShop,IInventory inventory, int slot_index, int display_x, int display_y)
    {
        super(inventory, slot_index, display_x, display_y, false);
        this.setContainer(containerShop);
        this.containerShop = containerShop;
        this.slotIndex = slot_index;
    }

    @Override
    public void onSlotClicked(EntityPlayer entity_player, int button, Container container) {
        super.onSlotClicked(entity_player, button, container);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        return this.getStack().copy();
    }

}
