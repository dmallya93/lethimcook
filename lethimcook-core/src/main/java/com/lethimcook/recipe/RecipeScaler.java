package com.lethimcook.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for scaling recipes to different serving sizes.
 *
 * <p>This class provides functionality to proportionally scale ingredient amounts
 * while preserving all recipe metadata. It handles special cases like ingredients
 * without amounts (e.g., "salt to taste") and ensures immutability through Java records.
 *
 * <p>Example usage:
 * <pre>{@code
 * Recipe original = new Recipe(4, List.of(
 *     new Ingredient(2.0, "cups", "flour"),
 *     new Ingredient(1.0, "tsp", "salt")
 * ));
 * Recipe scaled = RecipeScaler.scaleRecipe(original, 8);
 * // scaled will have 4 cups of flour and 2 tsp of salt
 * }</pre>
 */
public final class RecipeScaler {

    private RecipeScaler() {
        // Utility class - prevent instantiation
    }

    /**
     * Scales a recipe to a different number of servings.
     *
     * <p>The scaling process:
     * <ul>
     *   <li>Calculates scale factor: newServings / originalServings</li>
     *   <li>Multiplies each ingredient amount by the scale factor</li>
     *   <li>Preserves ingredients with null amounts (unmeasured ingredients)</li>
     *   <li>Preserves all ingredient properties (unit, name, note)</li>
     *   <li>Preserves all recipe properties (name, prepTime)</li>
     * </ul>
     *
     * @param recipe The original recipe to scale
     * @param newServings The target number of servings (must be positive)
     * @return A new Recipe instance with scaled ingredient amounts
     * @throws IllegalArgumentException if newServings is not positive
     */
    public static Recipe scaleRecipe(Recipe recipe, int newServings) {
        if (newServings <= 0) {
            throw new IllegalArgumentException("New servings must be positive");
        }

        double scaleFactor = (double) newServings / recipe.servings();

        // Scale each ingredient
        List<Ingredient> scaledIngredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.ingredients()) {
            Double scaledAmount;
            if (ingredient.amount() != null) {
                scaledAmount = ingredient.amount() * scaleFactor;
            } else {
                // Preserve null amounts (e.g., "salt to taste")
                scaledAmount = null;
            }

            // Create new ingredient with scaled amount, preserving all other properties
            Ingredient scaledIngredient = new Ingredient(
                scaledAmount,
                ingredient.unit(),
                ingredient.name(),
                ingredient.note()
            );
            scaledIngredients.add(scaledIngredient);
        }

        // Create new recipe with scaled ingredients, preserving all other properties
        return new Recipe(
            newServings,
            scaledIngredients,
            recipe.name(),
            recipe.prepTime()
        );
    }
}
