package net.tiffit.tconplanner.data;

import java.lang.reflect.Field;
import java.util.Objects;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.SlotType;

public class ModifierInfo {

    public final IDisplayModifierRecipe recipe;
    public final Modifier modifier;
    public final SlotType.SlotCount count;
    public int neededPerLevel;

    public ModifierInfo(IDisplayModifierRecipe recipe){
        this.recipe = recipe;
        this.modifier = recipe.getDisplayResult().getModifier();
        this.count = recipe.getSlots();
        if (recipe instanceof IncrementalModifierRecipe incrementalRecipe) {
            try {
                Field f = IncrementalModifierRecipe.class.getDeclaredField("neededPerLevel");
                f.setAccessible(true);
                this.neededPerLevel = f.getInt(incrementalRecipe);
            } catch (Exception e) {
                this.neededPerLevel = 0;
            }
        } else {
            this.neededPerLevel = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModifierInfo info = (ModifierInfo) o;
        return ((ITinkerStationRecipe)recipe).getId().equals(((ITinkerStationRecipe)info.recipe).getId()) && modifier.getId().equals(info.modifier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, modifier);
    }
}
