package net.tiffit.tconplanner.screen.buttons.modifiers;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.screen.buttons.PaginatedPanel;
import net.tiffit.tconplanner.util.TranslationUtil;

public class StackMoveButton extends Button {
    private static final Component MOVE_UP = TranslationUtil.createComponent("modifierstack.moveup");
    private static final Component MOVE_DOWN = TranslationUtil.createComponent("modifierstack.movedown");
    private final PaginatedPanel<ModifierStackButton> scrollPanel;
    private final PlannerScreen parent;
    private final boolean moveUp;

    public StackMoveButton(int x, int y, boolean moveUp, PaginatedPanel<ModifierStackButton> scrollPanel, PlannerScreen parent) {
        super(x, y, 18, 10, Component.literal(""), e -> {}, DEFAULT_NARRATION);
        this.parent = parent;
        this.moveUp = moveUp;
        this.scrollPanel = scrollPanel;
        setTooltip(Tooltip.create(moveUp ? MOVE_UP : MOVE_DOWN));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), 214, 145 + (moveUp ? 0 : height), width, height);
    }
    
    @Override
    public void onPress() {
        if(moveUp){
            if(parent.selectedModifierStackIndex > 0){
                parent.modifierStack.moveDown(parent.selectedModifierStackIndex - 1);
                parent.selectedModifierStackIndex--;
                scrollPanel.makeVisible(parent.selectedModifierStackIndex, false);
                parent.refresh();
            }
        }else{
            if(parent.selectedModifierStackIndex < parent.modifierStack.getStack().size() - 1){
                parent.modifierStack.moveDown(parent.selectedModifierStackIndex);
                parent.selectedModifierStackIndex++;
                scrollPanel.makeVisible(parent.selectedModifierStackIndex, false);
                parent.refresh();
            }
        }
    }
}