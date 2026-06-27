package com.someonewasalreadyhere.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ErrorScreen extends Screen {

    public ErrorScreen() {
        super(Component.literal("Err.player"));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.literal("OK"), btn -> this.onClose())
            .pos(this.width / 2 - 50, this.height / 2 + 20)
            .size(100, 20)
            .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        // Title
        graphics.drawCenteredString(this.font, "§c§l" + this.title.getString(),
            this.width / 2, this.height / 2 - 40, 0xFFFFFF);

        // Error body
        graphics.drawCenteredString(this.font, "§8A critical error occurred.",
            this.width / 2, this.height / 2 - 20, 0xAAAAAA);
        graphics.drawCenteredString(this.font, "§8Player identity: UNRESOLVED",
            this.width / 2, this.height / 2 - 10, 0xAAAAAA);
        graphics.drawCenteredString(this.font, "§8Status: OBSERVED",
            this.width / 2, this.height / 2, 0xFF4444);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
