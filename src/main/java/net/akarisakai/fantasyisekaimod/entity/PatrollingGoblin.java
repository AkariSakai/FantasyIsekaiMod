package net.akarisakai.fantasyisekaimod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.EntityType;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class PatrollingGoblin extends Monster {
    @Nullable
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;

    protected PatrollingGoblin(EntityType<? extends PatrollingGoblin > pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new PatrollingGoblin.LongDistancePatrolGoal(this, 0.7, 0.595));
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.patrolTarget != null) {
            pCompound.put("PatrolTarget", NbtUtils.writeBlockPos(this.patrolTarget));
        }

        pCompound.putBoolean("PatrolLeader", this.patrolLeader);
        pCompound.putBoolean("Patrolling", this.patrolling);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("PatrolTarget")) {
            this.patrolTarget = NbtUtils.readBlockPos(pCompound.getCompound("PatrolTarget"));
        }

        this.patrolLeader = pCompound.getBoolean("PatrolLeader");
        this.patrolling = pCompound.getBoolean("Patrolling");
    }

    public double getMyRidingOffset() {
        return -0.45;
    }

    public boolean canBeLeader() {
        return true;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (pReason != MobSpawnType.PATROL && pReason != MobSpawnType.EVENT && pReason != MobSpawnType.STRUCTURE && pLevel.getRandom().nextFloat() < 0.06F && this.canBeLeader()) {
            this.patrolLeader = true;
        }

        if (this.isPatrolLeader()) {
            this.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
            this.setDropChance(EquipmentSlot.HEAD, 2.0F);
        }

        if (pReason == MobSpawnType.PATROL) {
            this.patrolling = true;
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public static boolean checkPatrollingGoblinSpawnRules(EntityType<? extends PatrollingGoblin> PatrollingGoblin, LevelAccessor
    pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBrightness(LightLayer.BLOCK, pPos) > 8 ? false : checkAnyLightMonsterSpawnRules(PatrollingGoblin, pLevel, pSpawnType, pPos, pRandom);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.patrolling || pDistanceToClosestPlayer > 16384.0;
    }

    public void setPatrolTarget(BlockPos pPatrolTarget) {
        this.patrolTarget = pPatrolTarget;
        this.patrolling = true;
    }

    public BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    public void setPatrolLeader(boolean pPatrolLeader) {
        this.patrolLeader = pPatrolLeader;
        this.patrolling = true;
    }

    public boolean isPatrolLeader() {
        return this.patrolLeader;
    }


    public void findPatrolTarget() {
        this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
        this.patrolling = true;
    }

    public boolean isPatrolling() {
        return this.patrolling;
    }

    protected void setPatrolling(boolean pPatrolling) {
        this.patrolling = pPatrolling;
    }

    public static class LongDistancePatrolGoal<T extends PatrollingGoblin> extends Goal {
        private static final int NAVIGATION_FAILED_COOLDOWN = 200;
        private final T mob;
        private final double speedModifier;
        private final double leaderSpeedModifier;
        private long cooldownUntil;

        public LongDistancePatrolGoal(T pMob, double pSpeedModifier, double pLeaderSpeedModifier) {
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.leaderSpeedModifier = pLeaderSpeedModifier;
            this.cooldownUntil = -1L;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            boolean $$0 = this.mob.level().getGameTime() < this.cooldownUntil;
            return this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.isVehicle() && this.mob.hasPatrolTarget() && !$$0;
        }

        public void start() {
        }

        public void stop() {
        }

        public void tick() {
            boolean $$0 = this.mob.isPatrolLeader();
            PathNavigation $$1 = this.mob.getNavigation();
            if ($$1.isDone()) {
                List<PatrollingMonster> $$2 = this.findPatrolCompanions();
                if (this.mob.isPatrolling() && $$2.isEmpty()) {
                    this.mob.setPatrolling(false);
                } else if ($$0 && this.mob.getPatrolTarget().closerToCenterThan(this.mob.position(), 10.0)) {
                    this.mob.findPatrolTarget();
                } else {
                    Vec3 $$3 = Vec3.atBottomCenterOf(this.mob.getPatrolTarget());
                    Vec3 $$4 = this.mob.position();
                    Vec3 $$5 = $$4.subtract($$3);
                    $$3 = $$5.yRot(90.0F).scale(0.4).add($$3);
                    Vec3 $$6 = $$3.subtract($$4).normalize().scale(10.0).add($$4);
                    BlockPos $$7 = BlockPos.containing($$6);
                    $$7 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$7);
                    if (!$$1.moveTo((double)$$7.getX(), (double)$$7.getY(), (double)$$7.getZ(), $$0 ? this.leaderSpeedModifier : this.speedModifier)) {
                        this.moveRandomly();
                        this.cooldownUntil = this.mob.level().getGameTime() + 200L;
                    } else if ($$0) {
                        Iterator var9 = $$2.iterator();

                        while(var9.hasNext()) {
                            PatrollingMonster $$8 = (PatrollingMonster)var9.next();
                            $$8.setPatrolTarget($$7);
                        }
                    }
                }
            }

        }

        private List<PatrollingMonster> findPatrolCompanions() {
            return this.mob.level().getEntitiesOfClass(PatrollingMonster.class, this.mob.getBoundingBox().inflate(16.0), (p_264971_) -> {
                return p_264971_.canJoinPatrol() && !p_264971_.is(this.mob);
            });
        }

        private boolean moveRandomly() {
            RandomSource $$0 = this.mob.getRandom();
            BlockPos $$1 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + $$0.nextInt(16), 0, -8 + $$0.nextInt(16)));
            return this.mob.getNavigation().moveTo((double)$$1.getX(), (double)$$1.getY(), (double)$$1.getZ(), this.speedModifier);
        }
    }
}