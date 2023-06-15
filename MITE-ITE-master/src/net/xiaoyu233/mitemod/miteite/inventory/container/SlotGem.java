package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;

public class SlotGem extends Slot {
    ContainerGemSetting containerGemSetting;
    int slotIndex;
    public SlotGem(ContainerGemSetting containerGemSetting, IInventory inventory, int slot_index, int display_x, int display_y)
    {

        super(inventory, slot_index, display_x, display_y, false);
        this.setContainer(containerGemSetting);
        this.containerGemSetting = containerGemSetting;
        this.slotIndex = slot_index;
    }

    @Override
    public void onSlotChanged() {
        if(this.slotIndex == 0) {
            ItemStack source = containerGemSetting.getSlot(0).getStack();
            if(source != null) {
                if(source.stackTagCompound.hasKey("Gems")) {
                    this.initGems(source);
                } else {
                    source.setGem(null, 0);
                }
            } else {
                this.containerGemSetting.getTileEntityFurnace().destroyInventory();
            }
        } else {
            ItemStack gemStack = containerGemSetting.getSlot(this.slotIndex).getStack();
            ItemStack source = containerGemSetting.getSlot(0).getStack();
            if(source != null) {
                source.setGem(gemStack, this.slotIndex - 1);
            }
        }
        super.onSlotChanged();
    }
    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    public void initGems(ItemStack source) {
        if(source.stackTagCompound != null && source.stackTagCompound.hasKey("Gems")) {
            NBTTagList nbtTagList = source.stackTagCompound.getTagList("Gems");
            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtTagList.tagAt(i);
                if (nbtTagCompound.getShort("id") >= 0 && nbtTagCompound.getByte("meta") >= 0) {
                    Item item = Item.getItem(nbtTagCompound.getShort("id"));
                    if (item instanceof ItemEnhanceGem) {
                        containerGemSetting.getTileEntityFurnace().setInventorySlotContents(i + 1, new ItemStack(nbtTagCompound.getShort("id"), 1, nbtTagCompound.getByte("meta")));
                    } else {
                        containerGemSetting.getTileEntityFurnace().setInventorySlotContents(i + 1, null);
                    }
                } else {
                    containerGemSetting.getTileEntityFurnace().setInventorySlotContents(i + 1, null);
                }
            }
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
