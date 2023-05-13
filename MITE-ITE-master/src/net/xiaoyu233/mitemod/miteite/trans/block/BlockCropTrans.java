package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.util.Utils;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockCrops.class)
public class BlockCropTrans extends BlockGrowingPlant {

   public BlockCropTrans(int block_id) {
      super(block_id);
   }

   @Overwrite
   public boolean fertilize(World world, int x, int y, int z, ItemStack item_stack) {
      Item item = item_stack.getItem();
      if (item != Item.dyePowder && item != Item.manure) {
         return false;
      } else {
         int metadata = world.getBlockMetadata(x, y, z);
         return this.isBlighted(metadata) && world.setBlockMetadata(x, y, z, this.setBlighted(metadata, false), 2);
      }
   }

   @Shadow
   public static void playCropPopSound(BlockBreakInfo info) {}

   @Shadow
   public int getGrowth(int metadata) {
      return 0;
   }
   @Shadow
   public boolean isMature(int metadata) {
      return true;
   }

   @Shadow
   protected int getSeedItem() {
      return Item.seeds.itemID;
   }

   @Shadow
   protected int getCropItem() {
      return Item.wheat.itemID;
   }

   @Shadow
   protected int getMatureYield() {
      return 1;
   }

   public int dropBlockAsEntityItem(BlockBreakInfo info) {
      if (!info.wasHarvestedByPlayer() && !info.wasDrought() && !info.wasSnowedUpon() && !info.wasSelfDropped()) {
         return 0;
      } else {
         if (info.wasDrought()) {
            playCropPopSound(info);
         }

         if (this.isBlighted(info.getMetadata())) {
            return 0;
         } else {
            ItemStack item_stack = info.getHarvesterItemStack();
            Item item = item_stack == null ? null : item_stack.getItem();
            ItemTool tool = item instanceof ItemTool ? (ItemTool)item : null;
            float harvesting_enchantment;
            if (tool != null && tool.isEffectiveAgainstBlock(this, info.getMetadata())) {
               harvesting_enchantment = EnchantmentManager.getEnchantmentLevelFraction(Enchantment.harvesting, info.getHarvesterItemStack()) * 0.5F;
            } else {
               harvesting_enchantment = 0.0F;
            }

            int num_drops;
            if (this.getGrowth(info.getMetadata()) == 0) {
               num_drops = this.dropBlockAsEntityItem(info, this.getSeedItem(), 0, 1, 1.0F);
            } else {
               if (!this.isMature(info.getMetadata()) || info.wasSelfDropped()) {
                  return 0;
               }

               if(info.world.rand.nextInt(50) == 0) {
                  this.dropBlockAsEntityItem(info, new ItemStack(Items.voucherPlanting, 1));
               }

               num_drops = this.dropBlockAsEntityItem(info, this.getCropItem(), 0, this.getMatureYield(), 1.0F + harvesting_enchantment);
            }

            if (info.wasSnowedUpon() && num_drops > 0) {
               playCropPopSound(info);
            }

            return num_drops;
         }
      }
   }

   @Shadow
   private boolean isBlighted(int metadata) {
      return false;
   }

   @Shadow
   private int setBlighted(int metadata, boolean b) {
      return 0;
   }

   @Shadow
   public float getLowestOptimalTemperature() {
      return 0;
   }

   @Shadow
   public float getHighestOptimalTemperature() {
      return 0;
   }

   @Shadow
   public float getHumidityGrowthRateModifier(boolean b) {
      return 0;
   }

   @Shadow
   public float getGrowthRate(World world, int i, int i1, int i2) {
      return 0;
   }
}
