package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.ReferenceFileWriter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ReferenceFileWriter.class)
public class ReferenceFileWriterTrans {
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
}
