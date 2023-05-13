package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.Collection;
import java.util.Iterator;

public class EntityMirrorSkeleton extends EntitySkeleton {
    private boolean hasClonePlayer = false;

    public EntityMirrorSkeleton(World par1World) {
        super(par1World);
    }

    public boolean canNeverPickUpItem(Item item) {
        return true;
    }

    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.voucherZombieLord);
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = (day / 32) > 3 ? 3 : (day / 32);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.emerald);
            }
        }
    }

    @Override
    public void dropContainedItems() {

    }

    @Override
    protected void dropEquipment(boolean recently_hit_by_player, int par2) {

    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        double x = day / 7 - 7;
        double rate = (0.5+ x / (20 + Math.abs(x)));
        this.setEntityAttribute(GenericAttributes.attackDamage, rate * 40);
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if(ticksExisted % 20 == 0) {
                EntityLiving target = this.getTarget();

                if(target instanceof EntityPlayer) {
                    if(hasClonePlayer == false) {
                        if(target.getMaxHealth() > this.getMaxHealth()) {
                            this.setEntityAttribute(GenericAttributes.maxHealth, target.getMaxHealth());
                            this.setHealth(target.getMaxHealth());
                        }
                        this.hasClonePlayer = true;
                    }

                    Collection collection = target.getActivePotionEffects();
                    Iterator var7 = collection.iterator();
                    while(var7.hasNext()) {
                        MobEffect var8 = (MobEffect)var7.next();
                        this.addPotionEffect(new MobEffect(var8.getPotionID(), var8.getDuration()));
                    }

                    if(target.getHelmet() != null) {
                        this.setHelmet(target.getHelmet());
                    }
                    if(target.getBoots() != null) {
                        this.setBoots(target.getBoots());
                    }
                    if(target.getCuirass() != null) {
                        this.setCuirass(target.getCuirass());
                    }
                    if(target.getLeggings() != null) {
                        this.setLeggings(target.getLeggings());
                    }
                    if(target.getHeldItemStack() != null) {
                        this.setCurrentItemOrArmor(0, target.getHeldItemStack());
                    }
                }
            }
        }
    }
}
