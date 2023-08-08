package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemEnhanceGemBox extends Item {
    public ItemEnhanceGemBox(int id)
    {
        super(id, Material.diamond, "enhance_gem_box_phase1");
        this.setMaxStackSize(64);
        this.setCraftingDifficultyAsComponent(25.0F);
        this.setCreativeTab(CreativeModeTab.tabMisc);
    }

    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if (player.onServer())
        {
            player.causeBreakingItemEffect(player.getHeldItem(), player.inventory.currentItem);
            player.convertOneOfHeldItem((ItemStack) null);
            player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Items.itemEnhanceGem,1, player.worldObj.rand.nextInt(GemModifierTypes.values().length)));
        }
        else
        {
            player.bobItem();
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info)
        {
            info.add("");
            info.add("概率获得三角宝石其中之一");
        }
        super.addInformation(item_stack, player, info, extended_info, slot);
    }
}
