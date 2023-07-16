package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityFishingHook.class)
public abstract class EntityFishingHookTrans extends Entity {
   @Shadow
   public EntityPlayer angler;
   @Shadow
   public Entity bobber;
   @Shadow
   public int shake;
   @Shadow
   private boolean inGround;
   @Shadow
   private int ticksCatchable;

   public EntityFishingHookTrans(World par1World) {
      super(par1World);
   }

   @Redirect(method = "catchFish",
           at = @At(value = "NEW",
                   target = "(Lnet/minecraft/World;DDDI)Lnet/minecraft/EntityExperienceOrb;"))
   private EntityExperienceOrb ctorFishingExp(World par1World, double par2, double par4, double par6, int par8){
      return new EntityExperienceOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, Configs.wenscConfig.fishingXp.ConfigValue);
   }

   @Overwrite
   private boolean checkForBite() {
      int x = MathHelper.floor_double(this.posX);
      int y = MathHelper.floor_double(this.posY - 0.20000000298023224);
      int z = MathHelper.floor_double(this.posZ);
      if (BlockFluids.isFullWaterBlock(this.worldObj, x, y, z, false) && this.worldObj.isAirBlock(x, y + 1, z)) {
         int dx = this.rand.nextInt(7) - 3;
         int dy = -this.rand.nextInt(4);
         int dz = this.rand.nextInt(7) - 3;
         if (!this.isFishInhabitedWaterBlock(x + dx, y + dy, z + dz)) {
            return false;
         } else {
            Vec3D fish_hook_position = this.worldObj.getVec3((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F));
            Vec3D fish_position = this.worldObj.getVec3((double)((float)(x + dx) + 0.5F), (double)((float)(y + dy) + 0.5F), (double)((float)(z + dz) + 0.5F));
            if (!this.worldObj.checkForLineOfPhysicalReach(fish_hook_position, fish_position)) {
               return false;
            } else {
               int time_of_day = this.worldObj.getAdjustedTimeOfDay();
               float time_factor = (float)Math.min(Math.abs(time_of_day - 5500), Math.abs(time_of_day - 17500)) / 600.0F;

               int chance_in;
               if (Configs.wenscConfig.BlnFinsh.ConfigValue) {
                  chance_in = this.worldObj.isBlueMoon(true) ? 60 : MathHelper.clamp_int(((int)(120.0f * time_factor)), 60, 240);
               } else {
                  chance_in = this.worldObj.isBlueMoon(true) ? 600 : MathHelper.clamp_int(((int)(600.0f * time_factor)), 600, 2400);
               }
               if (this.worldObj.canLightningStrikeAt(x, y + 1, z)) {
                  chance_in /= 2;
               }

               if (this.worldObj.areSkillsEnabled() && !this.angler.hasSkill(Skill.FISHING)) {
                  chance_in *= 2;
               }

               int fortune = EnchantmentManager.getFishingFortuneModifier(this.angler);

               for(int i = 0; i < fortune; ++i) {
                  chance_in = chance_in * 9 / 10;
               }

               if (this.angler.inventory.getHotbarSlotContainItem(Item.wormRaw) >= 0) {
                  chance_in = (int)((float)chance_in * 0.5F);
               }

               return this.rand.nextInt(chance_in) == 0;
            }
         }
      } else {
         return false;
      }
   }

   @Shadow
   private boolean isFishInhabitedWaterBlock(int x, int y, int z) {
      return false;
   }

   @Overwrite
   public Item getFishType() {
      int x;
      int y;
      int z;
      x = MathHelper.floor_double(this.posX);
      y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
      z = MathHelper.floor_double(this.posZ);
      long count = 0L;

      Item[] fishTypeListStage0 = new Item[]{Item.appleRed, Item.potato, Item.banana, Item.carrot, Item.orange, Item.onion, Item.melon, Item.pumpkinSeeds};

      Item[] fishTypeListStage1 = new Item[]{Item.copperNugget, Item.silverNugget, Item.goldNugget, Item.ironNugget};

      Item[] fishTypeListStage2 = new Item[]{Item.mithrilNugget, Item.ancientMetalNugget, Item.adamantiumNugget, Items.VIBRANIUM_NUGGET};

      int k;
      for( k = -16; k <= 16; ++k) {
         for(int dz = -16; dz <= 16; ++dz) {
            for(int dy = -3; dy <= 0; ++dy) {
               Block block = this.worldObj.getBlock(x + k, y + dy, z + dz);
               if (block == Block.dirt || block == Block.grass || block == Block.sand) {
                  ++count;
               }
            }
         }
      }


      if (this.rand.nextInt(3) == 0) {
         if (this.rand.nextInt(16) == 0) {
            return Items.voucherFishing;
         }

         if(this.rand.nextInt(2) == 0) {
            return fishTypeListStage0[this.rand.nextInt(8)];
         }

         if (this.rand.nextInt(2) == 0) {
            return fishTypeListStage1[this.rand.nextInt(4)];
         }

         if (this.rand.nextInt(2) == 0) {
            return fishTypeListStage2[this.rand.nextInt(4)];
         }
      }

      if (this.worldObj.getBiomeGenForCoords(x, z) == BiomeBase.ocean && count == 0L && this.rand.nextInt(10) == 0) {
         return Item.fishLargeRaw;
      }

      return Item.fishRaw;
//      if (Configs.wenscConfig.isOpenExtremeFishing.ConfigValue) {
//
//      } else if (this.rand.nextFloat() < 0.8F) {
//         return Item.fishRaw;
//      } else {
//         x = MathHelper.floor_double(this.posX);
//         y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
//         z = MathHelper.floor_double(this.posZ);
//         if (this.worldObj.getBiomeGenForCoords(x, z) != BiomeBase.ocean) {
//            return Item.fishRaw;
//         } else {
//            for(int dx = -16; dx <= 16; ++dx) {
//               for(int dz = -16; dz <= 16; ++dz) {
//                  for(int dy = -3; dy <= 0; ++dy) {
//                     Block block = this.worldObj.getBlock(x + dx, y + dy, z + dz);
//                     if (block == Block.dirt || block == Block.grass || block == Block.sand) {
//                        return Item.fishRaw;
//                     }
//                  }
//               }
//            }
//
//            return Item.fishLargeRaw;
//         }
//      }
   }
}