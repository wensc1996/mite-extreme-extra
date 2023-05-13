package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mixin(BlockStrongbox.class)
public class BlockStrongboxTrans extends Block {
    protected BlockStrongboxTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Inject(method = "a",at = @At("RETURN"))
    private void injectTextureInit(mt register, CallbackInfo callbackInfo){
        if (this.blockMaterial == Materials.vibranium) {
            this.cW = register.a("block_vibranium");
        }
    }

    public void onBlockAboutToBeBroken(BlockBreakInfo info) {
        EntityPlayer player = info.getResponsiblePlayer();
        if (player != null && !player.inCreativeMode() && info.world.isWorldServer() && !((TileEntityStrongbox)info.tile_entity).isOwner(player)) {
            String POS = "(" + (int)player.posX + "," + (int)player.posY + "," + (int)player.posZ + ")";
            String World = player.getWorld().getDimensionName();
            info.world.getAsWorldServer().p().getLogAgent_().logWarning("Player " + player.getEntityName() + " tried to break personal chest of " + ((TileEntityStrongbox)info.tile_entity).owner_name);
            info.world.getAsWorldServer().p().getConfigurationManager().sendChatMsg(ChatMessage.createFromTranslationKey("[Server]:").appendComponent(ChatMessage.createFromTranslationKey(" " + player.getEntityName() + " 破坏了 " + ((TileEntityStrongbox)info.tile_entity).owner_name + " 的金属箱子→" + World + POS).setColor(EnumChatFormat.DARK_RED)));
            if (Configs.wenscConfig.isOpenStrongBoxBreakRecord.ConfigValue) {
                try {
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String day = player.getWorld().getDayOfWorld() + "天 ";
                    File file = new File("StrongBox.txt");
                    FileWriter fileWriter = new FileWriter(file.getName(), true);
                    fileWriter.write("=====================" + System.getProperty("line.separator"));
                    fileWriter.write("时间=" + day + formatter.format(date) + System.getProperty("line.separator"));
                    fileWriter.write("坐标=" + World + POS + System.getProperty("line.separator"));
                    fileWriter.write("破坏者=" + player.getEntityName() + System.getProperty("line.separator"));
                    fileWriter.write("所有者=" + ((TileEntityStrongbox)info.tile_entity).owner_name + System.getProperty("line.separator"));
                    fileWriter.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }
            }
        }

        super.onBlockAboutToBeBroken(info);
    }
}
