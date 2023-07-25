package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityBat.class)
public class EntityBatTrans extends EntityAmbient {
    private boolean spawnedByWitch;
    private EntityWanderingWitch spawnerWitch;
    private int spawnWorldID;

    @Shadow
    private int prevent_hang_countdown;

    public EntityBatTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    private void injectCtor(CallbackInfo callbackInfo){
        if (this.worldObj.isUnderworld()){
            this.spawnWorldID = 3;
        }
        if (this.worldObj.isOverworld()){
            this.spawnWorldID = 0;
        }
        if (this.worldObj.isTheNether()){
            this.spawnWorldID = 1;
        }
        if (this.worldObj.isTheEnd()){
            this.spawnWorldID = 2;
        }
    }

    @Override
    public void addPotionEffect(MobEffect par1PotionEffect) {
        if (!this.spawnedByWitch){
            super.addPotionEffect(par1PotionEffect);
        }
    }

    @Overwrite
    private boolean isPreventedFromHanging() {
        return this.riddenByEntity != null || this.prevent_hang_countdown > 0 || this.getAttackTarget() != null || this.hurtResistantTime > 0 || this.worldObj.getClosestPlayerToEntity(this, 4.0, true) != null;
    }

    @Redirect(method = "updateAITasks", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLiving;getEyePos()Lnet/minecraft/Vec3D;"))
    public Vec3D changeByMountSkeleton(EntityLiving entityLiving) {
        if(this.riddenByEntity instanceof EntitySkeleton) {
            return entityLiving.getFootPos();
        } else {
            return entityLiving.getEyePos();
        }
    }

    public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
        if (super.onEntityRightClicked(player, item_stack)) {
            return true;
        } else if (this.riddenByEntity == null && item_stack == null) {
            if (player.onServer()) {
                player.mountEntity(this);
                player.triggerAchievement(Achievements.rideBat);
            }
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public int getExperienceValue() {
//
//        return super.getExperienceValue();
//    }

    @Override
    public float getNaturalDefense(DamageSource damage_source) {
        if (spawnedByWitch){
            return damage_source.hasMagicAspect() && damage_source.isIndirect() ? Integer.MAX_VALUE : super.getNaturalDefense(damage_source);
        }
        return super.getNaturalDefense(damage_source);
    }

    @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
    private void injectReadNBT(NBTTagCompound nbt, CallbackInfo callbackInfo){
        if (nbt.hasKey("SpawnedByWitch")){
            if (nbt.hasKey("SpawnWorldId")){
                this.spawnWorldID = nbt.getInteger("SpawnWorldId");
            }
            this.spawnedByWitch = nbt.getBoolean("SpawnedByWitch");
            Entity entityByID = this.worldObj.getAsWorldServer().p().worldServers[this.spawnWorldID].getEntityByID(nbt.getInteger("WitchSpawner"));
            if (entityByID == null){
                this.setDead();
            }
            this.spawnerWitch = (EntityWanderingWitch) entityByID;
        }
    }

    @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
    private void injectWriteNBT(NBTTagCompound nbt, CallbackInfo callbackInfo){
        if (this.spawnedByWitch){
            nbt.setBoolean("SpawnedByWitch", true);
            nbt.setInteger("WitchSpawner",this.spawnerWitch.entityId);
            nbt.setInteger("SpawnWorldId",this.spawnWorldID);
        }
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (this.spawnerWitch != null && this.spawnerWitch.isEntityAlive()){
            this.spawnerWitch.onAllyBatsDeath();
        }
    }

    public void setSpawnedByWitch(boolean spawnedByWitch,EntityWanderingWitch witch) {
        this.spawnedByWitch = spawnedByWitch;
        this.spawnerWitch = witch;
    }
}
