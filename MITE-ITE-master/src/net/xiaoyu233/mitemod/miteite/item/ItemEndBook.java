package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.Material;
import org.spongepowered.asm.mixin.Overwrite;

public class ItemEndBook extends Item {
    public ItemEndBook(int par1) {
        super(par1, Material.wood, "end_book");
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeModeTab.tabTools);
    }

    @Overwrite
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        player.displayGUIChestForMinecart(player.getInventoryEnderChest());
        return true;
    }
}