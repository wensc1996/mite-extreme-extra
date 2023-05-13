package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;

public class EntityExchanger extends EntitySkeleton {
    private int teleportDelay;

    public EntityExchanger(World par1World) {
        super(par1World);
    }

    @Override
    protected void addRandomEquipment() {
        this.setCurrentItemOrArmor(0, (new ItemStack(Items.enderPearl, 1)).randomizeForMob(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        double x = day / 7 - 7;
        double rate = (0.5+ x / (20 + Math.abs(x)));
        int healthRate = Math.min(day / 16, 10);
        this.setEntityAttribute(GenericAttributes.attackDamage, rate * 40);
        this.setEntityAttribute(GenericAttributes.maxHealth, rate * 50 + healthRate * 5);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.2572D);
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItem(Items.voucherExchanger);
            int count = 3;
            for (int i1 = 0; i1 < count; i1++) {
                this.dropItem(Item.arrowIron);
            }
        }
    }

    public void exchangeSkill() {
        EntityPlayer target = (EntityPlayer)this.getEntityToAttack();
        if(target != null) {
            double entiX = target.posX;
            double entiY = target.posY;
            double entiZ = target.posZ;
            double currentX = this.posX;
            double currentY = this.posY;
            double currentZ = this.posZ;
            this.teleportToTarget(entiX, entiY, entiZ);
            target.setPositionAndUpdate(currentX, currentY, currentZ);
        }
    }

    @Override
    protected EntityPlayer findPlayerToAttack(float max_distance) {
        EntityPlayer player = this.getClosestVulnerablePlayer((double)max_distance);
        return player;
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            int var6;
            for (var6 = 0; var6 < 2; ++var6) {
                this.worldObj.spawnParticle(EnumParticle.portal_underworld, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }

            if(this.entityToAttack == null) {
                entityToAttack = this.getClosestVulnerablePlayer(32F);
                if (entityToAttack instanceof EntityPlayer) {
                    ((EntityPlayer) entityToAttack).sendChatToPlayer(ChatMessage.createFromTranslationKey("[转移骷髅] ").setColor(EnumChatFormat.BLUE).appendComponent(ChatMessage.createFromTranslationKey("你已被注视，3S后转移").setColor(EnumChatFormat.RED)));
                }
                this.teleportDelay = 0;
            } else {
                if(entityToAttack.isDead || getDistanceToEntity(entityToAttack) > 32F) {
                    if (entityToAttack instanceof EntityPlayer) {
                        ((EntityPlayer) entityToAttack).sendChatToPlayer(ChatMessage.createFromTranslationKey("[转移骷髅] ").setColor(EnumChatFormat.BLUE).appendComponent(ChatMessage.createFromTranslationKey("已放弃注视").setColor(EnumChatFormat.GREEN)));
                    }
                    this.entityToAttack = null;
                    return;
                }
                if (entityToAttack instanceof EntityPlayer) {
                    ItemStack[] var3 = ((EntityPlayer) entityToAttack).getWornItems();
                    for (ItemStack wornItem : var3) {
                        if (wornItem != null && wornItem.hasEnchantment(Enchantments.enchantmentFixed, false)) {
                            return;
                        }
                    }
                    if (this.teleportDelay < 70 && ++this.teleportDelay > 60) {
                        exchangeSkill();
                        this.teleportDelay = 70;
                    }
                }
            }
        }
    }

    public void teleportToTarget(double par1, double par3, double par5) {
        double var7 = this.posX;
        double var9 = this.posY;
        double var11 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;

        this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);

        double distance = (double)World.getDistanceFromDeltas(this.posX - var7, this.posY - var9, this.posZ - var11);
        this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y, z, (new SignalData()).setByte(EnumParticle.portal_underworld.ordinal()).setShort((int)(8.0D * distance)).setApproxPosition((double)MathHelper.floor_double(var7), (double)MathHelper.floor_double(var9), (double)MathHelper.floor_double(var11)));
        this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y + 1, z, (new SignalData()).setByte(EnumParticle.portal_underworld.ordinal()).setShort((int)(8.0D * distance)).setApproxPosition((double)MathHelper.floor_double(var7), (double)MathHelper.floor_double(var9 + 1.0D), (double)MathHelper.floor_double(var11)));
        this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
    }

}
