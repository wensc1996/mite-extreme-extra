package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.io.File;
import java.io.FileWriter;

@Mixin(ReferenceFileWriter.class)
public class ReferenceFileWriterMixin {
    @Overwrite
    private static void writeItemReachFile(File dir) throws Exception {
        FileWriter fw = new FileWriter(dir.getPath() + "/item_reach.txt");
        StringBuffer sb = new StringBuffer();
        sb.append("The player has a base reach of " + StringHelper.formatFloat(2.75F, 1, 2) + " vs blocks and " + StringHelper.formatFloat(1.5F, 1, 2) + " vs entities." + newline + newline);
        sb.append("Only items that have a reach bonus are listed." + newline + newline);
        sb.append("Reach Bonus" + newline);
        sb.append("-----------" + newline);

        for(int i = 0; i < Item.itemsList.length; ++i) {
            Item item = Item.getItem(i);
            if (item != null) {
                String name = item.getNameForReferenceFile();
                if (item instanceof ItemTool) {
                    ItemTool tool = (ItemTool)item;
                    if (tool.getToolMaterial() != Material.iron && !(tool instanceof ItemCudgel)) {
                        continue;
                    }

                    name = name.substring(tool.getToolMaterial() == Material.iron ? 1 : 2);
                }

                float reach_bonus = item.getReachBonus();
                if (reach_bonus > 0.0F) {
                    sb.append("Item[" + i + "] ");
                    sb.append(name + ": +" + StringHelper.formatFloat(reach_bonus, 1, 3));
                    sb.append(newline);
                }
            }
        }

        fw.write(sb.toString());
        fw.close();
    }

    @ModifyConstant(method = {
            "writeBlockMaterialFile",
            "writeBlockConstantsFile",
            "writeBlockHardnessFile",
            "writeBlockMetadataFile",
            "writeBlockDissolveTimeFile",
            "writeSilkHarvestFile",
            "writeHarvestLevelFile",
            "writeToolDecayRateFiles",
            "writeToolHarvestEfficiencyFiles",
            "writeBlockOpacityFile",
            "writeIsOpaqueStandardFormCubeFile",
            "writeNormalCubeFile",
            "writeBlockMetadataToSubtypeFile",
            "writeAllowsGrassBeneathFile",
            "writeUseNeighborBrightnessFile",
            "writeBlockRenderTypeFile"
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }

    @Shadow
    private static String newline;
}
