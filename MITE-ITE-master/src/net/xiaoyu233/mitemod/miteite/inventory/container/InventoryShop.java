package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.InventorySubcontainer;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.List;

public class InventoryShop extends InventorySubcontainer {
    ContainerShop containerShop;
    public int pageIndex = 0;
    public static int pageSize = 45;
    public InventoryShop(ContainerShop containerShop) {
        super("temp", true, pageSize);
        this.containerShop = containerShop;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        double buyPrice = this.getStackInSlot(par1).getPrice().buyPrice;
        if(buyPrice > 0d) {
            if(containerShop.player.money - buyPrice >= 0d) {
                containerShop.player.money -= buyPrice;
                return this.getStackInSlot(par1).copy();
            } else {
                containerShop.player.addChatMessage("余额不足");
                return null;
            }
        } else {
            return null;
        }
    }


    public void initItemList() {
        if(pageIndex * pageSize < Items.priceStackList.size()) {
            List<ItemStack> currentPageList = Items.priceStackList.subList(pageIndex * pageSize, Math.min(pageIndex * pageSize + pageSize, Items.priceStackList.size()));
            if(currentPageList.size() > 0) {
                for (int i = 0; i < 45; i++) {
                    if(i < currentPageList.size()) {
                        this.setInventorySlotContents(i, currentPageList.get(i).copy());
                    } else {
                        this.setInventorySlotContents(i, null);
                    }
                }
            }
        }
        containerShop.updatePlayerInventory(containerShop.player);
    }

}
