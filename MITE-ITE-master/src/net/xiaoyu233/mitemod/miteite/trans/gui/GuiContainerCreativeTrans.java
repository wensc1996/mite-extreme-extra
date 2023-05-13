package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.lwjgl.input.Keyboard;

import java.util.List;

@Mixin(axm.class)
public class GuiContainerCreativeTrans extends axp {

    @Shadow
    private static int v;
    @Shadow
    private avf z;
    @Shadow
    private axl D;

    public GuiContainerCreativeTrans(Container container) {
        super(container);
    }

    @Shadow
    protected void a(float par1, int par2, int par3){}

    @Overwrite
    public void A_() {
        super.A_();
        this.i.clear();
        Keyboard.enableRepeatEvents(true);
        this.z = new avf(this.o, this.p + 82, this.q + 6, 89, this.o.a);
        this.z.f(15);
        this.z.a(false);
        this.z.e(false);
        this.z.g(16777215);
        int var1 = v;
        v = -1;
        this.b(CreativeModeTab.creativeTabArray[var1]);
        this.D = new axl(this.f);
        this.f.h.inventoryContainer.onCraftGuiOpened(this.D);
    }

    @Overwrite
    public void c() {
        if (!this.f.c.h()) {
//            this.f.a(new axv(this.f.h));
        }
    }

    @Shadow
    private void b(CreativeModeTab par1CreativeTabs){}

}
