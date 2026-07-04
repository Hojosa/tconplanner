package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.tiffit.tconplanner.screen.PlannerScreen;

public class TextButton extends Button {

    private final PlannerScreen parent;
    private final Runnable onPress;
    private int color = 0xff_ff_ff;
    private Component tooltip = Component.literal("");

    public TextButton(int x, int y, Component text, Runnable onPress, PlannerScreen parent) {
        super(x, y, 58, 18, text, e -> {}, DEFAULT_NARRATION);
        this.parent = parent;
        this.onPress = onPress;
    }

    public TextButton withColor(int color){
        this.color = color;
        return this;
    }

    public TextButton withTooltip(Component tooltip){
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(((color & 0xff0000) >> 16)/255f, ((color & 0x00ff00) >> 8)/255f, (color & 0x0000ff)/255f,1f);
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), 176, 183, width, height);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, getMessage(), getX() + width/2, getY() + 5, isHovered ? 0xffffffff : 0xa0ffffff);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if(isHovered){
        	parent.postRenderTasks.add(() -> guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltip, mouseX, mouseY));
        }
    }

    @Override
    public void onPress() {
        onPress.run();
    }
}