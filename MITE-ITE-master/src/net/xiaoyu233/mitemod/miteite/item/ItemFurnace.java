package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.Block;
import net.minecraft.IDamageableItem;
import net.minecraft.ItemBlock;
import net.minecraft.Material;

public class ItemFurnace extends ItemBlock implements IDamageableItem {
    private final Material blockMaterial;
    public ItemFurnace(Block block, Material material) {
        super(block);
        this.blockMaterial = material;
        this.setMaxDamage(this.getDurability());
    }


    public int getDurability(){
        if (this.blockMaterial == Material.clay) {
            return 2048;
        } else if (this.blockMaterial == Material.hardened_clay) {
            return 3072;
        } else if(this.blockMaterial == Material.stone){
            return 8192;
        } else if (this.blockMaterial == Material.sand) {
            return 8192;
        } else if (this.blockMaterial == Material.obsidian) {
            return 16384;
        } else if (this.blockMaterial == Material.netherrack) {
            return 32768;
        }   else {
            return 1;
        }
    }


    public Material getMaterial() {
        return blockMaterial;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 30;
    }
}
