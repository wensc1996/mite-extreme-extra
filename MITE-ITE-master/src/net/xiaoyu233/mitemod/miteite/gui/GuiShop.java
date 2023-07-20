package net.xiaoyu233.mitemod.miteite.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerShop;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.URI;

import static net.xiaoyu233.mitemod.miteite.inventory.container.InventoryShop.pageSize;

public class GuiShop extends awy {
    private GUIPaginationButton v;
    private GUIPaginationButton w;
    private int pageIndex = 0;
    private static final bjo furnaceGuiTextures = new bjo("textures/gui/container/gui_shop.png");

    public GuiShop(EntityPlayer player)
    {
        super(new ContainerShop(player));
        this.c = 176;
        this.d = 209;
    }

    @Override
    public void A_() {
        super.A_();
        this.i.clear();
        this.i.add(this.v = new GUIPaginationButton(1, this.g / 2 - 82, this.h / 2 -9, false));
        this.i.add(this.w = new GUIPaginationButton(2, this.g / 2 + 68, this.h / 2-9, true));
        this.v.h = false;

        ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        DataOutputStream var4 = new DataOutputStream(var3);
        try {
            var4.writeInt(this.pageIndex);
            this.f.q().c(new Packet250CustomPayload("MC|ShopPageIndex", var3.toByteArray()));
        } catch (Exception var6) {
            var6.printStackTrace();
        }
//        this.w.h = false;
    }

    protected void a(aut var1) {
        ContainerShop e1 = (ContainerShop) this.e;
        switch (var1.g) {
            case 1:
                this.pageIndex--;
                if(this.pageIndex == 0) {
                    var1.h = false;
                }
                this.w.h = true;
//                e1.inventory.initItemList();
                break;
            case 2:
                this.v.h = true;
                if((this.pageIndex + 1) * pageSize > Items.priceStackList.size()) {
                    var1.h = false;
                } else {
                    this.pageIndex++;
//                    e1.inventory.initItemList();
                }

//                this.f.a((awe)null);
//                this.f.g();
                break;
        }
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        DataOutputStream var4 = new DataOutputStream(var3);


        try {
            var4.writeInt(this.pageIndex);
            this.f.q().c(new Packet250CustomPayload("MC|ShopPageIndex", var3.toByteArray()));
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    public void b(int par1, int par2)
    {
        String var3 = "shop";
        this.o.b(var3, this.c / 2 - this.o.a(var3) / 2, 6, 4210752);
        this.o.b(bkb.a("container.inventory"), 8, this.d - 96 + 2, 4210752);
        this.o.b(bkb.a("金额" + this.e.player.money), 80, this.d - 110, 4210752);
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
    }
}
