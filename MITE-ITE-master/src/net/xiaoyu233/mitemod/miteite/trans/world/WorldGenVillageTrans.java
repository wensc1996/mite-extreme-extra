package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(WorldGenVillage.class)
public abstract class WorldGenVillageTrans extends StructureGenerator{
    @Shadow
    public static final List villageSpawnBiomes = Arrays.asList(BiomeBase.plains, BiomeBase.desert);
    @Shadow
    private int terrainType;
    @Shadow
    private int field_82665_g;
    @Shadow
    private int field_82666_h;

    public WorldGenVillageTrans() {
    }

    @Overwrite
    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        if (Minecraft.isInTournamentMode()) {
            return false;
        } else if (this.worldObj.getDayOfWorld() < Configs.wenscConfig.whichDayGenVillage.ConfigValue) {
            return false;
        } else {
            byte required_village_conditions = WorldData.getVillagePrerequisites();
            if (this.worldObj.getWorldInfo().getVillageConditions() < required_village_conditions) {
                return false;
            } else {
                int var3 = par1;
                int var4 = par2;
                if (par1 < 0) {
                    par1 -= this.field_82665_g - 1;
                }

                if (par2 < 0) {
                    par2 -= this.field_82665_g - 1;
                }

                int var5 = par1 / this.field_82665_g;
                int var6 = par2 / this.field_82665_g;
                Random var7 = new Random((long)var5 * 341873128712L + (long)var6 * 132897987541L + this.worldObj.getWorldInfo().getSeed() + 10387312L);
                var5 *= this.field_82665_g;
                var6 *= this.field_82665_g;
                var5 += var7.nextInt(this.field_82665_g - this.field_82666_h);
                var6 += var7.nextInt(this.field_82665_g - this.field_82666_h);
                if (var3 == var5 && var4 == var6) {
                    boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, villageSpawnBiomes);
                    if (var8) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}
