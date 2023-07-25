package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerShop;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

@Mixin(PlayerConnection.class)
public class ServerNetworkManagerTrans extends NetworkManagerTrans {
   @Shadow
   public ServerPlayer playerEntity;

   @Shadow
   public INetworkManager getNetManager() {
      return null;
   }

   @SoftOverride
   public void handleSyncItems(CPacketSyncItems packet) {
      ArrayList<ItemStack> itemList = new ArrayList<>();

      for(int index = 0; index < this.playerEntity.openContainer.inventorySlots.size(); ++index) {
         itemList.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(index)).getStack());
      }

      this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, itemList);
   }

   @Shadow
   public boolean isServerHandler() {
      return false;
   }

   @SoftOverride
   public void processStartForgingPacket(CPacketStartForging packet) {
      if (this.playerEntity.openContainer instanceof ContainerForgingTable) {
         ((ContainerForgingTable)this.playerEntity.openContainer).startForging();
      }
   }

   @Inject(method = "handleCustomPayload", at = @At("HEAD"))
   public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload, CallbackInfo callbackInfo) {
      DataInputStream var2;
      int var14;
      if ("MC|OpenShopGUI".equals(par1Packet250CustomPayload.channel)) {
         try {
            var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
            boolean isOpen = var2.readBoolean();
            if(!Configs.wenscConfig.isCloseShop.ConfigValue) {
               this.playerEntity.displayGUIShop();
               this.playerEntity.triggerAchievement(Achievements.openShop);
            }
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }
      if ("MC|ShopPageIndex".equals(par1Packet250CustomPayload.channel)) {
         try {
            var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
            var14 = var2.readInt();
            Container var16 = this.playerEntity.openContainer;
            if (var16 instanceof ContainerShop) {
               ((ContainerShop)var16).inventory.pageIndex = var14;
               ((ContainerShop)var16).inventory.initItemList();
            }
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }
   }
}
