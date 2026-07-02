package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.tiffit.tconplanner.screen.PlannerScreen;

public class BannerWidget extends AbstractWidget {

    public BannerWidget(int x, int y, Component text, PlannerScreen parent) {
        super(x, y, 90, 19, text);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
    	RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), 0, 205, width, height);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, getMessage(), getX() + width/2, getY() + 5, 0xff_90_90_ff);
    }

    @Override
    public void playDownSound(SoundManager SoundManager) {}

    @Override
    public void updateWidgetNarration(NarrationElementOutput p_169152_) {

    }
}