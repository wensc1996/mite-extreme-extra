package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntitySkeletonBoss;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockMobSpawner.class)
public class BlockMobSpawnerTrans extends BlockContainer {
    private boolean HasSpawnZombieBoss = false;
    protected BlockMobSpawnerTrans(int par1, Material par2Material, BlockConstants block_constants) {
        super(par1, par2Material, block_constants);
    }

    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        this.dropXpOnBlockBreak(info.world, info.x, info.y, info.z, 15 + info.world.rand.nextInt(15) + info.world.rand.nextInt(15));
        if(info.world.isUnderworld() && info.world.rand.nextFloat() < Configs.wenscConfig.zombieBossSpawnPercent.ConfigValue) {
            EntityZombieBoss entityZombieBoss = new EntityZombieBoss(info.world);
            entityZombieBoss.setPosition(info.x, info.y, info.z);
            entityZombieBoss.refreshDespawnCounter(-9600);
            if(info.getResponsiblePlayer() != null) {
                entityZombieBoss.setAttackTarget(info.getResponsiblePlayer());
            }
            entityZombieBoss.entityFX(EnumEntityFX.summoned);
            entityZombieBoss.onSpawnWithEgg(null);
            info.world.spawnEntityInWorld(entityZombieBoss);
            HasSpawnZombieBoss = true;
        }

        if(info.world.isUnderworld() && info.world.rand.nextFloat() < Configs.wenscConfig.skeletonBossSpawnPercent.ConfigValue && (HasSpawnZombieBoss == Configs.wenscConfig.isSkeletonandZombieSpawnBoth.ConfigValue)) {
            EntitySkeletonBoss entitySkeletonBoss = new EntitySkeletonBoss(info.world);
            entitySkeletonBoss.setPosition(info.x, info.y, info.z);
            entitySkeletonBoss.refreshDespawnCounter(-9600);
            if(info.getResponsiblePlayer() != null) {
                entitySkeletonBoss.setAttackTarget(info.getResponsiblePlayer());
            }
            entitySkeletonBoss.entityFX(EnumEntityFX.summoned);
            entitySkeletonBoss.onSpawnWithEgg(null);
            info.world.spawnEntityInWorld(entitySkeletonBoss);
        }

        return 0;
    }

    @Shadow
    public TileEntity createNewTileEntity(World world) {
        return null;
    }
}
