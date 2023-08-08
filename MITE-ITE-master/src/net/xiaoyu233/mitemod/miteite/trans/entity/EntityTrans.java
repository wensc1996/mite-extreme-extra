package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockLeaves1;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityTrans {
   @Shadow public abstract boolean canCatchFire();

   @Shadow @Final public AxisAlignedBB boundingBox;
   @Shadow public World worldObj;
   private int netherrackWalkTime = 0;
   @Shadow public int ticksExisted;
   @Shadow
   public double posX;
   @Shadow
   public double posY;
   @Shadow
   public double posZ;
   @Shadow
   public boolean send_position_update_immediately;
   @Shadow
   public double motionX;
   @Shadow
   public double motionY;
   @Shadow
   public double motionZ;
   @Shadow
   public boolean sync_last_tick_pos_on_next_update;

   @Shadow
   protected void entityInit() {
   }
   @Overwrite
   public boolean isInFire() {
      if (this.worldObj.isTheNether() &&
              (Configs.wenscConfig.netherrackDamage.ConfigValue) &&
              Configs.wenscConfig.netherrackDamageLimitDay.ConfigValue <= this.worldObj.getDayOfOverworld() &&
              this.canCatchFire() &&
              this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.005D, 0.001D), Block.netherrack.blockID, -1)) {
         ++this.netherrackWalkTime;
         if (this.netherrackWalkTime > 20) {
            this.netherrackWalkTime = 0;
            return true;
         }
      }

      return (this.worldObj.isUnderworld() && this.boundingBox.minY <= Configs.wenscConfig.underworldMantleBlockOffset.ConfigValue + 1 || this.worldObj.isTheNether() && (this.boundingBox.minY <= 1.0D || this.boundingBox.maxY >= 123.0D)) && this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.001D, 0.001D), Block.mantleOrCore.blockID, -1) || this.worldObj.isBoundingBoxBurning(this.boundingBox.contract(0.001D, 0.001D, 0.001D), false);
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }

   @Shadow
   public boolean isEntityPlayer() {
      return false;
   }

   @Shadow
   public void setPosition(double par1, double par3, double par5) {

   }

   @Overwrite
   protected int pushOutOfBlocks() {
      if (!this.worldObj.isRemote && this.ticksExisted >= 2) {
         double original_center_y = (this.boundingBox.minY + this.boundingBox.maxY) / 2.0;
         double center_x = this.posX;
         double center_z = this.posZ;
         int x = center_x < 0.0 ? (int)center_x - 1 : (int)center_x;
         int y = original_center_y < 0.0 ? (int)original_center_y - 1 : (int)original_center_y;
         int z = center_z < 0.0 ? (int)center_z - 1 : (int)center_z;
         List collisions;
         if (!this.worldObj.isBlockFullSolidCube(x, y, z) || this.worldObj.getBlock(x, y, z) == Block.cauldron) {
            collisions = this.worldObj.getCollidingBlockBounds(this.boundingBox, ReflectHelper.dyCast(Entity.class,this));
            if (collisions.isEmpty()) {
               return 0;
            }
         }

         if (ReflectHelper.dyCast(EntityItem.class,this) instanceof EntityItem
                 && (this.worldObj.getBlock(x, y, z) instanceof BlockLeaves || this.worldObj.getBlock(x, y, z) instanceof BlockLeaves1)) {
            collisions = this.worldObj.getCollidingBlockBounds(this.boundingBox, ReflectHelper.dyCast(Entity.class,this));
            if (collisions.isEmpty()) {
               return 0;
            }
         }

         int max_escape_range = ReflectHelper.dyCast(EntityExperienceOrb.class,this) instanceof EntityExperienceOrb ? 2 : 1;
         if (ReflectHelper.dyCast(EntityLivestock.class,this) instanceof EntityLivestock || this.isEntityPlayer()) {
            max_escape_range = 3;
         }

         int matrix_size = max_escape_range * 2 + 1;
         int matrix_size_sq = matrix_size * matrix_size;
         boolean can_escape = false;
         boolean[] is_candidate_block = new boolean[matrix_size * matrix_size * matrix_size];

         int dy;
         for(int dx = -max_escape_range; dx <= max_escape_range; ++dx) {
            for(dy = -max_escape_range; dy <= max_escape_range; ++dy) {
               for(int dz = -max_escape_range; dz <= max_escape_range; ++dz) {
                  if (this.worldObj.blockExists(x + dx, y + dy, z + dz) && !this.worldObj.isBlockFullSolidCube(x + dx, y + dy, z + dz)) {
                     can_escape = true;
                     is_candidate_block[dx + max_escape_range + (dy + max_escape_range) * matrix_size + (dz + max_escape_range) * matrix_size_sq] = true;
                  }
               }
            }
         }

         if (!can_escape) {
            return -1;
         } else {
            AxisAlignedBB trial_bounding_box = this.boundingBox.copy();
            dy = (int)(Math.random() * 2.147483647E9);
            float range = 0.1F;

            while((range += 0.001F) < (float)(max_escape_range + 1)) {
               ++dy;
               double dPosX = RNG.double_1[dy & 32767] * (double)range * 2.0 - (double)range;
               ++dy;
               double dPosY = RNG.double_1[dy & 32767] * (double)range * 2.0 - (double)range;
               ++dy;
               double dPosZ = RNG.double_1[dy & 32767] * (double)range * 2.0 - (double)range;
               center_x = this.posX + dPosX;
               double center_y = original_center_y + dPosY;
               center_z = this.posZ + dPosZ;
               int trial_x = center_x < 0.0 ? (int)center_x - 1 : (int)center_x;
               int trial_y = center_y < 0.0 ? (int)center_y - 1 : (int)center_y;
               int trial_z = center_z < 0.0 ? (int)center_z - 1 : (int)center_z;
               int dx = trial_x - x;
               int var0 = trial_y - y;
               int dz = trial_z - z;
               if (dx >= -max_escape_range && dx <= max_escape_range && var0 >= -max_escape_range && var0 <= max_escape_range && dz >= -max_escape_range && dz <= max_escape_range && is_candidate_block[dx + max_escape_range + (var0 + max_escape_range) * matrix_size + (dz + max_escape_range) * matrix_size_sq]) {
                  trial_bounding_box.setBounds(this.boundingBox.minX + dPosX, this.boundingBox.minY + dPosY, this.boundingBox.minZ + dPosZ, this.boundingBox.maxX + dPosX, this.boundingBox.maxY + dPosY, this.boundingBox.maxZ + dPosZ);
                  collisions = this.worldObj.getCollidingBlockBounds(trial_bounding_box, ReflectHelper.dyCast(Entity.class,this));
                  if (collisions.isEmpty()) {
                     this.setPosition(this.posX + dPosX, this.posY + dPosY, this.posZ + dPosZ);
                     this.send_position_update_immediately = true;
                     this.motionX = this.motionY = this.motionZ = 0.0;
                     this.sync_last_tick_pos_on_next_update = true;
                     return 1;
                  }
               }
            }

            return -1;
         }
      } else {
         return 0;
      }
   }
}
