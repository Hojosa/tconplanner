package net.tiffit.tconplanner.screen.buttons;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.screen.PlannerScreen;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.part.IToolPart;

public class ToolPartButton extends Button {

    private final ItemStack stack;
    private final IMaterial material;
    public final IToolPart part;
    private final PlannerScreen parent;
    public final int index;

    public ToolPartButton(int index, int x, int y, IToolPart part, IMaterial material, PlannerScreen parent){
        super(x, y, 16, 16, Component.literal(""), button -> parent.setSelectedPart(index), DEFAULT_NARRATION);
        this.index = index;
        this.part = part;
        this.parent = parent;
        this.material = material;
        stack = material == null ? new ItemStack(part.asItem()) : part.withMaterialForDisplay(material.getIdentifier());
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
        boolean selected = parent.selectedPart == index;
        guiGraphics.pose().pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.pose().translate(0, 0, 1);
        RenderSystem.setShaderColor(1f, 1f, 1f, 0.7f);
        RenderSystem.enableBlend();
        guiGraphics.blit(PlannerScreen.TEXTURE, getX() - 1, getY() - 1, 176 + (material == null ? 18 : 0), 41 + (selected ? 18 : 0), 18, 18);
        guiGraphics.pose().popPose();
        guiGraphics.renderItem(this.stack, getX(), getY());
        if(isHovered){
        	parent.postRenderTasks.add(() -> guiGraphics.renderTooltip(Minecraft.getInstance().font, stack, mouseX, mouseY));
        }
    }
}