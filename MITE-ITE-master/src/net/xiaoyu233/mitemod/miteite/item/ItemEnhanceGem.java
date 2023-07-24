package net.xiaoyu233.mitemod.miteite.item;

import com.sun.imageio.plugins.common.I18N;
import net.minecraft.*;

import java.util.List;

public class ItemEnhanceGem extends Item {
    private IIcon[] field_94586_c;
    public int gemLevel;
    public ItemEnhanceGem(int id, int level)
    {
        super(id, Material.diamond, "gem/enhance_gem_phase" + level);
        this.gemLevel = level;
        this.setMaxStackSize(1);
        this.setCraftingDifficultyAsComponent(25.0F);
        this.setCreativeTab(CreativeModeTab.tabMisc);
    }

    public boolean isHarmedByAcid() {
        return false;
    }
    public boolean isHarmedByFire() {
        return false;
    }
    public boolean isHarmedByLava() {
        return false;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public GemModifierTypes getModifierType(int index) {
        return GemModifierTypes.values()[index];
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        if(par1ItemStack != null) {
            int var2 = par1ItemStack.getItemSubtype();

            if (var2 < 0 || var2 >= GemModifierTypes.values().length)
            {
                var2 = 0;
            }

            return super.getUnlocalizedName() + "." + GemModifierTypes.values()[var2].gemName;
        } else {
            return "";
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < GemModifierTypes.values().length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    public IIcon getIconFromSubtype(int par1)
    {
        if (par1 < 0 || par1 >= GemModifierTypes.values().length)
        {
            par1 = 0;
        }

        return this.field_94586_c[par1];
    }

    public void a(mt par1IconRegister)
    {
        this.field_94586_c = new IIcon[GemModifierTypes.values().length];

        for (int var2 = 0; var2 < GemModifierTypes.values().length; ++var2)
        {
            this.field_94586_c[var2] = par1IconRegister.a(this.A() + "_" + GemModifierTypes.values()[var2].gemName);
        }
    }


    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot)
    {
        if (extended_info)
        {
            info.add("");
            if(item_stack != null) {
                int subtype = item_stack.getItemSubtype();
                if(subtype < GemModifierTypes.values().length) {
                    info.add(EnumChatFormat.RED + Translator.getFormatted("item.tooltip.attributeGem." + GemModifierTypes.values()[subtype].gemName) + ":" + GemModifierTypes.values()[subtype].getRate() * gemLevel);
                }
            }
        }

        super.addInformation(item_stack, player, info, extended_info, slot);
    }
}
