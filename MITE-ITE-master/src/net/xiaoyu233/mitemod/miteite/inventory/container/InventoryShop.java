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
