package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Mixin(awy.class)
public abstract class PlayerInventoryGUITrans extends awe {
    @Shadow
    protected static bgw b = new bgw();
    @Shadow
    public Container e;
    @Shadow
    protected int p;
    @Shadow
    protected int q;
    @Shadow
    private Slot t;
    @Shadow
    private Slot u;
    @Shadow
    private boolean v;
    @Shadow
    private ItemStack w;
    @Shadow
    private int x;
    @Shadow
    private int y;
    @Shadow
    private Slot z;
    @Shadow
    private long A;
    @Shadow
    private ItemStack B;
    @Shadow
    private Slot C;
    @Shadow
    private long D;
    @Shadow
    protected final Set r = new HashSet();
    @Shadow
    protected boolean s;
    @Shadow
    private int E;
    @Shadow
    private int F;
    @Shadow
    private boolean G;
    @Shadow
    private int H;
    @Shadow
    private long I;
    @Shadow
    private Slot J;
    @Shadow
    private int K;
    @Shadow
    private boolean L;
    @Shadow
    private ItemStack M;
    @Shadow
    protected abstract void a(float var1, int var2, int var3);
    @Shadow
    protected void b(int par1, int par2) {
    }

    @Shadow
    public boolean a(Slot par1Slot, int par2, int par3) {
        return false;
    };
    @Shadow
    private void a(ItemStack par1ItemStack, int par2, int par3, String par4Str) {};

    @Shadow
    protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3, Slot slot) {};

    @Shadow
    private void g() {}

    @Overwrite
    private void a(Slot par1Slot) {
        int var2 = par1Slot.xDisplayPosition;
        int var3 = par1Slot.yDisplayPosition;
//        this.f.h.addChatMessage("x:" + var2 + "y:" + var3);
        ItemStack var4 = par1Slot.getStack();
        boolean var5 = false;
        boolean var6 = par1Slot == this.u && this.w != null && !this.v;
        ItemStack var7 = this.f.h.inventory.getItemStack();
        String var8 = null;
        if (par1Slot == this.u && this.w != null && this.v && var4 != null) {
            var4 = var4.copy();
            var4.stackSize /= 2;
        } else if (this.s && this.r.contains(par1Slot) && var7 != null) {
            if (this.r.size() == 1) {
                return;
            }

            if (Container.func_94527_a(par1Slot, var7, true) && this.e.canDragIntoSlot(par1Slot)) {
                var4 = var7.copy();
                var5 = true;
                Container.func_94525_a(this.r, this.E, var4, par1Slot.getStack() == null ? 0 : par1Slot.getStack().stackSize);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var8 = EnumChatFormat.YELLOW + "" + var4.getMaxStackSize();
                    var4.stackSize = var4.getMaxStackSize();
                }

                if (var4.stackSize > par1Slot.getSlotStackLimit()) {
                    var8 = EnumChatFormat.YELLOW + "" + par1Slot.getSlotStackLimit();
                    var4.stackSize = par1Slot.getSlotStackLimit();
                }
            } else {
                this.r.remove(par1Slot);
                this.g();
            }
        }

        this.n = 100.0F;
        b.f = 100.0F;
        if (var4 == null) {
            IIcon var9 = par1Slot.c();
            if (var9 != null) {
                GL11.glDisable(2896);
                this.f.J().a(bik.c);
                this.a(var2, var3, var9, 16, 16);
                GL11.glEnable(2896);
                var6 = true;
            }
        }

        if (!var6) {
            if (var5) {
                a(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }

            GL11.glEnable(2929);
            b.b(this.o, this.f.J(), var4, var2, var3);
            b.a(this.o, this.f.J(), var4, var2, var3, var8);
        }

        b.f = 0.0F;
        this.n = 0.0F;
    }

    @Overwrite
    protected void func_102021_a(List par1List, int par2, int par3, boolean has_title) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(32826);
            att.a();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int var4 = 0;
            Iterator var5 = par1List.iterator();

            int var15;
            while(var5.hasNext()) {
                String var6 = (String)var5.next();
                var15 = this.o.a(var6);
                if (var15 > var4) {
                    var4 = var15;
                }
            }

            int var14 = par2 + 12;
            var15 = par3 - 12;
            int var8 = 8;
            if (par1List.size() > 1) {
                var8 += 2 + (par1List.size() - 1) * 10;
            }

            if (!has_title) {
                var8 -= 2;
            }

            if (var14 + var4 > this.g) {
                var14 -= 28 + var4;
            }

            if (var15 + var8 + 6 > this.h) {
                var15 = this.h - var8 - 6;
            }

            this.n = 300.0F;
            b.f = 300.0F;
            int var9 = -267386864;
            var9 = var9 & 16777215 | -369098752;
            this.a(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
            this.a(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
            this.a(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
            this.a(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
            this.a(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
            int var10 = 1347420415;
            int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
            this.a(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
            this.a(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
            this.a(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
            this.a(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);

            for(int var12 = 0; var12 < par1List.size(); ++var12) {
                String var13 = (String)par1List.get(var12);
                this.o.a(var13, var14, var15, -1);
                if (var12 == 0 && has_title) {
                    var15 += 2;
                }

                var15 += 10;
            }

            this.n = 0.0F;
            b.f = 0.0F;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            att.b();
            GL11.glEnable(32826);
        }

    }

    @Overwrite
    public void a(int par1, int par2, float par3) {
        this.e();
        int var4 = this.p;
        int var5 = this.q;
        this.a(par3, par1, par2);
        GL11.glDisable(32826);
        att.a();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        super.a(par1, par2, par3);
        att.c();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        this.t = null;
        short var6 = 240;
        short var7 = 240;
        bma.a(bma.b, (float)var6 / 1.0F, (float)var7 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int var9;
//        this.f.h.addChatMessage("Class:" + this.e.toString());
        for(int var13 = 0; var13 < this.e.inventorySlots.size(); ++var13) {
            Slot var14 = (Slot)this.e.inventorySlots.get(var13);
            this.a(var14);
            if (this.a(var14, par1, par2) && var14.b()) {
                this.t = var14;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                int var8 = var14.xDisplayPosition;
                var9 = var14.yDisplayPosition;
//                this.f.h.addChatMessage("X:" +var8+" Y:" +var9);
                this.a(var8, var9, var8 + 16, var9 + 16, -2130706433, -2130706433);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
        }

        boolean lighting_enabled = GL11.glGetBoolean(2896);
        if (lighting_enabled) {
            GL11.glDisable(2896);
        }

        this.b(par1, par2);
        if (lighting_enabled) {
            GL11.glEnable(2896);
        }

        PlayerInventory var15 = this.f.h.inventory;
        ItemStack var16 = this.w == null ? var15.getItemStack() : this.w;
        if (var16 != null) {
//            this.f.h.addChatMessage(var16.toString());
            byte var18 = 8;
            var9 = this.w == null ? 8 : 16;
            String var10 = null;
            if (this.w != null && this.v) {
                var16 = var16.copy();
                var16.stackSize = MathHelper.ceiling_float_int((float)var16.stackSize / 2.0F);
            } else if (this.s && this.r.size() > 1) {
                var16 = var16.copy();
                var16.stackSize = this.H;
                if (var16.stackSize == 0) {
                    var10 = "" + EnumChatFormat.YELLOW + "0";
                }
            }

            this.a(var16, par1 - var4 - var18, par2 - var5 - var9, var10);
        }

        if (this.B != null) {
            float var17 = (float)(Minecraft.F() - this.A) / 100.0F;
            if (var17 >= 1.0F) {
                var17 = 1.0F;
                this.B = null;
            }

            var9 = this.z.xDisplayPosition - this.x;
            int var20 = this.z.yDisplayPosition - this.y;
            int var11 = this.x + (int)((float)var9 * var17);
            int var12 = this.y + (int)((float)var20 * var17);
            this.a(this.B, var11, var12, (String)null);
        }

        GL11.glPopMatrix();
        if (this.t != null && this.t.getHasStack() && (var15.getItemStack() == null || this.t instanceof SlotResult)) {
            ItemStack var19 = this.t.getStack();
            this.drawItemStackTooltip(var19, par1, par2, this.t);
        }

        GL11.glEnable(2896);
        GL11.glEnable(2929);
        att.b();
    }
}
