package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBed.class)
public class BlockBedTrans extends Block {
    public int sub;
    @Shadow
    private IIcon[] b;
    @Shadow
    private IIcon[] c;
    @Shadow
    private IIcon[] d;


    protected BlockBedTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    public BlockBed setSub(int sub) {
        this.sub = sub;
        return ReflectHelper.dyCast(BlockBed.class, this);
    }
    public BlockBed setAttr(float hardness, float cushioning, String unlocalizedName, String textureName) {
        this.setHardness(hardness).setCushioning(cushioning).setUnlocalizedName(unlocalizedName);
        this.disableStats();
        this.setTextureName(textureName);
        return ReflectHelper.dyCast(BlockBed.class, this);
    }

    @Overwrite
    public void a(mt par1IconRegister) {
        this.d = new IIcon[]{par1IconRegister.a("bed/" + this.E() + "/bed_feet_top"), par1IconRegister.a("bed/"+this.E() + "/bed_head_top")};
        this.b = new IIcon[]{par1IconRegister.a("bed/"+this.E() + "/bed_feet_end"), par1IconRegister.a("bed/bed_head_end")};
        this.c = new IIcon[]{par1IconRegister.a("bed/"+this.E() + "/bed_feet_side"), par1IconRegister.a("bed/"+this.E() + "/bed_head_side")};
    }

    @Shadow
    public static boolean isBlockHeadOfBed(int par0) {
        return (par0 & 8) != 0;
    }

    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        if (isBlockHeadOfBed(info.getMetadata())) {
            return 0;
        } else {
            return info.wasExploded() ? this.dropBlockAsEntityItem(info, Item.stick.itemID, 0, 1, 1.5F) | this.dropBlockAsEntityItem(info, Item.silk.itemID, 0, 1, 1.5F) : this.dropBlockAsEntityItem(info, new ItemStack(Item.bed,1, this.sub));
        }
    }
}
