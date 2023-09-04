package net.akarisakai.fantasyisekaimod.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;

public class AbstractGoblin extends PatrollingGoblin {
    protected AbstractGoblin(EntityType<? extends AbstractGoblin> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        super.registerGoals();
    }


    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    public AbstractGoblin.GoblinArmPose getArmPose() {
        return AbstractGoblin.GoblinArmPose.CROSSED;
    }

    public boolean canAttack(LivingEntity pTarget) {
        return pTarget instanceof AbstractVillager && pTarget.isBaby() ? false : super.canAttack(pTarget);
    }

    public static enum GoblinArmPose {
        CROSSED,
        ATTACKING,
        SPELLCASTING,
        BOW_AND_ARROW,
        CROSSBOW_HOLD,
        CROSSBOW_CHARGE,
        CELEBRATING,
        NEUTRAL;

        private GoblinArmPose() {
        }
    }

}
