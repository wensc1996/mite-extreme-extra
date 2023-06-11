package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockGemSetting;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

public class ContainerGemSetting extends Container {
    private TileEntityGemSetting furnace;

    public ContainerGemSetting(EntityPlayer player, TileEntityGemSetting par2TileEntityFurnace)
    {
        super(player);
        this.furnace = par2TileEntityFurnace;
        this.addSlotToContainer(new SlotGem(this, par2TileEntityFurnace, 0, 39, 31));
        for(int i = 0; i < 4; ++i) {
            this.addSlotToContainer(new SlotGem(this, par2TileEntityFurnace, i + 1, 64 + i * 18, 22));
        }

        for(int i = 4; i < 8; ++i) {
            this.addSlotToContainer(new SlotGem(this, par2TileEntityFurnace, i + 1, 64 + (i - 4) * 18, 43));
        }
        // 玩家包裹
        int col;
        for (int row = 0; row < 3; ++row)
        {
            for (col = 0; col < 9; ++col)
            {
                this.addSlotToContainer(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 玩家快捷栏
        for (col = 0; col < 9; ++col)
        {
            this.addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 142));
        }
    }


    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        this.furnace.isUsing = false;
        super.onContainerClosed(par1EntityPlayer);
        if (!this.world.isRemote) {
            this.updatePlayerInventory(par1EntityPlayer);
            ItemStack var2 = this.furnace.getStackInSlotOnClosing(0);
            if (var2 != null) {
                par1EntityPlayer.dropPlayerItem(var2);
            }
            this.furnace.destroyInventory();
        }
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
    }


    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.furnace.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 != 0)
            {
                if (par2 >= 1 && par2 < 9)
                {
                    if (!this.mergeItemStack(var5, 9, 45, false))
                    {
                        return null;
                    }
                } else if (par2 >= 9 && par2 < 45 && !this.mergeItemStack(var5, 0, 9, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 9, 45, false))
            {
                return null;
            }
            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        if (!this.player.worldObj.isRemote) {
            this.updatePlayerInventory(this.player);
            // 同步玩家包裹
            ((ServerPlayer)this.player).updateCraftingInventory(this, this.getInventory());
        }
        return var3;
    }

    public TileEntityGemSetting getTileEntityFurnace()
    {
        return this.furnace;
    }

    // 同步打开的容器包裹
    public void updatePlayerInventory(EntityPlayer player) {
        ArrayList<ItemStack> itemList = new ArrayList();

        for(int index = 0; index < player.openContainer.inventorySlots.size(); ++index) {
            itemList.add(((Slot)player.openContainer.inventorySlots.get(index)).getStack());
        }

        ((ServerPlayer) player).updateCraftingInventory(player.openContainer, itemList);
    }
}
