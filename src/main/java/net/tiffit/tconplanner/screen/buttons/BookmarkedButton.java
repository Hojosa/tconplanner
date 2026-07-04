package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.data.Blueprint;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.util.Icon;

public class BookmarkedButton extends Button {

    public static final Icon STAR_ICON = new Icon(6, 0);

    private final PlannerScreen parent;
    private final ItemStack stack;
    private final boolean starred;
    private boolean selected;

    public BookmarkedButton(int index, Blueprint blueprint, boolean starred, PlannerScreen parent){
        super(0, 0, 18, 18, Component.literal(""), button -> parent.setBlueprint(blueprint.clone()), DEFAULT_NARRATION);
        this.starred = starred;
        this.parent = parent;
        stack = blueprint.createOutput();
        this.selected = parent.blueprint != null && parent.blueprint.equals(blueprint);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
    	RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        guiGraphics.blit(PlannerScreen.TEXTURE, getX(), getY(), 213, 41 + (selected ? 18 : 0), 18, 18);
        guiGraphics.renderItem(this.stack, getX() + 1, getY() + 1);
        if(starred){
        	guiGraphics.pose().pushPose();
        	guiGraphics.pose().translate(getX() + 11, getY() + 11, 105);
        	guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
            STAR_ICON.render(guiGraphics, 0, 0);
            guiGraphics.pose().popPose();
        }
        if(isHovered){
        	parent.postRenderTasks.add(() -> guiGraphics.renderTooltip(Minecraft.getInstance().font, this.stack, mouseX, mouseY));
        }
    }
}