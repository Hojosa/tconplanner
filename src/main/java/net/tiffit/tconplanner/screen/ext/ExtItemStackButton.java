package net.tiffit.tconplanner.screen.ext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.EventListener;
import net.tiffit.tconplanner.screen.buttons.BookmarkedButton;

public class ExtItemStackButton extends Button {
    public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tconstruct", "textures/gui/tinker_station.png");

    private final ItemStack stack;
    private final Screen screen;
    private final List<Component> tooltips;

    public ExtItemStackButton(int x, int y, ItemStack stack, List<Component> tooltips, Button.OnPress action, Screen screen) {
        super(x, y, 16, 16, Component.literal(""), action, DEFAULT_NARRATION);
        this.stack = stack;
        this.screen = screen;
        this.tooltips = tooltips == null ? Collections.emptyList() : tooltips;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230431_4_) {
        Minecraft mc = screen.getMinecraft();
        guiGraphics.blit(BACKGROUND, getX() - 1, getY() - 1, 194, 0, 18, 18);
        if(!isHoveredOrFocused()){
        	guiGraphics.fill(getX(), getY(), getX() + 16, getY() + 16, 0xff_a29b81);
        }
        guiGraphics.renderItem(stack, getX(), getY());
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 0.6f);
        BookmarkedButton.STAR_ICON.render(guiGraphics, getX() + 2, getY() + 2);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.pose().popPose();
        if (this.isHoveredOrFocused()) {
            EventListener.postRenderQueue.offer(() -> {
                List<Component> result = Stream.concat(Screen.getTooltipFromItem(mc, stack).stream(), tooltips.stream()).collect(Collectors.toList());
                guiGraphics.renderComponentTooltip(mc.font, result, mouseX, mouseY);
            });
        }
    }
}