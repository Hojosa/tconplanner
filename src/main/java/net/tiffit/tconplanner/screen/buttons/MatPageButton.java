package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.tiffit.tconplanner.screen.PlannerScreen;

public class MatPageButton extends Button {
    private final boolean right;
    public MatPageButton(int x, int y, int change, PlannerScreen parent) {
        super(x, y, 38, 20, Component.literal(""), button -> {parent.materialPage += change; parent.refresh();}, DEFAULT_NARRATION);
        right = change > 0;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
    	RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    	guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), right ? 176 : 214, active ? 20 : 0, width, height);
    }
}