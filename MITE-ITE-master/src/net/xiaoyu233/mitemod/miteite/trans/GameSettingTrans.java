package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.ats;
import net.minecraft.aul;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(aul.class)
public class GameSettingTrans {
    @Shadow
    public ats[] W;
    @Shadow
    public ats I = new ats("key.forward", 17);
    @Shadow
    public ats J = new ats("key.left", 30);
    @Shadow
    public ats K = new ats("key.back", 31);
    @Shadow
    public ats L = new ats("key.right", 32);
    @Shadow
    public ats M = new ats("key.jump", 57);
    @Shadow
    public ats N = new ats("key.inventory", 18);
    @Shadow
    public ats O = new ats("key.drop", 16);
    @Shadow
    public ats P = new ats("key.chat", 20);
    @Shadow
    public ats Q = new ats("key.sneak", 42);
    @Shadow
    public ats R = new ats("key.attack", -100);
    @Shadow
    public ats S = new ats("key.use", -99);
    @Shadow
    public ats T = new ats("key.playerlist", 41);
    @Shadow
    public ats U = new ats("key.pickItem", -98);
    @Shadow
    public ats V = new ats("key.command", 53);
    @Shadow
    public ats keyBindToggleRun = new ats("key.toggleRun", 15);
    @Shadow
    public ats keyBindZoom = new ats("key.zoom", 44);
    @Shadow
    public ats keyBindRedrawChunks = new ats("key.redrawChunks", 19);

    @Overwrite
    public void initKeybindings() {
        this.W = new ats[]{this.R, this.S, this.I, this.J, this.K, this.L, this.M, this.Q, this.O, this.N, this.P, this.T, this.U, this.V, this.keyBindToggleRun, this.keyBindZoom, this.keyBindRedrawChunks, this.keyBindShop};
    }
    public ats keyBindShop = new ats("key.openShop", Keyboard.KEY_V);
}
