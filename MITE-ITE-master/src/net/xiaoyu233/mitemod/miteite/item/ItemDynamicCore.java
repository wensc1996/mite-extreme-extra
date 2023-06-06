package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;

import java.util.List;

public class ItemDynamicCore extends Item implements IDamageableItem {

    private Material reinforcement_material;
    public int level;

    ItemDynamicCore(int id, Material reinforcement_material, int level){
        super(id, reinforcement_material,"dynamic_core");
        this.level = level;
        this.setCreativeTab(CreativeModeTab.tabTools);
        this.reinforcement_material = reinforcement_material;
        this.setMaxDamage(this.getMultipliedDurability());
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

    public final int getMultipliedDurability()
    {
        return 6400 * level;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }

    @Override
    public int getRepairCost() {
        return 2;
    };

    public Material getMaterialForDurability()
    {
        return this.reinforcement_material;
    }

    public Material getMaterialForRepairs()
    {
        return this.reinforcement_material;
    }

    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add(" ");
            info.add(EnumChatFormat.BROWN + Translator.getFormatted(this.level + "/5夜视效果", new Object[0]));
        }
    }

}
