package net.akarisakai.fantasyisekaimod.client.custom;

import net.akarisakai.fantasyisekaimod.entity.DirewolfLeapAtTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class DirewolfEntity extends Monster {

    public DirewolfEntity(EntityType<? extends DirewolfEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final net.minecraft.world.entity.AnimationState idleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState runAnimationstate = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState liedownAnimationstate = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState sleepAnimationstate = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState standupAnimationstate = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState attackAnimationstate = new net.minecraft.world.entity.AnimationState();


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ATTACK_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.FOLLOW_RANGE, 27.0);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(5, new DirewolfLeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));
        this.goalSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));

    }


    @Override
    public void tick() {
        super.tick();

        // Check if there's a target
        LivingEntity target = getTarget();

        // Set the animation state to DIREWOLF_RUN when it's aggressive, or idle otherwise
        runAnimationstate.animateWhen(isAggressive(), this.tickCount);
        idleAnimationState.animateWhen(target == null && !isAggressive(), this.tickCount);
        attackAnimationstate.animateWhen(this.swinging, this.tickCount);

        // Check if it's nighttime


        if (isAggressive()) {
            // Enable chasing behavior and set a higher movement speed when aggressive
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45); // Adjust the speed as needed
            if (target != null) {
                getNavigation().moveTo(target, 1.0); // Adjust the speed modifier as needed
            }
        } else {
            // Disable chasing behavior and restore the default speed when not aggressive
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.20); // Restore the default speed
        }
    }



    public static boolean canSpawn(EntityType<DirewolfEntity> entityType, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
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
}

