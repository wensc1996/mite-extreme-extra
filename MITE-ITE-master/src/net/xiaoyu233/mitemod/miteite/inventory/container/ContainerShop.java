package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;

import java.math.BigDecimal;
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
                this.addSlotToContainer(new SlotShop(this, inventory, col + row * 9, 8 + col * 18,  18 + row * 18));
            }
        }
        // 玩家包裹

        for (int row = 0; row < 3; ++row)
        {
            for (col = 0; col < 9; ++col)
            {
                this.addSlotToContainer(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        // 玩家快捷栏
        for (col = 0; col < 9; ++col)
        {
            this.addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 198));
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

        if (var4 != null && var4.getHasStack())
        {
            if (par2 >= 0 && par2 < 45){

            } else {
                ItemStack var5 = var4.getStack();
                double soldPrice = (double) var5.getItem().soldPriceArray.get(var5.getItemSubtype());
                if(soldPrice > 0d) {
                    double totalMoney = var5.stackSize * soldPrice;
                    player.money += totalMoney;
                    BigDecimal two = new BigDecimal(player.money);
                    player.money = two.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                var4.putStack(null);
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
