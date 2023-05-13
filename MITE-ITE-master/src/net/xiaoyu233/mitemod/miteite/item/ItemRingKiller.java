package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemRingKiller extends Item{
    int level = 6;
    public ItemRingKiller(int par1, Material material) {
        super(par1, material, null);
        this.judgeMaterialLevel(material);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeModeTab.tabCombat);
        this.setCraftingDifficultyAsComponent(1.0E-9F);
    }

    public int getLevel() {
        return this.level;
    }

    public void judgeMaterialLevel (Material material) {
        if(material == Materials.copper) {
            this.level = 1;
        } else if(material == Materials.iron) {
            this.level = 2;
        } else if(material == Materials.ancient_metal) {
            this.level = 3;
        } else if(material == Materials.mithril) {
            this.level = 4;
        } else if(material ==  Materials.adamantium) {
            this.level = 5;
        } else if(material == Materials.vibranium) {
            this.level = 6;
        }
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

    public String getToolType() {
        return "ringKiller";
    }

    public int getRingKillerSkillCoolDownTime() {
        return (7 - this.level) * 10;
    }

    public float getRingKillerSkillRange() {
        return 3F;
    }

    public float getRingKillerSkillDamage() {
        return (float) this.level * 0.5F;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted("自动范围群体伤害", new Object[0]));
            info.add(EnumChatFormat.BLUE + Translator.getFormatted("范围" + this.getRingKillerSkillRange(), new Object[0]));
            info.add(EnumChatFormat.RED + Translator.getFormatted("伤害" + this.getRingKillerSkillDamage(), new Object[0]));
            info.add(EnumChatFormat.LIGHT_PURPLE + Translator.getFormatted("冷却" + (float)this.getRingKillerSkillCoolDownTime() / 20F + 'S', new Object[0]));
        }
    }
}
