package net.akarisakai.fantasyisekaimod.init;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundsInit {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FantasyIsekaiMod.MOD_ID);
    public static final RegistryObject<SoundEvent> GOBLIN_LAUGH = REGISTRY.register("goblin_laugh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("fantasyisekaimod", "goblin_laugh")));
    public static final RegistryObject<SoundEvent> GOBLIN_PURR = REGISTRY.register("goblin_purr", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("fantasyisekaimod", "goblin_purr")));
    public static final RegistryObject<SoundEvent> GOBLIN_DEATH = REGISTRY.register("goblin_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("fantasyisekaimod", "goblin_death")));
    public static final RegistryObject<SoundEvent> GOBLIN_HURT = REGISTRY.register("goblin_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("fantasyisekaimod", "goblin_hurt")));
    public static final RegistryObject<SoundEvent> GOBLIN_IDLE = REGISTRY.register("goblin_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("fantasyisekaimod", "goblin_idle")));

}