package com.someonewasalreadyhere.mixin;

import com.someonewasalreadyhere.system.phantom.PhantomClientHandler;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Options.class)
public class OptionsMixin {

    /**
     * Forces render distance to 4 when PhantomPlayer is active.
     * This contributes to the "thick fog / truncated world" horror effect.
     */
    @Inject(method = "renderDistance", at = @At("HEAD"), cancellable = true)
    private void injectRenderDistance(CallbackInfoReturnable<Integer> cir) {
        if (PhantomClientHandler.isActive()) {
            cir.setReturnValue(4);
        }
    }
}
