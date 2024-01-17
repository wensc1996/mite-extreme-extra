package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SlotShop extends Slot {
    int slotIndex;
    ContainerShop containerShop;
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return false;
    }
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
        if(this.getStack() != null) {
            if(button == 1) {
                ItemStack var5 = this.getStack().copy();
                if(var5.getPrice().buyPrice > 0) {
                    double totalMoney = var5.getMaxStackSize() * var5.getPrice().buyPrice;

                    if(entity_player.money >= totalMoney) {
                        entity_player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(var5.itemID, var5.getMaxStackSize(), var5.getItemSubtype()));
                        entity_player.money -= totalMoney ;

                        BigDecimal two = new BigDecimal(entity_player.money);
                        entity_player.money = two.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    } else {
                        int maxStackSize = (int) Math.floor(entity_player.money / var5.getPrice().buyPrice);
                        if(maxStackSize > 0) {
                            totalMoney = maxStackSize * var5.getPrice().buyPrice;
                            entity_player.money -= totalMoney;
                            BigDecimal two = new BigDecimal(entity_player.money);
                            entity_player.money = two.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

                            var5.setStackSize(maxStackSize);
                            entity_player.inventory.addItemStackToInventoryOrDropIt(var5);
                        } else {
                            entity_player.addChatMessage("余额不足");
                        }
                    }
                } else {
                    entity_player.addChatMessage("商店不支持购买此商品");
                }
            } else if(button == 0) {
                ItemStack var5 = this.getStack().copy();
                double buyPrice = var5.getPrice().buyPrice;
                if(buyPrice > 0d) {
                    if(containerShop.player.money - buyPrice >= 0d) {
                        containerShop.player.money -= buyPrice;
                        entity_player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(var5.itemID, 1, var5.getItemSubtype()));
                    } else {
                        containerShop.player.addChatMessage("余额不足");
                    }
                } else {
                    containerShop.player.addChatMessage("商店不支持购买此商品");
                }
            }
        }

    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
}
