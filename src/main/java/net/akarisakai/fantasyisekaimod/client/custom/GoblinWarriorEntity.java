package net.akarisakai.fantasyisekaimod.client.custom;

import net.akarisakai.fantasyisekaimod.entity.AbstractGoblin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;



public class GoblinWarriorEntity extends AbstractGoblin {
    public final AnimationState idleAnimationState = new AnimationState();


    public GoblinWarriorEntity(EntityType<GoblinWarriorEntity> goblinEntityEntityType, Level level) {
        super(goblinEntityEntityType, level);
        ItemStack weapon = getRandomWeapon();
        this.setItemInHand(InteractionHand.MAIN_HAND, weapon);
        this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
    }

    // Method to get a randomly chosen wooden sword or stone axe
    private ItemStack getRandomWeapon() {
        // Create an array of possible weapon items
        ItemStack[] weaponChoices = {
                new ItemStack(Items.WOODEN_SWORD),
                new ItemStack(Items.STONE_AXE),
                new ItemStack(Items.STONE_SWORD),
                new ItemStack(Items.WOODEN_AXE),
                new ItemStack(Items.GOLDEN_SWORD),
                new ItemStack(Items.GOLDEN_AXE)

        };

        // Randomly select a weapon from the array
        int randomIndex = random.nextInt(weaponChoices.length);
        return weaponChoices[randomIndex];
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.goalSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.goalSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Creeper.class, 8.0f, 1.2, 1.5));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, true));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ATTACK_SPEED, 1.6)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }



    public static boolean canSpawn(EntityType<GoblinWarriorEntity> entityType, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
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