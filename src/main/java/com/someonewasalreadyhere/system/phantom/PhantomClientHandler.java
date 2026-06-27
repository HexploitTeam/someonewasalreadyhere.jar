package com.someonewasalreadyhere.system.phantom;

import com.someonewasalreadyhere.client.gui.ErrorScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomClientHandler {

    private static boolean active = false;

    public static void setActive(boolean value) {
        active = value;
    }

    public static boolean isActive() {
        return active;
    }

    public static void openErrorScreen() {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) {
            mc.execute(() -> mc.setScreen(new ErrorScreen()));
        }
    }
}
