package net.akarisakai.fantasyisekaimod.entity;

import net.akarisakai.fantasyisekaimod.client.custom.GoblinShamanEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.IntFunction;

public abstract class SpellcasterGoblin extends AbstractGoblin {
    private static final EntityDataAccessor<Byte> DATA_SPELL_CASTING_ID;
    protected int spellCastingTickCount;
    public net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell currentSpell;

    protected SpellcasterGoblin(EntityType<GoblinShamanEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.currentSpell = net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell.NONE;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SPELL_CASTING_ID, (byte)0);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.spellCastingTickCount = pCompound.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("SpellTicks", this.spellCastingTickCount);
    }


    public boolean isCastingSpell() {
        if (this.level().isClientSide) {
            return (Byte)this.entityData.get(DATA_SPELL_CASTING_ID) > 0;
        } else {
            return this.spellCastingTickCount > 0;
        }
    }

    public void setIsCastingSpell(net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell pCurrentSpell) {
        this.currentSpell = pCurrentSpell;
        this.entityData.set(DATA_SPELL_CASTING_ID, (byte)pCurrentSpell.id);
    }

    protected net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell getCurrentSpell() {
        return !this.level().isClientSide ? this.currentSpell : net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell.byId((Byte)this.entityData.get(DATA_SPELL_CASTING_ID));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellCastingTickCount > 0) {
            --this.spellCastingTickCount;
        }

    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell()) {
            net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell $$0 = this.getCurrentSpell();
            double $$1 = $$0.spellColor[0];
            double $$2 = $$0.spellColor[1];
            double $$3 = $$0.spellColor[2];
            float $$4 = this.yBodyRot * 0.017453292F + Mth.cos((float)this.tickCount * 0.6662F) * 0.25F;
            float $$5 = Mth.cos($$4);
            float $$6 = Mth.sin($$4);
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)$$5 * 0.6, this.getY() + 1.8, this.getZ() + (double)$$6 * 0.6, $$1, $$2, $$3);
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() - (double)$$5 * 0.6, this.getY() + 1.8, this.getZ() - (double)$$6 * 0.6, $$1, $$2, $$3);
        }

    }

    protected int getSpellCastingTime() {
        return this.spellCastingTickCount;
    }

    protected abstract SoundEvent getCastingSoundEvent();

    static {
        DATA_SPELL_CASTING_ID = SynchedEntityData.defineId(net.minecraft.world.entity.monster.SpellcasterIllager.class, EntityDataSerializers.BYTE);
    }

    protected static enum IllagerSpell {
        NONE(0, 0.0, 0.0, 0.0),
        SUMMON_VEX(1, 0.7, 0.7, 0.8),
        FANGS(2, 0.4, 0.3, 0.35),
        WOLOLO(3, 0.7, 0.5, 0.2),
        DISAPPEAR(4, 0.3, 0.3, 0.8),
        BLINDNESS(5, 0.1, 0.1, 0.2);

        private static final IntFunction<net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell> BY_ID = ByIdMap.continuous((p_263091_) -> {
            return p_263091_.id;
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        final int id;
        final double[] spellColor;

        private IllagerSpell(int pId, double pRed, double pGreen, double pBlue) {
            this.id = pId;
            this.spellColor = new double[]{pRed, pGreen, pBlue};
        }

        public static net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell byId(int pId) {
            return (net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell)BY_ID.apply(pId);
        }
    }

    public abstract class SpellcasterUseSpellGoal extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        public SpellcasterUseSpellGoal() {
        }

        public boolean canUse() {
            LivingEntity $$0 = net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.getTarget();
            if ($$0 != null && $$0.isAlive()) {
                if (net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.isCastingSpell()) {
                    return false;
                } else {
                    return net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.tickCount >= this.nextAttackTickCount;
                }
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity $$0 = net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.getTarget();
            return $$0 != null && $$0.isAlive() && this.attackWarmupDelay > 0;
        }

        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
            net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.spellCastingTickCount = this.getCastingTime();
            this.nextAttackTickCount = net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.tickCount + this.getCastingInterval();
            SoundEvent $$0 = this.getSpellPrepareSound();
            if ($$0 != null) {
                net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.playSound($$0, 1.0F, 1.0F);
            }

            net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.setIsCastingSpell(this.getSpell());
        }

        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performSpellCasting();
                net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.playSound(net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.getCastingSoundEvent(), 1.0F, 1.0F);
            }

        }

        protected abstract void performSpellCasting();

        protected int getCastWarmupTime() {
            return 20;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();

        protected abstract IllagerSpell getSpell();
    }

    protected class SpellcasterCastingSpellGoal extends Goal {
        public SpellcasterCastingSpellGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.getSpellCastingTime() > 0;
        }

        public void start() {
            super.start();
            net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.navigation.stop();
        }

        public void stop() {
            super.stop();
            net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.setIsCastingSpell(net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.IllagerSpell.NONE);
        }

        public void tick() {
            if (net.akarisakai.fantasyisekaimod.entity.SpellcasterGoblin.this.getTarget() != null) {
            }

        }
    }
}
