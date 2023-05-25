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

}
