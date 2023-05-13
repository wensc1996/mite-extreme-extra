package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.ItemRock;
import net.minecraft.Material;

public class ItemFancyRed extends ItemRock {
    protected ItemFancyRed(int id, Material material, String texture) {
        super(id, material, texture);
        this.setXPReward(40).setUnlocalizedName("fancy_red");
    }
}
