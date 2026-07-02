package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.screen.PlannerScreen;

public class OutputToolWidget extends AbstractWidget {

    private final ItemStack stack;
    private final PlannerScreen parent;

    public OutputToolWidget(int x, int y, ItemStack stack, PlannerScreen parent){
        super(x, y, 16, 16, Component.literal(""));
        this.parent = parent;
        this.stack = stack;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
    	RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    	guiGraphics.blit(PlannerScreen.TEXTURE, getX() - 6, getY() - 6, 176, 117, 28, 28);
    	guiGraphics.renderItem(this.stack, getX(), getY());
        if(isHovered){
            renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        parent.postRenderTasks.add(() -> parent.renderItemTooltip(guiGraphics, this.stack, mouseX, mouseY));
    }

    @Override
    public void playDownSound(SoundManager sound) {}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
		
	}
}