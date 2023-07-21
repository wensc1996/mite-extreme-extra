package net.xiaoyu233.mitemod.miteite.gui;

import net.minecraft.Minecraft;
import net.minecraft.aut;
import net.minecraft.axw;
import net.minecraft.bjo;
import org.lwjgl.opengl.GL11;

public class GUIPaginationButton extends aut {
    private static final bjo t = new bjo("textures/gui/container/gui_shop.png");
    private final boolean o;

    public GUIPaginationButton(int var1, int var2, int var3, boolean var4) {
        super(var1, var2, var3, 12, 19, "");
        this.o = var4;
    }

    public void a(Minecraft var1, int var2, int var3) {
        if (this.i) {
            var1.J().a(t);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var4 = var2 >= this.d && var3 >= this.e && var2 < this.d + this.b && var3 < this.e + this.c;
            int var5 = 0;
            int var6 = 176;
            if (!this.h) {
                var6 += this.b * 2;
            } else if (var4) {
                var6 += this.b;
            }

            if (!this.o) {
                var5 += this.c;
            }

            this.b(this.d, this.e, var6, var5, this.b, this.c);
        }
    }
}
