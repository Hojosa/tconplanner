package net.tiffit.tconplanner.screen.buttons.modifiers;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.tiffit.tconplanner.screen.PlannerScreen;

public class ModPreviewWidget extends AbstractWidget {
    private final ItemStack stack;
    private final boolean disabled;
    private final PlannerScreen parent;

    public ModPreviewWidget(int x, int y, ItemStack stack, PlannerScreen parent){
        super(x, y, 16, 16, Component.literal(""));
        this.parent = parent;
        this.disabled = stack.isEmpty();
        this.stack = disabled ? new ItemStack(Items.BARRIER) : stack;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
    	guiGraphics.renderItem(this.stack, getX(), getY());
        if(isHovered && !disabled){
            renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

//    @Override
    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        parent.postRenderTasks.add(() -> parent.renderItemTooltip(guiGraphics, this.stack, mouseX, mouseY));
    }

    @Override
    public void playDownSound(SoundManager sound) {}

    @Override
    public void updateWidgetNarration(NarrationElementOutput p_169152_) {

    }
}