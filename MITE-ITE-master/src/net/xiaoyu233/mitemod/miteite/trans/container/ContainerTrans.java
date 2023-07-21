package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerShop;
import net.xiaoyu233.mitemod.miteite.inventory.container.SlotShop;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(Container.class)
public abstract class ContainerTrans {
    @Shadow
    public final EntityPlayer player;
    @Shadow
    private int field_94536_g;
    @Shadow
    private final Set field_94537_h = new HashSet();
    @Shadow

    public Slot slot_that_did_not_accept_item;
    @Shadow
    public List inventorySlots = new ArrayList();

    public ContainerTrans(EntityPlayer player) {
        this.player = player;
    }

    @Shadow
    public Slot getSlot(int par1) {
        return null;
    }
    @Shadow
    private final boolean handleMiddleMouseClick(EntityPlayer player, int gui_slot_index, boolean shift_is_down){
        return false;
    }

    @Shadow
    public static int func_94532_c(int par0) {
        return par0 & 3;
    }
    @Shadow
    protected void func_94533_d() {}
    @Shadow
    private int field_94535_f = -1;
    @Shadow
    public static int func_94529_b(int par0) {return 0;}
    @Shadow
    public static boolean func_94528_d(int par0) {
        return false;
    }
    @Shadow
    public static boolean func_94527_a(Slot par0Slot, ItemStack par1ItemStack, boolean par2) {
        return false;
    }

    @Shadow
    public static void func_94525_a(Set par0Set, int par1, ItemStack par2ItemStack, int par3) {}

    @Shadow
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {return null;}

    @Shadow
    private final void retrySlotClick(int par1, int par2, boolean par3, boolean holding_shift, EntityPlayer par4EntityPlayer) {}

    @Shadow
    public void detectAndSendChanges() {}

    @Shadow
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
        return true;
    }
    @Shadow
    public boolean canDragIntoSlot(Slot par1Slot) {
        return true;
    }

    @Overwrite
    public final ItemStack slotClick(int par1, int par2, int par3, boolean holding_shift, EntityPlayer par4EntityPlayer) {

        if(par1 >= 0 && this.getSlot(par1) instanceof SlotShop) {
            this.getSlot(par1).onSlotClicked(par4EntityPlayer, holding_shift ? 1 : 0 , this.getSlot(par1).getContainer());
            return null;
        }

        if (par1 == 2) {
            Container container = this.getSlot(par1).getContainer();
            if (container instanceof ContainerAnvil && container.repair_fail_condition != 0) {
                return null;
            }
        }

        if (par2 == 2 && par3 == 3 && par1 != -999 && !par4EntityPlayer.inCreativeMode() && this.getSlot(par1).getHasStack() && this.handleMiddleMouseClick(par4EntityPlayer, par1, holding_shift)) {
            return null;
        } else {
            ItemStack var5 = null;
            PlayerInventory var6 = par4EntityPlayer.inventory;
            int var9;
            ItemStack var17;
            ItemStack var19;
            int var22;
            if (par3 == 5) {
                int var7 = this.field_94536_g;
                this.field_94536_g = func_94532_c(par2);
                if ((var7 != 1 || this.field_94536_g != 2) && var7 != this.field_94536_g) {
                    this.func_94533_d();
                } else if (var6.getItemStack() == null) {
                    this.func_94533_d();
                } else if (this.field_94536_g == 0) {
                    this.field_94535_f = func_94529_b(par2);
                    if (func_94528_d(this.field_94535_f)) {
                        this.field_94536_g = 1;
                        this.field_94537_h.clear();
                    } else {
                        this.func_94533_d();
                    }
                } else if (this.field_94536_g == 1) {
                    Slot var8 = (Slot)this.inventorySlots.get(par1);
                    if (var8 != null && func_94527_a(var8, var6.getItemStack(), true) && var8.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(var8)) {
                        this.field_94537_h.add(var8);
                    }
                } else if (this.field_94536_g == 2) {
                    if (!this.field_94537_h.isEmpty()) {
                        var17 = var6.getItemStack().copy();
                        var9 = var6.getItemStack().stackSize;
                        Iterator var10 = this.field_94537_h.iterator();

                        while(var10.hasNext()) {
                            Slot var11 = (Slot)var10.next();
                            if (var11 != null && func_94527_a(var11, var6.getItemStack(), true) && var11.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(var11)) {
                                var19 = var17.copy();
                                var22 = var11.getHasStack() ? var11.getStack().stackSize : 0;
                                func_94525_a(this.field_94537_h, this.field_94535_f, var19, var22);
                                if (var19.stackSize > var19.getMaxStackSize()) {
                                    var19.stackSize = var19.getMaxStackSize();
                                }

                                if (var19.stackSize > var11.getSlotStackLimit()) {
                                    var19.stackSize = var11.getSlotStackLimit();
                                }

                                var9 -= var19.stackSize - var22;
                                var11.putStack(var19);
                            }
                        }

                        var17.stackSize = var9;
                        if (var17.stackSize <= 0) {
                            var17 = null;
                        }

                        var6.setItemStack(var17);
                    }

                    this.func_94533_d();
                } else {
                    this.func_94533_d();
                }
            } else if (this.field_94536_g != 0) {
                this.func_94533_d();
            } else {
                Slot var16;
                int var21;
                ItemStack var23;
                if ((par3 == 0 || par3 == 1) && (par2 == 0 || par2 == 1)) {
                    if (par1 == -999) {
                        if (var6.getItemStack() != null && par1 == -999) {
                            if (par2 == 0) {
                                par4EntityPlayer.dropPlayerItem(var6.getItemStack());
                                var6.setItemStack((ItemStack)null);
                            }

                            if (par2 == 1) {
                                par4EntityPlayer.dropPlayerItem(var6.getItemStack().splitStack(1));
                                if (var6.getItemStack().stackSize == 0) {
                                    var6.setItemStack((ItemStack)null);
                                }
                            }
                        }
                    } else if (par3 == 1 && par1 >= 0 && !(this.getSlot(par1) instanceof SlotResult)) {
                        if (par1 < 0) {
                            return null;
                        }

                        var16 = (Slot)this.inventorySlots.get(par1);
                        if (var16 != null && var16.canTakeStack(par4EntityPlayer)) {
                            var17 = this.transferStackInSlot(par4EntityPlayer, par1);
                            if (var17 != null) {
                                var9 = var17.itemID;
                                var5 = var17.copy();
                                if (var16 != null && var16.getStack() != null && var16.getStack().itemID == var9) {
                                    this.retrySlotClick(par1, par2, true, holding_shift, par4EntityPlayer);
                                }
                            }
                        }
                    } else {
                        if (par1 < 0) {
                            return null;
                        }

                        var16 = (Slot)this.inventorySlots.get(par1);
                        if (var16 != null) {
                            var17 = var16.getStack();
                            var19 = var6.getItemStack();
                            if (var17 != null) {
                                var5 = var17.copy();
                            }

                            if (var17 == null) {
                                if (var19 != null && var16.isItemValid(var19)) {
                                    var21 = par2 == 0 ? var19.stackSize : 1;
                                    if (var21 > var16.getSlotStackLimit()) {
                                        var21 = var16.getSlotStackLimit();
                                    }

                                    if (var19.stackSize >= var21) {
                                        var16.putStack(var19.splitStack(var21));
                                    }

                                    if (var19.stackSize == 0) {
                                        var6.setItemStack((ItemStack)null);
                                    }
                                }

                                if (var19 != null && !var16.isItemValid(var19)) {
                                    this.slot_that_did_not_accept_item = var16;
                                }
                            } else if (var16.canTakeStack(par4EntityPlayer)) {
                                if (var19 == null) {
                                    var21 = par2 == 0 ? var17.stackSize : (var17.stackSize + 1) / 2;
                                    var23 = var16.decrStackSize(var21);
                                    var6.setItemStack(var23);
                                    if (var17.stackSize == 0) {
                                        var16.putStack((ItemStack)null);
                                    }

                                    var16.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
                                } else if (var16.isItemValid(var19)) {
                                    if (ItemStack.areItemStacksEqual(var17, var19, true)) {
                                        var21 = par2 == 0 ? var19.stackSize : 1;
                                        if (var21 > var16.getSlotStackLimit() - var17.stackSize) {
                                            var21 = var16.getSlotStackLimit() - var17.stackSize;
                                        }

                                        if (var21 > var19.getMaxStackSize() - var17.stackSize) {
                                            var21 = var19.getMaxStackSize() - var17.stackSize;
                                        }

                                        var19.splitStack(var21);
                                        if (var19.stackSize == 0) {
                                            var6.setItemStack((ItemStack)null);
                                        }

                                        var17.stackSize += var21;
                                    } else if (var19.stackSize <= var16.getSlotStackLimit()) {
                                        var16.putStack(var19);
                                        var6.setItemStack(var17);
                                    }
                                } else if (var19.getMaxStackSize() > 1 && ItemStack.areItemStacksEqual(var17, var19, true, false, true)) {
                                    var21 = par2 == 0 ? var17.stackSize : (var17.stackSize + 1) / 2;
                                    if (var21 + var19.stackSize > var19.getMaxStackSize()) {
                                        var21 = var19.getMaxStackSize() - var19.stackSize;
                                    }

                                    if (var21 > 0 && var21 + var19.stackSize <= var19.getMaxStackSize()) {
                                        var19.stackSize += var21;
                                        var17 = var16.decrStackSize(var21);
                                        if (var17.stackSize == 0) {
                                            var16.putStack((ItemStack)null);
                                        }

                                        var16.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
                                    }
                                } else if (var19 != null && !var16.isItemValid(var19)) {
                                    this.slot_that_did_not_accept_item = var16;
                                }
                            }

                            var16.onSlotChanged();
                            var16.onSlotClicked(par4EntityPlayer, par2, ReflectHelper.dyCast(Container.class, this));
                        }
                    }
                } else if (par3 == 2 && par2 >= 0 && par2 < 9) {
                    var16 = (Slot)this.inventorySlots.get(par1);
                    if (var16.canTakeStack(par4EntityPlayer)) {
                        var17 = var6.getStackInSlot(par2);
                        boolean var18 = var17 == null || var16.inventory == var6 && var16.isItemValid(var17);
                        var21 = -1;
                        if (!var18) {
                            var21 = var6.getFirstEmptyStack();
                            var18 |= var21 > -1;
                        }

                        if (var16.getHasStack() && var18) {
                            var23 = var16.getStack();
                            var6.setInventorySlotContents(par2, var23.copy());
                            if ((var16.inventory != var6 || !var16.isItemValid(var17)) && var17 != null) {
                                if (var21 > -1) {
                                    var6.addItemStackToInventory(var17);
                                    var16.decrStackSize(var23.stackSize);
                                    var16.putStack((ItemStack)null);
                                    var16.onPickupFromSlot(par4EntityPlayer, var23);
                                }
                            } else {
                                var16.decrStackSize(var23.stackSize);
                                var16.putStack(var17);
                                var16.onPickupFromSlot(par4EntityPlayer, var23);
                            }
                        } else if (!var16.getHasStack() && var17 != null && var16.isItemValid(var17)) {
                            var6.setInventorySlotContents(par2, (ItemStack)null);
                            var16.putStack(var17);
                        }
                    }
                } else if (par3 == 3 && par4EntityPlayer.capabilities.isCreativeMode && var6.getItemStack() == null && par1 >= 0) {
                    var16 = (Slot)this.inventorySlots.get(par1);
                    if (var16 != null && var16.getHasStack()) {
                        var17 = var16.getStack().copy();
                        var17.stackSize = var17.getMaxStackSize();
                        var6.setItemStack(var17);
                    }
                } else if (par3 == 4 && var6.getItemStack() == null && par1 >= 0) {
                    var16 = (Slot)this.inventorySlots.get(par1);
                    if (var16 != null && var16.getHasStack() && var16.canTakeStack(par4EntityPlayer)) {
                        var17 = var16.decrStackSize(par2 == 0 ? 1 : var16.getStack().stackSize);
                        var16.onPickupFromSlot(par4EntityPlayer, var17);
                        par4EntityPlayer.dropPlayerItem(var17);
                    }
                } else if (par3 == 6 && par1 >= 0) {
                    var16 = (Slot)this.inventorySlots.get(par1);
                    var17 = var6.getItemStack();
                    if (var17 != null && (var16 == null || !var16.getHasStack() || !var16.canTakeStack(par4EntityPlayer))) {
                        var9 = par2 == 0 ? 0 : this.inventorySlots.size() - 1;
                        var21 = par2 == 0 ? 1 : -1;

                        for(int var20 = 0; var20 < 2; ++var20) {
                            for(var22 = var9; var22 >= 0 && var22 < this.inventorySlots.size() && var17.stackSize < var17.getMaxStackSize(); var22 += var21) {
                                Slot var24 = (Slot)this.inventorySlots.get(var22);
                                if (var24.getHasStack() && func_94527_a(var24, var17, true) && var24.canTakeStack(par4EntityPlayer) && this.func_94530_a(var17, var24) && (var20 != 0 || var24.getStack().stackSize != var24.getStack().getMaxStackSize())) {
                                    int var14 = Math.min(var17.getMaxStackSize() - var17.stackSize, var24.getStack().stackSize);
                                    ItemStack var15 = var24.decrStackSize(var14);
                                    var17.stackSize += var14;
                                    if (var15.stackSize <= 0) {
                                        var24.putStack((ItemStack)null);
                                    }

                                    var24.onPickupFromSlot(par4EntityPlayer, var15);
                                }
                            }
                        }
                    }

                    this.detectAndSendChanges();
                }
            }

            return var5;
        }
    }
}
