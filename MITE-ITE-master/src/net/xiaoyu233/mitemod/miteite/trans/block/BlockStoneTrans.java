//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(BlockStone.class)
public class BlockStoneTrans extends Block {

    public BlockStoneTrans(int par1) {
        super(par1, Material.stone, new BlockConstants());
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setMaxStackSize(64);
    }

    public Block setBlockHardness(float resistance) {
        this.setMaxStackSize(64);
        // super.a(resistance / 10.0F); 经验
        return null;
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return this.dropBlockAsEntityItem(info, cobblestone);
    }
}
