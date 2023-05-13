package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemLavaInPipes extends Item {

    protected ItemLavaInPipes(int par1, Material par2Material) {
        super(par1, Material.lava.setDissolvesInWater(), "lava_in_pipes");
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeModeTab.tabMaterials);
    }

    public int getBurnTime(ItemStack item_stack) {
        return 3200;
    }

    public int getHeatLevel(ItemStack item_stack) {
        return 3;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("无限燃料", new Object[0]));
        }
    }
}
