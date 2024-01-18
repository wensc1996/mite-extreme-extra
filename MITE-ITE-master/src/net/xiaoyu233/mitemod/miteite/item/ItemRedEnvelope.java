package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemRedEnvelope extends Item {
    public ItemRedEnvelope(int id)
    {
        super(id, Material.diamond, "red_envelope");
        this.setMaxStackSize(64);
        this.setCraftingDifficultyAsComponent(25.0F);
        this.setCreativeTab(CreativeModeTab.tabMisc);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        if (player.onServer())
        {
            player.causeBreakingItemEffect(player.getHeldItem(), player.inventory.currentItem);
            if(ctrl_is_down) {
                ItemStack current = player.getHeldItemStack();
                player.setHeldItemStack(null);
                int total = 0;
                for (int i= 0; i< current.stackSize; i++) {
                    int perMoney = player.worldObj.rand.nextInt(5) + 1;
                    total += perMoney;
                }
                player.plusMoney(total);
                player.addChatMessage("共计红包获得：" + total);
            } else {
                player.convertOneOfHeldItem((ItemStack)null);
                int perMoney = player.worldObj.rand.nextInt(5) + 1;
                player.plusMoney(perMoney);
                player.addChatMessage("红包获得：" + perMoney);
            }
        }
        else
        {
            player.bobItem();
        }
        return true;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot)
    {
        if (extended_info)
        {
            info.add("");
            info.add(EnumChatFormat.RED + Translator.getFormatted("item.tooltip.red_envelope"));
        }

        super.addInformation(item_stack, player, info, extended_info, slot);
    }
}
