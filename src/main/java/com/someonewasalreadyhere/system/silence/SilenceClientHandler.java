package com.someonewasalreadyhere.system.silence;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SilenceClientHandler {

    private static float currentSilence = 0f;

    public static void applySilence(float silence) {
        currentSilence = silence;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return;

        // Scale ambient volume inversely with silence
        float ambientVolume = Math.max(0f, 1.0f - silence);
        mc.options.getSoundSourceOptionInstance(net.minecraft.sounds.SoundSource.AMBIENT).set((double) ambientVolume);

        // Apply blindness/darkness client-side at high silence levels
        if (silence > 0.5f && mc.player != null) {
            mc.player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0, false, false));
        }
    }

    public static float getCurrentSilence() {
        return currentSilence;
    }
}
