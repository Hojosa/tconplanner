package net.tiffit.tconplanner.screen;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.api.TCSlotPos;
import net.tiffit.tconplanner.data.Blueprint;
import net.tiffit.tconplanner.data.PlannerData;
import net.tiffit.tconplanner.screen.buttons.IconButton;
import net.tiffit.tconplanner.screen.buttons.OutputToolWidget;
import net.tiffit.tconplanner.screen.buttons.ToolPartButton;
import net.tiffit.tconplanner.util.Icon;
import net.tiffit.tconplanner.util.TranslationUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IToolPart;

public class ToolTopPanel extends PlannerPanel{

    public ToolTopPanel(int x, int y, int width, int height, ItemStack result, ToolStack tool, PlannerData data, PlannerScreen parent) {
        super(x, y, width, height, parent);
        //Add the tool part buttons
        Blueprint blueprint = parent.blueprint;
        List<TCSlotPos> positions = blueprint.tool.getSlotPos();
        for(int i = 0; i < blueprint.parts.length; i++){
            TCSlotPos pos = positions.get(i);
            IToolPart part = blueprint.parts[i];
            addChild(new ToolPartButton(i, pos.getX(), pos.getY(), part, blueprint.materials[i], parent));
        }

        //Add randomize tool button
        addChild(new IconButton(parent.guiWidth - 70, 88, new Icon(3, 0),
                TranslationUtil.createComponent("randomize"), parent, e -> parent.randomize())
                .withSound(SoundEvents.ENDERMAN_TELEPORT));

        if(tool != null){
            addChild(new OutputToolWidget(parent.guiWidth - 34, 58, result, parent));
            boolean bookmarked = data.isBookmarked(blueprint);
            boolean starred = blueprint.equals(data.starred);
            addChild(new IconButton(parent.guiWidth - 33, 88, new Icon(bookmarked ? 2 : 1, 0),
                    TranslationUtil.createComponent(bookmarked ? "bookmark.remove" : "bookmark.add"), parent, e -> {if(bookmarked) parent.unbookmarkCurrent(); else parent.bookmarkCurrent();})
                    .withSound(bookmarked ? SoundEvents.UI_STONECUTTER_TAKE_RESULT : SoundEvents.BOOK_PAGE_TURN));
            if(bookmarked){
                addChild(new IconButton(parent.guiWidth - 18, 88, new Icon(starred ? 7 : 6, 0),
                        TranslationUtil.createComponent(starred ? "star.remove" : "star.add"), parent, e -> {if(starred) parent.unstarCurrent(); else parent.starCurrent();})
                        .withSound(starred ? SoundEvents.UI_STONECUTTER_TAKE_RESULT : SoundEvents.BOOK_PAGE_TURN));
            }
            assert Minecraft.getInstance().player != null;
            if(Minecraft.getInstance().player.isCreative()) {
                addChild(new IconButton(parent.guiWidth - 48, 88, new Icon(4, 0), TranslationUtil.createComponent("giveitem"), parent, e -> parent.giveItemstack(result))
                        .withSound(SoundEvents.ITEM_PICKUP));
            }
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_230430_4_) {
    	guiGraphics.pose().pushPose();
    	guiGraphics.pose().translate(getX() + TCSlotPos.partsOffsetX + 7, getY() + TCSlotPos.partsOffsetY + 22, -200);
    	guiGraphics.pose().scale(.7F, 3.7F, 1.0F);
    	guiGraphics.renderItem(parent.blueprint.toolStack, 0, 0);
    	guiGraphics.pose().popPose();

        int boxX = 13, boxY = 24, boxL = 81;
        if(mouseX > boxX + getX() && mouseY > boxY + getY() && mouseX < boxX + getX() + boxL && mouseY < boxY + getY() + boxL)
            RenderSystem.setShaderColor(1f, 1f, 1f, 0.75f);
        else RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        guiGraphics.blit(PlannerScreen.TEXTURE, getX() + boxX, getY() + boxY, boxX, boxY, boxL, boxL);
        super.renderWidget(guiGraphics, mouseX, mouseY, p_230430_4_);
    }
}