package com.lethimcook;

import java.util.List;

/**
 * Utility for scaling recipe ingredient amounts to a different number of servings.
 * <p>
 * Translates the Python {@code scale_recipe} function from {@code recipe.py}.
 * The scaling strategy multiplies each ingredient's amount by the ratio
 * {@code newServings / recipe.servings}, leaving null amounts unchanged
 * (e.g. "a pinch of salt" has no measurable amount and is preserved as-is).
 * All other ingredient and recipe properties are carried forward unmodified.
 */
public final class RecipeScaler {

    private RecipeScaler() {
        // utility class
    }

    /**
     * Scale a recipe to a different number of servings.
     * <p>
     * Computes a scale factor from {@code newServings / recipe.servings()} and
     * applies it to every ingredient that has a non-null amount. Ingredients
     * without an amount (null) are preserved unchanged. A new {@link Recipe}
     * instance is returned — the original is never mutated.
     *
     * @param recipe      the recipe to scale (must not be null)
     * @param newServings the target number of servings (must be &gt; 0)
     * @return a new {@link Recipe} with scaled ingredient amounts and the
     *         updated serving count; name, prepTime, and other metadata are preserved
     * @throws IllegalArgumentException if {@code newServings} is zero or negative
     */
    public static Recipe scale(Recipe recipe, int newServings) {
        if (newServings <= 0) {
            throw new IllegalArgumentException("New servings must be positive");
        }

        double scaleFactor = (double) newServings / recipe.servings();

        List<Ingredient> scaledIngredients = recipe.ingredients().stream()
                .map(ingredient -> scaleIngredient(ingredient, scaleFactor))
                .toList();

        return new Recipe(newServings, scaledIngredients, recipe.name(), recipe.prepTime());
    }

    /**
     * Scale a single ingredient's amount by the given factor.
     * <p>
     * If the ingredient's amount is {@code null}, the ingredient is returned
     * as-is (unmeasured ingredients like "a pinch of salt" are not scaled).
     * Otherwise, a new {@link Ingredient} is created with the scaled amount
     * and all other fields preserved.
     *
     * @param ingredient  the ingredient to scale
     * @param scaleFactor the multiplication factor
     * @return a new ingredient with the scaled amount, or the original if amount is null
     */
    private static Ingredient scaleIngredient(Ingredient ingredient, double scaleFactor) {
        if (ingredient.amount() == null) {
            return ingredient;
        }
        double scaledAmount = ingredient.amount() * scaleFactor;
        return new Ingredient(scaledAmount, ingredient.unit(), ingredient.name(), ingredient.note());
    }
}
