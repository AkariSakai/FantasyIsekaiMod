package net.akarisakai.fantasyisekaimod.client.custom;

import net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin;
import net.akarisakai.fantasyisekaimod.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.List;

public class GoblinShamanEntity extends SpellcasterGoblin {


    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private Sheep wololoTarget;

    public GoblinShamanEntity(EntityType<? extends SpellcasterGoblin> entityType, Level world) {
        super((EntityType<GoblinShamanEntity>) entityType, world);
    }
    public GoblinShamanEntity(Level level, double x, double y, double z) {
        this(EntityInit.GOBLINSHAMAN_ENTITY.get(), level);
        setPos(x, y, z);
    }
    public GoblinShamanEntity(Level level, BlockPos position) {
        this(level, position.getX(), position.getY(), position.getZ());

    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GoblinShamanEntity.GoblinCastingSpellGoal());

        this.goalSelector.addGoal(4, new GoblinShamanEntity.GoblinSummonSpellGoal());
        this.goalSelector.addGoal(5, new GoblinShamanEntity.GoblinAttackSpellGoal());
        this.goalSelector.addGoal(6, new GoblinShamanEntity.GoblinWololoSpellGoal());
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Zombie.class, 8.0f, 1.2, 1.5));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Skeleton.class, 8.0f, 1.2, 1.5));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Creeper.class, 8.0f, 1.2, 1.5));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6, 1.0));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));

    }

    @Override
    public void tick() {
        if(level().isClientSide()) {
            this.idleAnimationState.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
            this.attackAnimationState.animateWhen(isCastingSpell(), this.tickCount);

        }
        super.tick();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 12.0)
                .add(Attributes.MAX_HEALTH, 12.0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public boolean isAlliedTo(Entity pEntity) {
        if (pEntity == null) {
            return false;
        } else if (pEntity == this) {
            return true;
        } else if (super.isAlliedTo(pEntity)) {
            return true;
        } else if (pEntity instanceof Vex) {
            return this.isAlliedTo(((Vex)pEntity).getOwner());
        } else {
            return false;
        }
    }


    void setWololoTarget(@Nullable Sheep pWololoTarget) {
        this.wololoTarget = pWololoTarget;
    }

    @Nullable
    Sheep getWololoTarget() {
        return this.wololoTarget;
    }

    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }



    public static boolean canSpawn(EntityType<GoblinShamanEntity> entityType, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        if (spawnType != MobSpawnType.NATURAL && spawnType != MobSpawnType.CHUNK_GENERATION) {
            // Only allow natural and chunk generation spawns
            return true;
        }

        if (levelAccessor instanceof ServerLevel serverWorld) {
            // Only proceed if it's a server world
            if (!serverWorld.isDay() || serverWorld.getDifficulty() == Difficulty.PEACEFUL) {
                // Don't spawn at night or in peaceful mode
                return false;
            }

            BlockPos surfacePos = serverWorld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
            return surfacePos.getY() >= 60 && surfacePos.getY() <= 100;
        }

        return false;
    }

    class GoblinCastingSpellGoal extends SpellcasterGoblin.SpellcasterCastingSpellGoal {
        GoblinCastingSpellGoal() {
            super();
        }

        public void tick() {
            if (GoblinShamanEntity.this.getTarget() != null) {
                GoblinShamanEntity.this.getLookControl().setLookAt(GoblinShamanEntity.this.getTarget(), (float)GoblinShamanEntity.this.getMaxHeadYRot(), (float)GoblinShamanEntity.this.getMaxHeadXRot());
            } else if (GoblinShamanEntity.this.getWololoTarget() != null) {
                GoblinShamanEntity.this.getLookControl().setLookAt(GoblinShamanEntity.this.getWololoTarget(), (float)GoblinShamanEntity.this.getMaxHeadYRot(), (float)GoblinShamanEntity.this.getMaxHeadXRot());
            }

        }
    }

    class GoblinSummonSpellGoal extends SpellcasterGoblin.SpellcasterUseSpellGoal {
        private final TargetingConditions vexCountTargeting = TargetingConditions.forNonCombat().range(16.0).ignoreLineOfSight().ignoreInvisibilityTesting();

        GoblinSummonSpellGoal() {
            super();
        }

        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                int i = GoblinShamanEntity.this.level().getNearbyEntities(Vex.class, this.vexCountTargeting, GoblinShamanEntity.this, GoblinShamanEntity.this.getBoundingBox().inflate(16.0)).size();
                return GoblinShamanEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return 340;
        }

        protected void performSpellCasting() {
            ServerLevel serverlevel = (ServerLevel)GoblinShamanEntity.this.level();

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos = GoblinShamanEntity.this.blockPosition().offset(-2 + GoblinShamanEntity.this.random.nextInt(5), 1, -2 + GoblinShamanEntity.this.random.nextInt(5));
                Vex vex = (Vex)EntityType.VEX.create(GoblinShamanEntity.this.level());
                if (vex != null) {
                    vex.moveTo(blockpos, 0.0F, 0.0F);
                    vex.finalizeSpawn(serverlevel, GoblinShamanEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    vex.setOwner(GoblinShamanEntity.this);
                    vex.setBoundOrigin(blockpos);
                    vex.setLimitedLife(20 * (30 + GoblinShamanEntity.this.random.nextInt(90)));
                    serverlevel.addFreshEntityWithPassengers(vex);
                }
            }

        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        protected SpellcasterGoblin.IllagerSpell getSpell() {
            return IllagerSpell.SUMMON_VEX;
        }
    }

    class GoblinAttackSpellGoal extends SpellcasterGoblin.SpellcasterUseSpellGoal {
        GoblinAttackSpellGoal() {
            super();
        }

        protected int getCastingTime() {
            return 40;
        }

        protected int getCastingInterval() {
            return 100;
        }

        protected void performSpellCasting() {
            LivingEntity livingentity = GoblinShamanEntity.this.getTarget();
            double d0 = Math.min(livingentity.getY(), GoblinShamanEntity.this.getY());
            double d1 = Math.max(livingentity.getY(), GoblinShamanEntity.this.getY()) + 1.0;
            float f = (float) Mth.atan2(livingentity.getZ() - GoblinShamanEntity.this.getZ(), livingentity.getX() - GoblinShamanEntity.this.getX());
            int k;
            if (GoblinShamanEntity.this.distanceToSqr(livingentity) < 9.0) {
                float f2;
                for(k = 0; k < 5; ++k) {
                    f2 = f + (float)k * 3.1415927F * 0.4F;
                    this.createSpellEntity(GoblinShamanEntity.this.getX() + (double)Mth.cos(f2) * 1.5, GoblinShamanEntity.this.getZ() + (double)Mth.sin(f2) * 1.5, d0, d1, f2, 0);
                }

                for(k = 0; k < 8; ++k) {
                    f2 = f + (float)k * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                    this.createSpellEntity(GoblinShamanEntity.this.getX() + (double)Mth.cos(f2) * 2.5, GoblinShamanEntity.this.getZ() + (double)Mth.sin(f2) * 2.5, d0, d1, f2, 3);
                }
            } else {
                for(k = 0; k < 16; ++k) {
                    double d2 = 1.25 * (double)(k + 1);
                    int j = 1 * k;
                    this.createSpellEntity(GoblinShamanEntity.this.getX() + (double)Mth.cos(f) * d2, GoblinShamanEntity.this.getZ() + (double)Mth.sin(f) * d2, d0, d1, f, j);
                }
            }

        }

        private void createSpellEntity(double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay) {
            BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
            boolean flag = false;
            double d0 = 0.0;

            do {
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = GoblinShamanEntity.this.level().getBlockState(blockpos1);
                if (blockstate.isFaceSturdy(GoblinShamanEntity.this.level(), blockpos1, Direction.UP)) {
                    if (!GoblinShamanEntity.this.level().isEmptyBlock(blockpos)) {
                        BlockState blockstate1 = GoblinShamanEntity.this.level().getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(GoblinShamanEntity.this.level(), blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.max(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.below();
            } while(blockpos.getY() >= Mth.floor(pMinY) - 1);

            if (flag) {
                GoblinShamanEntity.this.level().addFreshEntity(new EvokerFangs(GoblinShamanEntity.this.level(), pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, GoblinShamanEntity.this));
            }

        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        protected SpellcasterGoblin.IllagerSpell getSpell() {
            return IllagerSpell.FANGS;
        }
    }

    public class GoblinWololoSpellGoal extends SpellcasterGoblin.SpellcasterUseSpellGoal {
        private final TargetingConditions wololoTargeting = TargetingConditions.forNonCombat().range(16.0).selector((p_32710_) -> {
            return ((Sheep)p_32710_).getColor() == DyeColor.BLUE;
        });

        public GoblinWololoSpellGoal() {
            super();
        }

        public boolean canUse() {
            if (GoblinShamanEntity.this.getTarget() != null) {
                return false;
            } else if (GoblinShamanEntity.this.isCastingSpell()) {
                return false;
            } else if (GoblinShamanEntity.this.tickCount < this.nextAttackTickCount) {
                return false;
            } else if (!ForgeEventFactory.getMobGriefingEvent(GoblinShamanEntity.this.level(), GoblinShamanEntity.this)) {
                return false;
            } else {
                List<Sheep> list = GoblinShamanEntity.this.level().getNearbyEntities(Sheep.class, this.wololoTargeting, GoblinShamanEntity.this, GoblinShamanEntity.this.getBoundingBox().inflate(16.0, 4.0, 16.0));
                if (list.isEmpty()) {
                    return false;
                } else {
                    GoblinShamanEntity.this.setWololoTarget(list.get(GoblinShamanEntity.this.random.nextInt(list.size())));
                    return true;
                }
            }
        }

        public boolean canContinueToUse() {
            return GoblinShamanEntity.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
        }

        public void stop() {
            super.stop();
            GoblinShamanEntity.this.setWololoTarget(null);
        }

        protected void performSpellCasting() {
            Sheep sheep = GoblinShamanEntity.this.getWololoTarget();
            if (sheep != null && sheep.isAlive()) {
                sheep.setColor(DyeColor.RED);
            }

        }

        protected int getCastWarmupTime() {
            return 40;
        }

        protected int getCastingTime() {
            return 60;
        }

        protected int getCastingInterval() {
            return 140;
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_WOLOLO;
        }

        protected SpellcasterGoblin.IllagerSpell getSpell() {
            return IllagerSpell.WOLOLO;
        }
    }
}
