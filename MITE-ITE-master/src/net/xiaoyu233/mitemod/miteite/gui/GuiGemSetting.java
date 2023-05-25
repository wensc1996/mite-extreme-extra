package net.xiaoyu233.mitemod.miteite.gui;

import com.sun.imageio.plugins.common.I18N;
import net.minecraft.EntityPlayer;
import net.minecraft.awy;
import net.minecraft.bjo;
import net.minecraft.bkb;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerGemSetting;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;
import org.lwjgl.opengl.GL11;

public class GuiGemSetting extends awy {
    private static final bjo furnaceGuiTextures = new bjo("textures/gui/container/gem_setting_table.png");
    private TileEntityGemSetting gemSettingInventory;

    public GuiGemSetting(EntityPlayer player, TileEntityGemSetting tileEntityGemSetting)
    {
        super(new ContainerGemSetting(player, tileEntityGemSetting));
        this.gemSettingInventory = tileEntityGemSetting;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    public void b(int par1, int par2)
    {
        String var3 = this.gemSettingInventory.hasCustomName() ? this.gemSettingInventory.getCustomNameOrUnlocalized() : bkb.a(this.gemSettingInventory.getCustomNameOrUnlocalized());
        this.o.b(var3, this.c / 2 - this.o.a(var3) / 2, 6, 4210752);
        this.o.b(bkb.a("container.inventory"), 8, this.d - 96 + 2, 4210752);

    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    public void a(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.f.J().a(furnaceGuiTextures);
        int var4 = (this.g - this.c) / 2;
        int var5 = (this.h - this.d) / 2;
        this.b(var4, var5, 0, 0, this.c, this.d);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void a(int mouse_x, int mouse_y, float par3) {
        super.a(mouse_x, mouse_y, par3);

//        if (this.inventorySlots.slot_that_did_not_accept_item != null)
//        {
//            ItemStack mouse_item_stack = this.mc.thePlayer.inventory.getItemStack();
//
//            if (mouse_item_stack != null)
//            {
//                Slot slot = this.getSlotThatMouseIsOver(mouse_x, mouse_y);
//
//                if (slot == null)
//                {
//                    this.inventorySlots.slot_that_did_not_accept_item = null;
//                }
//                else if (slot != null && !slot.isItemValid(mouse_item_stack))
//                {
//                    if (!slot.accepts_large_items && Slot.isLargeItem(mouse_item_stack.getItem()))
//                    {
//                        this.drawCreativeTabHoveringText(EnumChatFormatting.GOLD + Translator.get("container.furnace.wontFit"), mouse_x, mouse_y);
//                    }
//                    else
//                    {
//                        if (slot == this.inventorySlots.getSlot(0))
//                        {
//                            if (!FurnaceRecipes.smelting().doesSmeltingRecipeExistFor(mouse_item_stack))
//                            {
//                                this.drawCreativeTabHoveringText(EnumChatFormatting.GOLD + Translator.get("container.furnace.cantSmelt"), mouse_x, mouse_y);
//                                return;
//                            }
//                        }
//                        else if (slot == this.inventorySlots.getSlot(1))
//                        {
//
//                        }
//                    }
//                }
//            }
//        }
    }
}
