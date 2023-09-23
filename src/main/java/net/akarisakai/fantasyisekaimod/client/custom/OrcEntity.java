package net.akarisakai.fantasyisekaimod.client.custom;

import net.akarisakai.fantasyisekaimod.entity.OrcMeleeAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class OrcEntity extends Monster {
    public final net.minecraft.world.entity.AnimationState idleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState attackAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState walkAnimationState = new net.minecraft.world.entity.AnimationState();

    private int attackAnimTimer = 0;
    private boolean isAttackAnimationPlaying = false;


    public OrcEntity(EntityType<OrcEntity> orcEntityEntityType, Level level) {
        super(orcEntityEntityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new OrcMeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1.D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 150.0)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.ATTACK_SPEED, 0.3)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7 && this.random.nextInt(5) == 0;
    }


    @Override
    public void tick() {
        if (level().isClientSide()) {
            this.idleAnimationState.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
            if (attackAnim > 0) {
                if (!isAttackAnimationPlaying) {
                    // Start the attack animation
                    this.attackAnimationState.animateWhen(true, this.tickCount);
                    isAttackAnimationPlaying = true;
                    attackAnimTimer = 0;
                }
            } else if (isAttackAnimationPlaying) {
                attackAnimTimer++;
                if (attackAnimTimer > 30) {
                    this.attackAnimationState.stop();
                    isAttackAnimationPlaying = false;

                }
            }
        }

        super.tick();
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        float attackDamage = this.getAttackDamage();
        float knockbackStrength = 0.6F; // Adjust the vertical knockback strength as needed
        float horizontalKnockback = 1F; // Adjust the horizontal knockback strength as needed

        boolean success = entity.hurt(this.damageSources().mobAttack(this), attackDamage);
        if (success) {
            double knockback = Math.max(0.0, 1.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.4 * knockbackStrength * knockback, 0.0));

            double horizontalSpeed = Math.sqrt(entity.getDeltaMovement().x * entity.getDeltaMovement().x + entity.getDeltaMovement().z * entity.getDeltaMovement().z);
            if (horizontalSpeed > 0.0) {
                entity.setDeltaMovement(
                        entity.getDeltaMovement().x / horizontalSpeed * (horizontalSpeed + horizontalKnockback),
                        entity.getDeltaMovement().y,
                        entity.getDeltaMovement().z / horizontalSpeed * (horizontalSpeed + horizontalKnockback)
                );
            }

            this.doEnchantDamageEffects(this, entity);
        }
        return success;
    }



    public static boolean canSpawn(EntityType<OrcEntity> entityType, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        if (spawnType != MobSpawnType.NATURAL && spawnType != MobSpawnType.CHUNK_GENERATION) {
            return true;
        }

        if (levelAccessor instanceof ServerLevel serverWorld) {
            if (!serverWorld.isDay() || serverWorld.getDifficulty() == Difficulty.PEACEFUL) {
                return false;
            }

            BlockPos surfacePos = serverWorld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
            return surfacePos.getY() >= 60 && surfacePos.getY() <= 100;
        }

        return false;
    }
}
