package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public class EnchantmentChain extends Enchantment {
    protected EnchantmentChain(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 1;
    }

//    @Override
//    public float enchantIndividualChance(int enchantmentLevel) {
////        return enchantmentLevel < 43 ? 0 : 0.005f + 0.3f * (enchantmentLevel / 158f);
//        return enchantmentLevel < 30 ? 0 : 0.5f + 0.3f * (enchantmentLevel / 158f);
//    }

    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        return (super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != silkTouch.effectId);
    }

//    @Override
//    public boolean enchantIndividually() {
//        return true;
//    }

    @Override
    public String getNameSuffix() {
        return "chain";
    }

    @Override
    public boolean canEnchantItem(Item item) {
        if (item instanceof ItemTool) {
            Material material = ((ItemTool)item).getToolMaterial();
            if (!(material == Materials.vibranium || material == Materials.mithril || material == Materials.adamantium || material == Materials.iron || material == Materials.ancient_metal)) {
                return false;
            }
        }
        return (item instanceof ItemPickaxe || item instanceof ItemWarHammer);
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab creative_tab)
    {
        return this == looting ? creative_tab == CreativeModeTab.tabCombat : creative_tab == CreativeModeTab.tabTools;
    }
}
