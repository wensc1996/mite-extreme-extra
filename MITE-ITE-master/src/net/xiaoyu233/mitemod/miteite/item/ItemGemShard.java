package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemGemShard extends Item {
    private IIcon[] field_94586_c;

    public static int maxLevel = 5;

    public ItemGemShard(int id)
    {
        super(id, Material.diamond, "gem/gem_shard");
        this.setMaxStackSize(16);
        this.setCraftingDifficultyAsComponent(25.0F);
        this.setCreativeTab(CreativeModeTab.tabMisc);
    }
    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        if(par1ItemStack != null) {
            int var2 = par1ItemStack.getItemSubtype();

            if (var2 < 0 || var2 >= maxLevel)
            {
                var2 = 0;
            }

            return super.getUnlocalizedName() + "_" + var2;
        } else {
            return "";
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < maxLevel; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    public IIcon getIconFromSubtype(int par1)
    {
        if (par1 < 0 || par1 >= maxLevel)
        {
            par1 = 0;
        }

        return this.field_94586_c[par1];
    }

    public void a(mt par1IconRegister)
    {
        this.field_94586_c = new IIcon[maxLevel];

        for (int var2 = 0; var2 < maxLevel; ++var2)
        {
            this.field_94586_c[var2] = par1IconRegister.a(this.A() + "_" + var2);
        }
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot)
    {
        if (extended_info)
        {
            info.add("");
            if(item_stack != null) {
                int subtype = item_stack.getItemSubtype();
                if(subtype < maxLevel) {
                    info.add(EnumChatFormat.RED + Translator.getFormatted("item.tooltip.gem_shard_usage_" + subtype));
                }
            }
        }

        super.addInformation(item_stack, player, info, extended_info, slot);
    }
}
