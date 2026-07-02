package net.tiffit.tconplanner.screen.buttons.modifiers;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.data.ModifierInfo;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.util.TranslationUtil;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.tools.SlotType;

public class ModifierStackButton extends Button {

    private final Modifier modifier;
    private final IDisplayModifierRecipe recipe;
    private final ModifierInfo modifierInfo;
    private final PlannerScreen parent;
    private final Component displayName;
    private final ItemStack display;
    private final int index;

    public ModifierStackButton(ModifierInfo modifierInfo, int index, int level, ItemStack display, PlannerScreen parent) {
        super(0, 0, 100, 18, Component.literal(""), e -> {}, DEFAULT_NARRATION);
        this.modifierInfo = modifierInfo;
        this.parent = parent;
        this.modifier = modifierInfo.modifier;
        this.recipe = modifierInfo.recipe;
        this.display = display;
        this.index = index;
        displayName = modifier.getDisplayName(level);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
//        PlannerScreen.bindTexture();
        RenderSystem.enableBlend();
        if(parent.selectedModifierStackIndex == index){
            RenderSystem.setShaderColor(255/255f, 200/255f, 0f, 1f);
        }
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), 0, 224, 100, 18);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.renderItem(display, getX() + 1, getY() + 1);
        Font font = Minecraft.getInstance().font;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getX() + 20, getY() + 2, 0);
        float nameWidth = font.width(displayName);
        int maxWidth = width - 22;
        if (nameWidth > maxWidth) {
            float scale = maxWidth / nameWidth;
            guiGraphics.pose().scale(scale, scale, 1);
        }
        guiGraphics.drawString(font, displayName, 0, 0, 0xff_ff_ff_ff);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getX() + 20, getY() + 11, 0);
        guiGraphics.pose().scale(0.5f, 0.5f, 1);
        if (recipe.getSlots() != null) {
            SlotType.SlotCount count = recipe.getSlots();
            MutableComponent text = count.count() == 1 ? TranslationUtil.createComponent("modifiers.usedslot", count.type().getDisplayName()) :
                    TranslationUtil.createComponent("modifiers.usedslots", count.count(), count.type().getDisplayName());
            guiGraphics.drawString(font, text, 0, 0, 0xff_ff_ff_ff);
        }
        guiGraphics.pose().popPose();
        if (isHovered) {
            renderToolTip(guiGraphics, font, mouseX, mouseY);
        }
    }

//    @Override
    public void renderToolTip(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        parent.postRenderTasks.add(() -> {
            List<Component> tooltips = new ArrayList<>(modifier.getDescriptionList());
            guiGraphics.renderComponentTooltip(font, tooltips, mouseX, mouseY);
        });
    }

    @Override
    public void onPress() {
        parent.selectedModifierStackIndex = index;
        parent.refresh();
    }
}
