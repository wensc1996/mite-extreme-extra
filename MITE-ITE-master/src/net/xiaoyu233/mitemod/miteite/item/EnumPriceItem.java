package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.ItemStack;

public class EnumPriceItem {
    public ItemStack itemStack;
    public double soldPrice;
    public double buyPrice;
    public EnumPriceItem(ItemStack itemStack, double soldPrice, double buyPrice) {
        this.itemStack = itemStack;
        this.soldPrice = soldPrice;
        this.buyPrice = buyPrice;
    }
}
