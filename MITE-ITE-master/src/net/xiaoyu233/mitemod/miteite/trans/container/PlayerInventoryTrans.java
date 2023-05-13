package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemDynamicCore;
import net.xiaoyu233.mitemod.miteite.item.ItemRingKiller;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryTrans {
   @Shadow
   public ItemStack[] mainInventory = new ItemStack[36];

   public ItemStack[] jewelryInventory = new ItemStack[8];

   @Shadow
   public ItemStack[] armorInventory = new ItemStack[4];

   @Shadow
   public EntityPlayer player;

   @Shadow
   public int currentItem;
   @Shadow
   private ItemStack f;
   @Shadow
   private ItemStack itemStack;
   @Shadow
   public boolean inventoryChanged;

   //Fix the bug of getting items invalidly
   @Inject(method = "trySwitchItemOrRestock",at = @At(value = "HEAD"))
   private void injectItemsSync(CallbackInfoReturnable<Boolean> callbackInfo) {
      this.player.sendPacket(new CPacketSyncItems());
   }
   // 这里会在放置物品时触发
//   @Inject(method = "addItemStackToInventoryOrDropIt",at = @At(value = "HEAD"))
//   public void addItemStackToInventoryOrDropIt(ItemStack item_stack, CallbackInfo callbackInfo) {
//      this.player.addChatMessage("检测");
//   }

   public ItemStack getRingKiller() {
      ItemStack itemStack = null;
      for(int i = 0; i < this.jewelryInventory.length; ++i) {
         if (this.jewelryInventory[i] != null && (this.jewelryInventory[i].getItem() instanceof ItemRingKiller)) {
            if(itemStack == null) {
               itemStack = this.jewelryInventory[i];
            } else {
               if(((ItemRingKiller) this.jewelryInventory[i].getItem()).getLevel() > ((ItemRingKiller)itemStack.getItem()).getLevel()) {
                  itemStack = this.jewelryInventory[i];
               }
            }
         }
      }
      return itemStack;
   }

   public ItemStack getDynamicCore() {
      for(int i = 0; i < this.jewelryInventory.length; ++i) {
         if (this.jewelryInventory[i] != null && (this.jewelryInventory[i].getItem() instanceof ItemDynamicCore)) {
            return this.jewelryInventory[i];
         }
      }
      return null;
   }
   
   @Overwrite
   public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
      int var2;
      NBTTagCompound var3;
      for(var2 = 0; var2 < this.mainInventory.length; ++var2) {
         if (this.mainInventory[var2] != null) {
            var3 = new NBTTagCompound();
            var3.setByte("Slot", (byte)var2);
            this.mainInventory[var2].writeToNBT(var3);
            par1NBTTagList.appendTag(var3);
         }
      }

      for(var2 = 0; var2 < this.jewelryInventory.length; ++var2) {
         if (this.jewelryInventory[var2] != null) {
            var3 = new NBTTagCompound();
            var3.setByte("Slot", (byte)(var2 + 100));
            this.jewelryInventory[var2].writeToNBT(var3);
            par1NBTTagList.appendTag(var3);
         }
      }

      for(var2 = 0; var2 < this.armorInventory.length; ++var2) {
         if (this.armorInventory[var2] != null) {
            var3 = new NBTTagCompound();
            var3.setByte("Slot", (byte)(var2 + 100 + this.jewelryInventory.length));
            this.armorInventory[var2].writeToNBT(var3);
            par1NBTTagList.appendTag(var3);
         }
      }
      return par1NBTTagList;
   }

   @Overwrite
   public void readFromNBT(NBTTagList par1NBTTagList) {
      this.mainInventory = new ItemStack[36];
      this.jewelryInventory = new ItemStack[8];
      this.armorInventory = new ItemStack[4];

      for(int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
         NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
         int var4 = var3.getByte("Slot") & 255;
         ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
         if (var5 != null) {
            if (var4 >= 0 && var4 < this.mainInventory.length) {
               this.mainInventory[var4] = var5;
            }

            if (var4 >= 100 && var4 < this.jewelryInventory.length + 100) {
               this.jewelryInventory[var4 - 100] = var5;
            } else if (var4 >= 100 && var4 < this.armorInventory.length + this.jewelryInventory.length + 100) {
               this.armorInventory[var4 - 100 - this.jewelryInventory.length] = var5;
            }
         }
      }

   }
   @Overwrite
   public int getSizeInventory() {
      return this.mainInventory.length + this.armorInventory.length + this.jewelryInventory.length;
   }

   @Overwrite
   public ItemStack decrStackSize(int par1, int par2) {

      ItemStack[] var3 = this.mainInventory;
      ItemStack[] val4 = this.jewelryInventory;
      if(par1 >= var3.length + val4.length) {
         par1 -= var3.length +val4.length;
         var3 = this.armorInventory;
      } else if (par1 >= var3.length) {
         par1 -= var3.length;
         var3 = this.jewelryInventory;
      }

      if (var3[par1] != null) {
         ItemStack var4;
         if (var3[par1].stackSize <= par2) {
            var4 = var3[par1];
            var3[par1] = null;
            return var4;
         } else {
            var4 = var3[par1].splitStack(par2);
            if (var3[par1].stackSize == 0) {
               var3[par1] = null;
            }
            return var4;
         }
      } else {
         return null;
      }
   }

   @Overwrite
   public ItemStack getStackInSlot(int par1) {
      ItemStack[] var2 = this.mainInventory;
      ItemStack[] val3 = this.jewelryInventory;
      if(par1 >= var2.length + val3.length) {
         par1 -= var2.length + val3.length;
         var2 = this.armorInventory;
      } else if (par1 >= var2.length) {
         par1 -= var2.length;
         var2 = this.jewelryInventory;
      }
      return var2[par1];
   }
   @Shadow
   public void inventorySlotChangedOnServer(int slot_index){};

   @Overwrite
   public int getSlotIndex(ItemStack item_stack) {
      int i;
      for(i = 0; i < this.mainInventory.length; ++i) {
         if (this.mainInventory[i] == item_stack) {
            return i;
         }
      }

      for(i = 0; i < this.jewelryInventory.length; ++i) {
         if (this.jewelryInventory[i] == item_stack) {
            return i + this.mainInventory.length;
         }
      }

      for(i = 0; i < this.armorInventory.length; ++i) {
         if (this.armorInventory[i] == item_stack) {
            return i + this.mainInventory.length + this.jewelryInventory.length;
         }
      }
      return -1;
   }

   @Overwrite
   public void destroyInventoryItemStack(int slot_index, ItemStack[] inventory) {
      ItemStack item_stack = inventory[slot_index];
      if (item_stack != null) {
         if (inventory == this.jewelryInventory) {
            slot_index += this.mainInventory.length;
         }

         if (inventory == this.armorInventory) {
            slot_index += this.mainInventory.length + this.jewelryInventory.length;
         }

         this.setInventorySlotContents(slot_index, (ItemStack)null);
      }
   }
   @Shadow
   public int getSlotIndex(ItemStack item_stack, ItemStack[] inventory) { return -1; }

   @Overwrite
   public void destroyInventoryItemStack(ItemStack item_stack) {
      if (item_stack != null) {
         int slot_index = this.getSlotIndex(item_stack, this.mainInventory);
         if (slot_index >= 0) {
            this.destroyInventoryItemStack(slot_index, this.mainInventory);
         } else {
            slot_index = this.getSlotIndex(item_stack, this.jewelryInventory);
            if (slot_index >= 0) {
               this.destroyInventoryItemStack(slot_index, this.jewelryInventory);
            } else {
               slot_index = this.getSlotIndex(item_stack, this.armorInventory);
               if(slot_index >= 0) {
                  this.destroyInventoryItemStack(slot_index, this.armorInventory);
               }
            }
         }
      }
   }
   @Overwrite
   public int calcChecksum(int for_release_number) {
      int checksum = 0;

      int i;
      ItemStack item_stack;
      for(i = 0; i < this.mainInventory.length; ++i) {
         item_stack = this.mainInventory[i];
         if (item_stack != null) {
            checksum += item_stack.calcChecksum(for_release_number);
         }
      }

      for(i = 0; i < this.jewelryInventory.length; ++i) {
         item_stack = this.jewelryInventory[i];
         if (item_stack != null) {
            checksum += item_stack.calcChecksum(for_release_number);
         }
      }

      for(i = 0; i < this.armorInventory.length; ++i) {
         item_stack = this.armorInventory[i];
         if (item_stack != null) {
            checksum += item_stack.calcChecksum(for_release_number);
         }
      }

      return checksum;
   }
   @Overwrite
   public void destroyInventory() {
      ItemStack[] item_stacks = this.mainInventory;

      int i;
      for(i = 0; i < item_stacks.length; ++i) {
         item_stacks[i] = null;
      }

      item_stacks = this.jewelryInventory;

      for(i = 0; i < item_stacks.length; ++i) {
         item_stacks[i] = null;
      }

      item_stacks = this.armorInventory;

      for(i = 0; i < item_stacks.length; ++i) {
         item_stacks[i] = null;
      }

   }

   @Overwrite
   public int getNumItems(Item item) {
      int num = 0;

      int i;
      for(i = 0; i < this.mainInventory.length; ++i) {
         if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == item) {
            num += this.mainInventory[i].stackSize;
         }
      }

      for(i = 0; i < this.jewelryInventory.length; ++i) {
         if (this.jewelryInventory[i] != null && this.jewelryInventory[i].getItem() == item) {
            num += this.jewelryInventory[i].stackSize;
         }
      }

      for(i = 0; i < this.armorInventory.length; ++i) {
         if (this.armorInventory[i] != null && this.armorInventory[i].getItem() == item) {
            num += this.armorInventory[i].stackSize;
         }
      }

      return num;
   }

   @Overwrite
   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      ItemStack[] var3 = this.mainInventory;
      ItemStack[] val4 = this.jewelryInventory;
      if(par1 >= var3.length + val4.length) {
         par1 -= var3.length +val4.length;
         var3 = this.armorInventory;
      } else if (par1 >= var3.length) {
         par1 -= var3.length;
         var3 = this.jewelryInventory;
      }
      if(var3 == this.jewelryInventory && par1 == 7 && par2ItemStack != null) {
         if(!Configs.wenscConfig.isCloseShop.ConfigValue) {
            if(par2ItemStack.getItem().getSoldPrice() > 0D) {
               if(!this.player.worldObj.isRemote) {
                  player.addChatMessage("现有余额：" + String.format("%.2f", player.plusMoney(par2ItemStack.stackSize * par2ItemStack.getItem().getSoldPrice())));
               }
            }
         }
         var3[par1] = null;
         return;
      }
      if (var3[par1] != par2ItemStack) {
         if(par2ItemStack != null ) {
            if(par2ItemStack.getItem().itemID != Items.lavaInPipes.itemID) {
               var3[par1] = par2ItemStack;
               if (var3 == this.mainInventory && !this.player.worldObj.isRemote) {
                  this.inventorySlotChangedOnServer(par1);
               }
            } else {
               var3[par1] = null;
               if (var3 == this.mainInventory && !this.player.worldObj.isRemote) {
                  this.inventorySlotChangedOnServer(par1);
               }
            }
         } else {

            var3[par1] = par2ItemStack;
            if (var3 == this.mainInventory && !this.player.worldObj.isRemote) {
               this.inventorySlotChangedOnServer(par1);
            }
         }
      }

   }

   @Overwrite
   public ItemStack getStackInSlotOnClosing(int par1) {
      ItemStack[] var2 = this.mainInventory;
      ItemStack[] var4 = this.jewelryInventory;
      if(par1 >= var2.length + var4.length) {
         var2 = this.armorInventory;
         par1-= var2.length + var4.length;
      } else if (par1 >= var2.length) {
         var2 = var4;
         par1 -= this.mainInventory.length;
      }
      if (var2[par1] != null) {
         ItemStack var3 = var2[par1];
         var2[par1] = null;
         return var3;
      } else {
         return null;
      }
   }

   @Overwrite
   public void dropAllItems() {
      int var1;
      for(var1 = 0; var1 < this.mainInventory.length; ++var1) {
         if (this.mainInventory[var1] != null) {
            this.player.dropPlayerItemWithRandomChoice(this.mainInventory[var1], true);
            this.mainInventory[var1] = null;
         }
      }

      for(var1 = 0; var1 < this.jewelryInventory.length; ++var1) {
         if (this.jewelryInventory[var1] != null) {
            this.player.dropPlayerItemWithRandomChoice(this.jewelryInventory[var1], true);
            this.jewelryInventory[var1] = null;
         }
      }

      for(var1 = 0; var1 < this.armorInventory.length; ++var1) {
         if (this.armorInventory[var1] != null) {
            this.player.dropPlayerItemWithRandomChoice(this.armorInventory[var1], true);
            this.armorInventory[var1] = null;
         }
      }
      this.player.sendPacket(new Packet85SimpleSignal(EnumSignal.clear_inventory));
   }


   @Overwrite
   public ItemStack getInventorySlotContents(int index) {
      if(index >= this.mainInventory.length + this.jewelryInventory.length) {
         return this.armorInventory[index - this.mainInventory.length - this.jewelryInventory.length];
      } else if(index >= this.mainInventory.length) {
         return this.jewelryInventory[index - this.mainInventory.length];
      } else {
         return this.mainInventory[index];
      }
   }

   @Overwrite
   public void copyInventory(PlayerInventory par1InventoryPlayer) {
      int var2;
      for(var2 = 0; var2 < this.mainInventory.length; ++var2) {
         this.mainInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[var2]);
      }

      for(var2 = 0; var2 < this.armorInventory.length; ++var2) {
         this.armorInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[var2]);
      }

      for(var2 = 0; var2 < this.jewelryInventory.length; ++var2) {
         this.jewelryInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.jewelryInventory[var2]);
      }

      this.currentItem = par1InventoryPlayer.currentItem;
   }
}


