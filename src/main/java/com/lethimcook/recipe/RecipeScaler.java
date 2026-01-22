package com.lethimcook.recipe;

import java.util.List;

/**
 * Utility class for scaling recipes to different serving sizes.
 *
 * <p>This class provides a static method to scale a recipe's ingredient amounts
 * proportionally while preserving all metadata (unit, name, note, prep_time, etc.).
 * It implements the same behavior as Python's scale_recipe function.
 *
 * <p>Example usage:
 * <pre>{@code
 * Recipe original = new Recipe(
 *     4,
 *     List.of(
 *         new Ingredient(2.0, "cups", "flour", null),
 *         new Ingredient(1.0, "tsp", "salt", null)
 *     )
 * );
 * Recipe scaled = RecipeScaler.scale(original, 8);
 * // scaled recipe now has 4 cups of flour and 2 tsp of salt
 * }</pre>
 */
public final class RecipeScaler {

    /**
     * Private constructor to prevent instantiation.
     */
    private RecipeScaler() {
        throw new AssertionError("RecipeScaler should not be instantiated");
    }

    /**
     * Scale a recipe to a different number of servings.
     *
     * <p>This method creates a new Recipe with ingredient amounts scaled proportionally
     * to the new serving count. All other recipe and ingredient properties are preserved
     * unchanged.
     *
     * <p>Scaling behavior:
     * <ul>
     *   <li>Calculates scale factor as: newServings / recipe.servings()</li>
     *   <li>Multiplies each ingredient's amount by the scale factor</li>
     *   <li>Preserves null amounts (e.g., "pinch of salt" without quantity)</li>
     *   <li>Preserves all ingredient metadata (unit, name, note)</li>
     *   <li>Preserves all recipe metadata (name, prep_time, additionalProperties)</li>
     * </ul>
     *
     * @param recipe the recipe to scale (must not be null)
     * @param newServings the target number of servings (must be positive)
     * @return a new Recipe instance with scaled ingredient amounts
     * @throws IllegalArgumentException if newServings is not positive
     * @throws NullPointerException if recipe is null
     */
    public static Recipe scale(Recipe recipe, int newServings) {
        if (newServings <= 0) {
            throw new IllegalArgumentException("New servings must be positive");
        }

        double scaleFactor = (double) newServings / recipe.servings();

        // Scale each ingredient using Stream API
        List<Ingredient> scaledIngredients = recipe.ingredients().stream()
            .map(ingredient -> {
                Double originalAmount = ingredient.amount();
                Double scaledAmount = originalAmount == null ? null : originalAmount * scaleFactor;

                return new Ingredient(
                    scaledAmount,
                    ingredient.unit(),
                    ingredient.name(),
                    ingredient.note()
                );
            })
            .toList();

        // Create new Recipe with scaled ingredients and preserved metadata
        return new Recipe(
            newServings,
            scaledIngredients,
            recipe.name(),
            recipe.prep_time(),
            recipe.additionalProperties()
        );
    }
}
