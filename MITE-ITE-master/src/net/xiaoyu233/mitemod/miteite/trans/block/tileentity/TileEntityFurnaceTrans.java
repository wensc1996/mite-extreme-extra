package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityFurnace.class)
public abstract class TileEntityFurnaceTrans extends TileEntity {
   @Shadow public abstract BlockFurnace getFurnaceBlock();

   @Shadow
   private final ItemStack[] furnaceItemStacks = new ItemStack[3];
   @Shadow
   public int currentItemBurnTime;
   @Shadow
   public int furnaceBurnTime;
   @Shadow
   public int furnaceCookTime;
   @Shadow
   public int heat_level = 0;

   public int damage;
   @Inject(method = "readFromNBT",at = @At("RETURN"))
   public void injectReadNBT(NBTTagCompound nbtTagCompound, CallbackInfo callbackInfo) {
      this.damage = nbtTagCompound.getInteger("Damage");
   }
   @Inject(method = "writeToNBT",at = @At("RETURN"))
   public void injectWriteNBT(NBTTagCompound nbtTagCompound, CallbackInfo callbackInfo) {
      nbtTagCompound.setInteger("Damage", this.damage);
   }

   public void addDamage(World world, int x, int y, int z, int amount) {
      if (world.isRemote) {
         Minecraft.setErrorMessage("addDamage: why adding damage to furnace on client?");
      }
      if(this.getFurnaceBlock().blockMaterial != Materials.vibranium){
         this.damage += amount;
      }

      if (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) instanceof BlockFurnace){
         BlockFurnace block = (BlockFurnace)world.getBlock(x, y, z);
         if(this.damage >= block.getDurability()){
            world.setBlock(x, y, z, Block.fire.blockID);
         }
      }
   }


   @Overwrite
   public static int getHeatLevelRequired(int item_id) {
      // 高燃染料=振金，艾德曼
      if (item_id == Block.coalBlock.blockID || item_id == Blocks.blockVibranium.blockID || item_id == Block.blockAdamantium.blockID) {
         return 5;
         // 烈焰棒=秘银块，远古金属块
      } else if (item_id == Block.oreAdamantium.blockID || item_id == Blocks.netherAdamantiumOre.blockID || item_id == Block.blockMithril.blockID || item_id == Block.blockAncientMetal.blockID) {
         return 4;
         // 岩浆=铁块，金块，银块，铜块
      } else if (item_id == Block.oreMithril.blockID || item_id == Block.blockIron.blockID || item_id == Block.blockCopper.blockID || item_id == Block.blockSilver.blockID || item_id == Block.blockGold.blockID) {
         return 3;
      } else if (item_id == Block.oreCopper.blockID || item_id == Block.oreSilver.blockID || item_id == Block.oreGold.blockID || item_id == Block.oreIron.blockID) {
         return 2;
      } else if (item_id == Block.oreNetherQuartz.blockID || item_id == Block.oreEmerald.blockID || item_id == Block.oreDiamond.blockID || item_id == Block.oreRedstone.blockID) {
         return 2;
      } else if (item_id == Block.oreLapis.blockID) {
         return 2;
      } else if (item_id == Block.sandStone.blockID) {
         return 2;
      } else {
         return 1;
      }
   }

   @Shadow
   public boolean canSmelt(int heat_level) {
      return false;
   }

   @Overwrite
   public int d(int par1) {
      return this.furnaceItemStacks[0] == null ? this.furnaceCookTime * par1 / 200 : this.furnaceCookTime * par1 / (this.furnaceItemStacks[0].getItem().getCookTime() / Math.max(this.heat_level, 1));
   }

   @Overwrite
   public void smeltItem(int heat_level) {
      if (this.canSmelt(heat_level)) {
         ItemStack var1 = RecipesFurnace.smelting().getSmeltingResult(this.getInputItemStack(), heat_level);
         if (this.furnaceItemStacks[2] == null) {
            this.furnaceItemStacks[2] = var1.copy();
         } else if (this.furnaceItemStacks[2].itemID == var1.itemID) {
            this.furnaceItemStacks[2].stackSize += var1.stackSize;
         }

         int consumption;
         if (this.getInputItemStack().itemID == Block.sand.blockID && var1.itemID == Block.sandStone.blockID) {
            consumption = 4;
         } else if (this.getInputItemStack().itemID == Block.sand.blockID && var1.itemID == Block.glass.blockID) {
            consumption = 4;
         } else {
            consumption = 1;
         }

         ItemStack inputItemStack = this.getInputItemStack();
         inputItemStack.stackSize -= consumption;
         if (this.getInputItemStack().getItem() == Item.clay && var1.getItem() == Item.brick) {
            int extra_converted = Math.min(this.getOutputItemStack().getMaxStackSize() - this.getOutputItemStack().stackSize, this.getInputItemStack().stackSize);
            if (extra_converted > 3) {
               extra_converted = 3;
            }

            inputItemStack = this.getOutputItemStack();
            inputItemStack.stackSize += extra_converted;
            inputItemStack = this.getInputItemStack();
            inputItemStack.stackSize -= extra_converted;
         }

         if (this.furnaceItemStacks[0].stackSize <= 0) {
            this.furnaceItemStacks[0] = null;
         }
      }

   }

   @Overwrite
   public void updateEntity() {
      if (super.worldObj.isRemote || this.furnaceBurnTime == 1 || !this.isFlooded() && !this.isSmotheredBySolidBlock()) {
         boolean var1 = this.furnaceBurnTime > 0;
         boolean var2 = false;
         if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
         } else {
            this.heat_level = 0;
         }

         if (!super.worldObj.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt(this.getFuelHeatLevel())) {
               this.currentItemBurnTime = this.furnaceBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
               if (this.furnaceBurnTime > 0) {
                  this.heat_level = this.getItemHeatLevel(this.furnaceItemStacks[1]);
                  var2 = true;
                  if (this.furnaceItemStacks[1] != null) {
                     --this.furnaceItemStacks[1].stackSize;
                     if (this.furnaceItemStacks[1].stackSize == 0) {
                        Item var3 = this.furnaceItemStacks[1].getItem().getContainerItem();
                        this.furnaceItemStacks[1] = var3 != null ? new ItemStack(var3) : null;
                     }
                  }
               }
            }

            if (this.isBurning() && this.canSmelt(this.heat_level)) {
               ++this.furnaceCookTime;
               if (this.furnaceCookTime == this.furnaceItemStacks[0].getItem().getCookTime() / this.heat_level) {
                  this.furnaceCookTime = 0;
                  this.smeltItem(this.heat_level);

                  this.addDamage(this.worldObj, super.xCoord, super.yCoord, super.zCoord, this.heat_level * 15);

                  var2 = true;
               }
            } else {
               this.furnaceCookTime = 0;
            }

            if (var1 != this.furnaceBurnTime > 0) {
               var2 = true;
               BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, super.worldObj, super.xCoord, super.yCoord, super.zCoord);
            }
         }

         if (var2) {
            super.onInventoryChanged();
         }
      } else {
         if (this.furnaceBurnTime > 0) {
            if (this.isFlooded()) {
               super.worldObj.blockFX(EnumBlockFX.steam, super.xCoord, super.yCoord, super.zCoord);
            }

            BlockFurnace.updateFurnaceBlockState(false, super.worldObj, super.xCoord, super.yCoord, super.zCoord);
         }

         this.furnaceBurnTime = 0;
         this.furnaceCookTime = 0;
      }
   }
   @Shadow
   public int getFuelHeatLevel() {
      return 0;
   }

   @Shadow
   public ItemStack getInputItemStack() {
      return this.furnaceItemStacks[0];
   }

   @Shadow
   public int getItemBurnTime(ItemStack itemStack) {
      return 0;
   }

   @Shadow
   public int getItemHeatLevel(ItemStack itemStack) {
      return 0;
   }

   @Shadow
   public ItemStack getOutputItemStack() {
      return this.furnaceItemStacks[2];
   }

   @Shadow
   public boolean isBurning() {
      return false;
   }

   @Shadow
   public boolean isFlooded() {
      return false;
   }

   @Shadow
   public boolean isSmotheredBySolidBlock() {
      return false;
   }
}
