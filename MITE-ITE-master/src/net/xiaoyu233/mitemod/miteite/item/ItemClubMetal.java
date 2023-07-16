package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.ItemCudgel;
import net.minecraft.Material;

public class ItemClubMetal extends ItemCudgel {
    protected ItemClubMetal(int par1, Material material) {
        super(par1, material);
        this.addMaterialsEffectiveAgainst(new Material[]{Material.cake, Material.coral, Material.glass, Material.ice, Material.pumpkin, Material.tree_leaves, Material.web});
        this.setReachBonus(0.75F);
    }

    public String getToolType() {
        return "club";
    }

    public float getBaseDamageVsEntity() {
        return super.getBaseDamageVsEntity() + 2.0F;
    }

    public boolean canBlock() {
        return true;
    }

    public int getNumComponentsForDurability() {
        return 2;
    }
}
