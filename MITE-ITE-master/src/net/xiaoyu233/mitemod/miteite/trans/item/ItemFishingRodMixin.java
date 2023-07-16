package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemFishingRod.class)
public class ItemFishingRodMixin extends Item implements IDamageableItem{
    @Shadow
    public int getNumComponentsForDurability() {
        return 0;
    }
    @Shadow
    private Material hook_material;


    private IIcon castIcon;
    private IIcon[] uncastIcons = new IIcon[10];

    @Overwrite
    public void a(mt par1IconRegister) {
        this.castIcon = par1IconRegister.a(this.A() + "_cast");
        for(int i = 0; i < this.uncastIcons.length; ++i) {
            this.uncastIcons[i] = par1IconRegister.a("fishing_rods/" + this.getMaterialByOrdinal(i).getName() + "_uncast");
        }
    }
    @Overwrite
    public IIcon g() {
        return this.castIcon;
    }

    @Overwrite
    public IIcon getIconFromSubtype(int par1) {
        return this.uncastIcons[this.getMaterialOrdinal()];
    }

    @Overwrite
    private int getMaterialOrdinal() {
        if (this.hook_material == Material.flint) {
            return 0;
        } else if (this.hook_material == Material.obsidian) {
            return 1;
        } else if (this.hook_material == Material.copper) {
            return 2;
        } else if (this.hook_material == Material.silver) {
            return 3;
        }     else if (this.hook_material == Material.gold) {
            return 4;
        }else if (this.hook_material == Material.iron) {
            return 5;
        } else if (this.hook_material == Material.mithril) {
            return 6;
        } else if (this.hook_material == Material.adamantium) {
            return 7;
        } else if (this.hook_material == Materials.vibranium) {
            return 8;
        }else {
            return this.hook_material == Material.ancient_metal ? 9 : -1;
        }
    }


    @Overwrite
    private Material getMaterialByOrdinal(int ordinal) {
        if (ordinal == 0) {
            return Material.flint;
        } else if (ordinal == 1) {
            return Material.obsidian;
        } else if (ordinal == 2) {
            return Material.copper;
        } else if (ordinal == 3) {
            return Material.silver;
        } else if (ordinal == 4) {
            return Material.gold;
        } else if (ordinal == 5) {
            return Material.iron;
        } else if (ordinal == 6) {
            return Material.mithril;
        } else if (ordinal == 7) {
            return Material.adamantium;
        } else if (ordinal == 8) {
            return Materials.vibranium;
        }else {
            return ordinal == 9 ? Material.ancient_metal : null;
        }
    }

}
