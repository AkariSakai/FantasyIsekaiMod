package net.akarisakai.fantasyisekaimod.client.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class GoblinEntity extends Monster implements RangedAttackMob, CrossbowAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_CHARGING_STATE = SynchedEntityData.defineId(GoblinEntity.class, EntityDataSerializers.BOOLEAN);
    public final net.minecraft.world.entity.AnimationState idleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState attackAnimationState = new net.minecraft.world.entity.AnimationState();
    private int cd;
    private final SimpleContainer inventory = new SimpleContainer(5); // You can change the inventory size as needed
    private final int maxPickupCooldown = 60; // Cooldown in ticks (3 seconds with a 20 ticks/second server)
    private int pickupCooldown = 0; // Cooldown counter
    private ItemStack primary = ItemStack.EMPTY;
    private ItemStack trident = ItemStack.EMPTY;
    private boolean persistenceRequired;

    public GoblinEntity(@NotNull EntityType<GoblinEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ATTACK_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedCrossbowAttackGoal<>(this, 1.0D, 12.0F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));


        this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, Zombie.class, 8.0f, 1.2, 1.5));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Skeleton.class, 8.0f, 1.2, 1.5));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Creeper.class, 8.0f, 1.2, 1.5));

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
            this.attackAnimationState.animateWhen(this.swinging, this.tickCount);

        }
        super.tick();


        if (!level().isClientSide()) {
            // Check if the GoblinEntity's inventory is full
            if (isInventoryFull()) {
                return;
            }

            // Reduce the pickup cooldown
            if (pickupCooldown > 0) {
                pickupCooldown--;
            }

            // If the cooldown is zero, look for items to pick up
            if (pickupCooldown == 0) {
                // Find all nearby items
                List<ItemEntity> nearbyItems = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(8.0)); // Adjust the inflate value as needed

                // Find the closest item to the GoblinEntity
                ItemEntity closestItem = null;
                double closestItemDistance = Double.MAX_VALUE;

                for (ItemEntity itemEntity : nearbyItems) {
                    if (!itemEntity.isRemoved() && !itemEntity.getItem().isEmpty()) {
                        double distanceToItem = this.distanceTo(itemEntity);
                        if (distanceToItem < closestItemDistance) {
                            closestItem = itemEntity;
                            closestItemDistance = distanceToItem;
                        }
                    }
                }

                // If the closest item is found and the cooldown is zero, go directly to it
                if (closestItem != null && isAlive() && pickupCooldown <= 0) {
                    this.getNavigation().moveTo(closestItem.getX(), closestItem.getY(), closestItem.getZ(), 1.0);

                    // Check if GoblinEntity is close enough to pick up the item
                    double pickupDistance = 1.35; // Adjust the pickup distance as needed
                    if (this.distanceToSqr(closestItem) <= pickupDistance * pickupDistance) {
                        // Pick up the item and reset the cooldown
                        pickUpItem(closestItem);
                        pickupCooldown = maxPickupCooldown;
                    }
                }
            }
        }

    }

    private boolean isInventoryFull() {
        int maxInventorySize = 5;
        int currentInventorySize = 0;

        // Assuming 'inventory' is a SimpleContainer representing the GoblinEntity's inventory
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                currentInventorySize++;
            }
        }

        // Compare the current inventory size to the maximum capacity
        return currentInventorySize >= maxInventorySize;
    }

    protected void pickUpItem(ItemEntity pItemEntity) {
        ItemStack newItem = pItemEntity.getItem();
        EquipmentSlot slot = getEquipmentSlotForItem(newItem);
        ItemStack currentItem = this.getItemBySlot(slot);

        if (slot == EquipmentSlot.MAINHAND && (newItem.getItem() instanceof SwordItem || newItem.getItem() instanceof TridentItem || newItem.getItem() instanceof CrossbowItem)) {
            if (currentItem.isEmpty() || canReplaceCurrentItem(newItem, currentItem)) {
                // If the main hand is empty or the new item can replace the current one, equip the new item
                equipItem(slot, currentItem, newItem);
                pItemEntity.discard();
                this.onItemPickup(pItemEntity);
            } else {
                // The new item cannot replace the current one, handle it as needed
                handleNewItem(newItem, pItemEntity);
            }
        } else if (slot == EquipmentSlot.OFFHAND && (newItem.getItem() instanceof ShieldItem)) {
            if (currentItem.isEmpty() || canReplaceCurrentItem(newItem, currentItem)) {
                // If the offhand is empty or the new item can replace the current one, equip the new item
                equipItem(slot, currentItem, newItem);
                pItemEntity.discard();
                this.onItemPickup(pItemEntity);
            } else {
                // The new item cannot replace the current one, handle it as needed
                equipItemIfPossible(newItem);
                handleNewItem(newItem, pItemEntity);
            }
        } else {
            // Handle other item types and comparisons
            // You can use equipItemIfPossible or custom logic to determine if the item should be equipped
            equipItemIfPossible(newItem);
            pItemEntity.discard();
            this.onItemPickup(pItemEntity);
        }
    }

    private void equipItem(EquipmentSlot slot, ItemStack currentItem, ItemStack newItem) {
        if (!currentItem.isEmpty()) {
            this.spawnAtLocation(currentItem); // Drop the old item
        }

        this.setItemSlotAndDropWhenKilled(slot, newItem); // Equip the new item
    }

    private void handleNewItem(ItemStack newItem, ItemEntity pItemEntity) {
        ItemStack remainingStack = this.addItemToInventory(newItem.copy());

        if (remainingStack.isEmpty()) {
            // The item was successfully added to the inventory, so discard the entity
            pItemEntity.discard();
        } else {
            // Update the item entity with the remaining stack
            pItemEntity.setItem(remainingStack);
        }
    }


    private ItemStack addItemToInventory(ItemStack stack) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack slotStack = inventory.getItem(i);
            if (slotStack.isEmpty()) {
                inventory.setItem(i, stack);
                return ItemStack.EMPTY;
            } else if (ItemStack.isSameItem(slotStack, stack) && ItemStack.isSameItemSameTags(slotStack, stack)) {
                int space = slotStack.getMaxStackSize() - slotStack.getCount();
                if (stack.getCount() <= space) {
                    slotStack.grow(stack.getCount());
                    return ItemStack.EMPTY;
                } else {
                    stack.shrink(space);
                    slotStack.setCount(slotStack.getMaxStackSize());
                }
            }
        }
        return stack;
    }

    public ItemStack equipItemIfPossible(ItemStack stack) {
        EquipmentSlot slot = getEquipmentSlotForItem(stack);
        ItemStack current = this.getItemBySlot(slot);

        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof TridentItem || stack.getItem() instanceof CrossbowItem) {
            // Handle main hand items
            if (slot == EquipmentSlot.MAINHAND) {
                if (current.isEmpty()) {
                    this.setGuaranteedDrop(slot, stack);
                    return ItemStack.EMPTY;
                } else if (this.canReplaceCurrentItem(stack, current)) {
                    this.setGuaranteedDrop(slot, stack);
                    return current;
                }
            } else if (slot == EquipmentSlot.OFFHAND) {
                // Handle offhand items
                this.setGuaranteedDrop(EquipmentSlot.MAINHAND, stack);
                return current;
            }
        } else if (stack.getItem() instanceof ShieldItem) {
            // Handle shields or Totem of Undying in the offhand
            if (slot == EquipmentSlot.OFFHAND) {
                if (current.isEmpty()) {
                    this.setGuaranteedDrop(slot, stack);
                    return ItemStack.EMPTY;
                } else if (this.canReplaceCurrentItem(stack, current)) {
                    this.setGuaranteedDrop(slot, stack);
                    return current;
                }
            } else if (slot == EquipmentSlot.MAINHAND) {
                // Move offhand items to the main hand
                this.setGuaranteedDrop(EquipmentSlot.OFFHAND, stack);
                return current;
            }
        } else if (stack.getItem() instanceof ArmorItem) {
            // Handle armor items
            EquipmentSlot armorSlot = getEquipmentSlotForItem(stack);
            ItemStack currentArmor = this.getItemBySlot(armorSlot);

            if (currentArmor.isEmpty() || this.canReplaceCurrentItem(stack, currentArmor)) {
                this.setItemSlotAndDropWhenKilled(armorSlot, stack);
                return currentArmor;
            }
        }

        // Handle other item types here, if needed

        return stack;
    }
    public void setGuaranteedDrop(EquipmentSlot pSlot, ItemStack stack) {
        switch (pSlot.getType()) {
            case HAND -> this.handDropChances[pSlot.getIndex()] = 2.0F;
            case ARMOR -> this.armorDropChances[pSlot.getIndex()] = 2.0F;
        }

    }

    protected void setItemSlotAndDropWhenKilled(EquipmentSlot pSlot, ItemStack pStack) {
        this.setItemSlot(pSlot, pStack);
        this.setGuaranteedDrop(pSlot);
        this.persistenceRequired = true;
    }

    protected void dropCustomDeathLoot(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropCustomDeathLoot(source, lootingMultiplier, allowDrops);

        if (allowDrops) {
            // Drop equipped armor
            for (ItemStack armorItem : this.getArmorSlots()) {
                if (!armorItem.isEmpty()) {
                    this.spawnAtLocation(armorItem);
                }
            }

            // Drop the weapon (main hand)
            ItemStack weaponItem = this.getMainHandItem();
            if (!weaponItem.isEmpty()) {
                this.spawnAtLocation(weaponItem);
            }

            // Drop the items from the inventory
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty()) {
                    this.spawnAtLocation(stack);
                }
            }
        }
    }

    public void baseTick() {
        super.baseTick();
        LevelAccessor world = this.level();
        if (!world.isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            if (this.cd > 0) {
                if (this.cd == 1 && !(this.trident.isEmpty())) {
                    this.primary = this.getMainHandItem();
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    this.setItemInHand(InteractionHand.OFF_HAND, this.trident);
                }
                --this.cd;
            }
            for (ThrownTrident proj : this.level().getEntitiesOfClass(ThrownTrident.class, this.getBoundingBox().inflate(0.85D))) {
                if (proj.getOwner() == this && proj.clientSideReturnTridentTickCount > 0 && this.getOffhandItem().isEmpty()) {
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    this.setItemInHand(InteractionHand.OFF_HAND, this.trident);
                    this.cd = 0;
                    proj.discard();
                }
            }
        }
    }

    public int getCD() {
        return this.cd;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (this.getMainHandItem().getItem() instanceof CrossbowItem) {
            this.performCrossbowAttack(this, 2.0F);
        } else if (this.getOffhandItem().getItem() instanceof TridentItem) {
            ItemStack trident = this.getOffhandItem();
            ThrownTrident proj = new ThrownTrident(this.level(), this, trident);
            double d0 = target.getX() - this.getX();
            double d1 = target.getY(0.3333333333333333D) - proj.getY();
            double d2 = target.getZ() - this.getZ();
            double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
            proj.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
            this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().addFreshEntity(proj);
            this.setItemInHand(InteractionHand.MAIN_HAND, this.primary);
            this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            this.cd = 1200;
        }
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity arg0, ItemStack arg1, Projectile arg2, float arg3) {
        this.shootCrossbowProjectile(this, arg0, arg2, arg3, 1.6F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CHARGING_STATE, false);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_CHARGING_STATE);
    }

    public void setChargingCrossbow(boolean charging) {
        this.entityData.set(DATA_CHARGING_STATE, charging);
    }

    public boolean canReplaceCurrentItem(ItemStack drop, ItemStack hand) {
        if (drop.getItem() instanceof SwordItem) {
            if (hand.isEmpty() || !(hand.getItem() instanceof SwordItem)) {
                return true;
            } else {
                SwordItem newItem = (SwordItem) drop.getItem();
                SwordItem currentItem = (SwordItem) hand.getItem();

                // Compare the attributes of the items (you can add more checks as needed)
                return newItem.getDamage() > currentItem.getDamage();
            }
        } else if (drop.getItem() instanceof ArmorItem) {
            if (hand.isEmpty() || !(hand.getItem() instanceof ArmorItem)) {
                return true;
            } else {
                ArmorItem newItem = (ArmorItem) drop.getItem();
                ArmorItem currentItem = (ArmorItem) hand.getItem();

                // Compare the attributes of the items (e.g., defense, toughness)
                return newItem.getDefense() > currentItem.getDefense();
                // You can add more attribute comparisons here as needed
            }
        }
        // Add similar checks for other item types as needed

        return false;
    }


    public boolean canReplaceEqualItem(ItemStack pCandidate, ItemStack pExisting) {
        if (pCandidate.getDamageValue() < pExisting.getDamageValue() || pCandidate.hasTag() && !pExisting.hasTag()) {
            return true;
        } else if (pCandidate.hasTag() && pExisting.hasTag()) {
            return pCandidate.getTag().getAllKeys().stream().anyMatch((p_21513_) -> !p_21513_.equals("Damage")) && pExisting.getTag().getAllKeys().stream().allMatch((p_21503_) -> p_21503_.equals("Damage"));
        } else {
            return false;
        }
    }

    public boolean canHoldItem(ItemStack pStack) {
        return true;
    }

    public boolean wantsToPickUp(ItemStack pStack) {
        return this.canHoldItem(pStack);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return true;
    }

    public boolean requiresCustomPersistence() {
        return this.isPassenger();
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }


    public static boolean canSpawn(EntityType<GoblinEntity> entityType, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
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

    @Override
    public boolean isPersistenceRequired() {
        return persistenceRequired;
    }
}