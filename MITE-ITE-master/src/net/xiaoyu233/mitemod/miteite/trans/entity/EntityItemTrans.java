package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItem.class)
public abstract class EntityItemTrans extends Entity {
   private boolean isExploded;
   private boolean canBePickUpByPlayer;

   public EntityItemTrans(World par1World) {
      super(par1World);
   }

   @Shadow
   public boolean canBePickedUpBy(EntityLiving entity_living_base) { return true;};

   @Overwrite
   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
      if (!par1EntityPlayer.isGhost() && !par1EntityPlayer.isZevimrgvInTournament()) {
         if (!(par1EntityPlayer.ridingEntity instanceof EntityHorse) || !(this.posY - par1EntityPlayer.getFootPosY() < -0.5)) {
            if (!this.worldObj.isRemote) {
               boolean was_empty_handed_before = !par1EntityPlayer.hasHeldItem();
               ItemStack var2 = this.getEntityItem();
               int var3 = var2.stackSize;
               if (this.canBePickedUpBy(par1EntityPlayer) && par1EntityPlayer.inventory.addItemStackToInventory(var2)) {
                  if (var2.itemID == Block.wood.blockID || var2.itemID == Blocks.wood1.blockID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                  }

                  if (var2.itemID == Item.leather.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                  }

                  if (var2.itemID == Item.diamond.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                  }

                  if (var2.itemID == Item.emerald.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.emeralds);
                  }

                  if (var2.itemID == Item.blazeRod.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                  }

                  if (var2.itemID == Item.seeds.itemID || var2.itemID == Item.blueberries.itemID || var2.itemID == Item.wormRaw.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.seeds);
                  }

                  if (var2.itemID == Item.stick.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.stickPicker);
                  }

                  if (var2.itemID == Item.copperNugget.itemID || var2.itemID == Item.silverNugget.itemID || var2.itemID == Item.goldNugget.itemID || var2.itemID == Item.ironNugget.itemID || var2.itemID == Item.mithrilNugget.itemID || var2.itemID == Item.adamantiumNugget.itemID) {
                     par1EntityPlayer.triggerAchievement(AchievementList.nuggets);
                  }

                  if (var2.itemID == Item.wheat.itemID) {
                     this.worldObj.getWorldInfo().fullfillVillageCondition(1, (WorldServer)this.worldObj);
                  }

                  if (var2.itemID == Item.carrot.itemID) {
                     this.worldObj.getWorldInfo().fullfillVillageCondition(2, (WorldServer)this.worldObj);
                  }

                  if (var2.itemID == Item.potato.itemID) {
                     this.worldObj.getWorldInfo().fullfillVillageCondition(4, (WorldServer)this.worldObj);
                  }

                  if (var2.itemID == Item.onion.itemID) {
                     this.worldObj.getWorldInfo().fullfillVillageCondition(8, (WorldServer)this.worldObj);
                  }

                  this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                  par1EntityPlayer.onItemPickup(this, var3);
                  if (var2.stackSize <= 0) {
                     this.setDead();
                  }

                  if (was_empty_handed_before && par1EntityPlayer.hasHeldItem()) {
                     par1EntityPlayer.sendPacket(new Packet85SimpleSignal(EnumSignal.picked_up_held_item));
                  }
               }
            }

         }
      }
   }

   @ModifyConstant(method = {
           "isImmuneToExplosion",
           "handleExplosion",
   }, constant = @Constant(intValue = 256))
   private static int injected(int value) {
      return 1024;
   }

   @Shadow
   public EntityItem applyExplosionMotion(Explosion explosion) {
      return null;
   }

   @Shadow
   protected abstract boolean canTriggerWalking();

   @Inject(method = {"<init>(Lnet/minecraft/World;)V","<init>(Lnet/minecraft/World;DDD)V","<init>(Lnet/minecraft/World;DDDLnet/minecraft/ItemStack;)V"},at = @At("RETURN"))
   protected void injectInit(CallbackInfo callbackInfo) {
      this.canBePickUpByPlayer = true;
   }

   @Inject(method = "canBePickedUpBy",
           at = @At(value = "HEAD"),
           cancellable = true)
   public void injectPlayerCannotPickup(EntityLiving entity_living_base,CallbackInfoReturnable<Boolean> callback){
      if (entity_living_base instanceof EntityPlayer) {
         if (!this.canBePickUpByPlayer){
            callback.setReturnValue(false);
            callback.cancel();
         }
      }
   }

   @Shadow
   public ItemStack getEntityItem() {
      return null;
   }

   @Inject(method = "handleExplosion",
           cancellable = true,
           at = @At(value = "INVOKE",
                   shift = At.Shift.AFTER,
                   target = "Lnet/minecraft/EntityItem;calcExplosionForce(FD)F"))
   private void injectCancelExplosionCopy(CallbackInfoReturnable<Boolean> callback){
      if (this.isExploded) {
         this.setDead();
         this.tryRemoveFromWorldUniques();
         callback.setReturnValue(true);
         callback.cancel();
      }
   }

   @Inject(method = "readEntityFromNBT",at = @At(value = "RETURN"))
   protected void readEntityFromNBT(NBTTagCompound var1, CallbackInfo callback) {
      if (var1.hasKey("CanBePickupByPlayer")){
         this.canBePickUpByPlayer = var1.getBoolean("CanBePickupByPlayer");
      }
   }

   @Redirect(method = "handleExplosion",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityItem;tryRemoveFromWorldUniques()V"))
   private void injectUpdateExploded(EntityItem caller){
      this.isExploded = true;
      this.tryRemoveFromWorldUniques();
   }

   public void setCanBePickUpByPlayer(boolean canBePickUpByPlayer) {
      this.canBePickUpByPlayer = canBePickUpByPlayer;
   }

   public boolean canBePickUpByPlayer() {
      return canBePickUpByPlayer;
   }

   @Shadow
   public void tryRemoveFromWorldUniques() {
   }

   @Inject(method = "writeEntityToNBT",at = @At(value = "RETURN"))
   protected void writeEntityToNBT(NBTTagCompound var1,CallbackInfo callback) {
      var1.setBoolean("CanBePickupByPlayer",this.canBePickUpByPlayer);
   }
}
