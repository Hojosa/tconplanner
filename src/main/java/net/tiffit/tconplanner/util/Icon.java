package net.tiffit.tconplanner.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.tiffit.tconplanner.TConPlanner;

public class Icon {
    private static final ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath(TConPlanner.MODID, "textures/gui/icons.png");

    private final int x, y;

    public Icon(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void render(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(ICONS, x, y, this.x*12, this.y*12, 12, 12);
    }
}