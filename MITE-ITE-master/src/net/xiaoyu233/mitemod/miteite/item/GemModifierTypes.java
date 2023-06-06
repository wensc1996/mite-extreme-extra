package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.IIcon;
import net.minecraft.Material;

import javax.swing.*;

public enum GemModifierTypes{
    damage("damage", "White"),
    health("health", "Red"),
    protection("protection", "Purple"),
    recover("recover", "Aquamarine");
    public String gemName;
    public String iconName;

    GemModifierTypes(String gemName, String iconName) {
        this.gemName = gemName;
        this.iconName = iconName;
    }

    public float getRate() {
        if(this == protection) {
            return 0.25f;
        } else if(this == recover) {
            return 0.125f;
        } else {
            return 1f;
        }
    }
}
