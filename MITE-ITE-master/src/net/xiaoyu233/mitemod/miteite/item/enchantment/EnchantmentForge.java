package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentForge
        extends Enchantment {
    protected EnchantmentForge(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    public int getNumLevels() {
        return 5;
    }

    public String getNameSuffix() {
        return "forge";
    }

    public boolean canEnchantItem(Item item) {
        return item instanceof ItemAxe || item instanceof ItemWarHammer;
    }

    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return creativeModeTab == CreativeModeTab.tabTools;
    }
}

