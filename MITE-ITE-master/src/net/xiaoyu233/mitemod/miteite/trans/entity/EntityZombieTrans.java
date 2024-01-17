package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.Arrays;

@Mixin(EntityZombie.class)
class EntityZombieTrans extends EntityAnimalWatcher {
   @Shadow
   @Final
   protected static IAttribute field_110186_bp;

   public EntityZombieTrans(World world) {
      super(world);
   }

   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
//      if (this.worldObj.isUnderworld() && this.worldObj.getDayOfOverworld() < 64) {
//
//      }
      MonsterUtil.addDefaultArmor(this.worldObj.getDayOfOverworld(), this, true);
   }

   public void addRandomWeapon() {
      int day_of_world = MinecraftServer.F().getOverworld().getDayOfOverworld();
      super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, day_of_world >= 32));
   }

   private Item getWeapon(int day){
      day += this.worldObj.isUnderworld() ? 32 : 0;
      int weight = day / 32 + rand.nextInt(3) - 1;
      int weaponIndex = Math.max(Math.min(weight, Constant.SWORDS.length - 1),0);
      return Constant.SWORDS[weaponIndex][rand.nextInt(Constant.SWORDS[weaponIndex].length)];
//      return Constant.SWORDS[Math.max(Math.min(day / 64,Constant.SWORDS.length - 1),0)];
   }

   @Overwrite
   protected int getConversionTimeBoost() {
      int var1 = 1;
      if (this.rand.nextFloat() < 0.01F) {
         int var2 = 0;

         for(int var3 = (int)this.posX - 4; var3 < (int)this.posX + 4 && var2 < 14; ++var3) {
            for(int var4 = (int)this.posY - 4; var4 < (int)this.posY + 4 && var2 < 14; ++var4) {
               for(int var5 = (int)this.posZ - 4; var5 < (int)this.posZ + 4 && var2 < 14; ++var5) {
                  int var6 = this.worldObj.getBlockId(var3, var4, var5);
                  if (var6 == Block.fenceIron.blockID || Arrays.stream(Constant.bedBlockTypes).anyMatch(e -> e.blockID == var6)) {
                     if (this.rand.nextFloat() < 0.3F) {
                        ++var1;
                     }

                     ++var2;
                  }
               }
            }
         }
      }

      return var1;
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld() - 64, 0) : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23000000417232513D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 8D + (double)day / 24.0D);
      this.setEntityAttribute(GenericAttributes.maxHealth, 30D + (double)day / 16.0D);
      this.setEntityAttribute(field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
   }

   @Override
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.15D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }


   //
//      EntityDamageResult result = super.attackEntityFrom(damage);
//      if (result != null && !result.entityWasDestroyed() && result.entityWasNegativelyAffected() && damage.wasCausedByPlayer()) {
//         this.is_smart = true;
//      }
//
//      return result;

   @Override
   protected float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f,0.25f);
   }
}
