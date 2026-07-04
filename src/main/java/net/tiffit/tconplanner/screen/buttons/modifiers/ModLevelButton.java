package net.tiffit.tconplanner.screen.buttons.modifiers;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.util.ModifierStack;
import net.tiffit.tconplanner.util.TranslationUtil;

public class ModLevelButton extends Button {

    private final PlannerScreen parent;
    private final int change;
    private boolean disabled = false;
    private Component tooltip;

    public ModLevelButton(int x, int y, int change, PlannerScreen parent) {
        super(x, y, 18, 17, Component.literal(""), e -> {}, DEFAULT_NARRATION);
        this.parent = parent;
        this.change = change;
    }

    public void disable(Component tooltip){
        this.tooltip = tooltip;
        disabled = true;
    }

    public boolean isDisabled(){
        return disabled;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, disabled ? 0.5f : 1f);
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), change > 0 ? 176 : 194, disabled  ? 146 : 163, width, height);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if(isHoveredOrFocused()){
            renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    @Override
    public void onPress() {
        if(!disabled) {
            ModifierStack stack = parent.blueprint.modStack;
            stack.setIncrementalDiff(parent.selectedModifier.modifier, 0, parent.selectedModifier.neededPerLevel);
            if(change > 0)stack.push(parent.selectedModifier);
            else stack.pop(parent.selectedModifier);
            parent.refresh();
        }
    }

//    @Override
    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if(disabled) {
            parent.postRenderTasks.add(() -> guiGraphics.renderTooltip(parent.getMinecraft().font ,tooltip, mouseX, mouseY));
        }else{
            parent.postRenderTasks.add(() -> guiGraphics.renderComponentTooltip(parent.getMinecraft().font, List.of(TranslationUtil.createComponent(change < 0 ? "modifiers.removelevel" : "modifiers.addlevel").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))), mouseX, mouseY));
        }
    }

    @Override
    public void playDownSound(SoundManager handler) {
        if(!disabled)super.playDownSound(handler);
    }
}