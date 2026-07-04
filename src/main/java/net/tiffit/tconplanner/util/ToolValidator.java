package net.tiffit.tconplanner.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tiffit.tconplanner.data.Blueprint;
import net.tiffit.tconplanner.data.ModifierInfo;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public final class ToolValidator {

    /**
     * Validate if a modifier is able to be removed from a tool
     * @param tool The tool to try to remove a modifier from
     * @param modInfo The modifier to remove
     */
    public static RecipeResult<ItemStack> validateModRemoval(Blueprint bp, ToolStack tool, ModifierInfo modInfo){
        ToolStack toolClone = tool.copy();
        int toolBaseLevel = ToolStack.from(bp.createOutput(false)).getModifierLevel(modInfo.modifier);
        int minLevel = Math.max(0, toolBaseLevel);
        if(bp.modStack.getLevel(modInfo.modifier) + toolBaseLevel <= minLevel || !bp.modStack.isRecipeUsed((ITinkerStationRecipe) modInfo.recipe))
            return RecipeResult.failure("gui.tconplanner.modifiers.error.minlevel");
        toolClone.removeModifier(modInfo.modifier.getId(), 1);
        if(modInfo.neededPerLevel > 0){
            toolClone.addModifierAmount(modInfo.modifier.getId(), modInfo.neededPerLevel, modInfo.neededPerLevel);
        }
        Component validationError = toolClone.tryValidate();
        if(validationError != null) return RecipeResult.failure(validationError);
        Blueprint bpClone = bp.clone();
        bpClone.modStack.pop(modInfo);
        RecipeResult<ItemStack> bpResult = bpClone.validate();
        if(bpResult.hasError())return bpResult;
        return RecipeResult.success(toolClone.createStack());
    }
}