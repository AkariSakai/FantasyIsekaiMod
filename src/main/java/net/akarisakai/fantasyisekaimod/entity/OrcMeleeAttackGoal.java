package net.akarisakai.fantasyisekaimod.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class OrcMeleeAttackGoal extends MeleeAttackGoal {
    private static final int POST_ATTACK_DELAY = 40; // 2 seconds at 20 ticks per second

    private int postAttackTicks = 0;
    public boolean isAttacking = false;

    public OrcMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Override
    public void tick() {
        if (postAttackTicks > 0) {
            postAttackTicks--;
            isAttacking = false;
        } else {
            super.tick();
            isAttacking = true;
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
            postAttackTicks = POST_ATTACK_DELAY;
        }
    }

    @Override
    protected void resetAttackCooldown() {
        super.resetAttackCooldown();
        postAttackTicks = POST_ATTACK_DELAY;
    }

    public boolean isAttacking() {
        return isAttacking;
    }
}