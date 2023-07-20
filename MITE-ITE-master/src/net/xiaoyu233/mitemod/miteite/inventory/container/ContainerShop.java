package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;

import java.util.ArrayList;

public class ContainerShop extends Container {
    public InventoryShop inventory = new InventoryShop(this);
    public ContainerShop(EntityPlayer player)
    {
        super(player);
        int col;
        for (int row = 0; row < 5; ++row)
        {
            for (col = 0; col < 9; ++col)
            {
                this.addSlotToContainer(new SlotShop(this, inventory, col + row * 9, 8 + col * 18,  6 + row * 18));
            }
        }
        // 玩家包裹

        for (int row = 0; row < 3; ++row)
        {
            for (col = 0; col < 9; ++col)
            {
                this.addSlotToContainer(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 121 + row * 18));
            }
        }

        // 玩家快捷栏
        for (col = 0; col < 9; ++col)
        {
            this.addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 179));
        }
    }


    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {

    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }


    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        System.out.println(par2);
        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();

            if (par2 >= 0 && par2 < 45 && !mergeItemStack(var5.copy().setStackSize(var5.getMaxStackSize()), 45, 81, false))
            {
                return null;
            }
        }
        return var3;
    }

    // 同步打开的容器包裹
    public void updatePlayerInventory(EntityPlayer player) {
        ArrayList<ItemStack> itemList = new ArrayList();

        for(int index = 0; index < player.openContainer.inventorySlots.size(); ++index) {
            itemList.add(((Slot)player.openContainer.inventorySlots.get(index)).getStack());
        }

        ((ServerPlayer) player).updateCraftingInventory(player.openContainer, itemList);
    }
}
