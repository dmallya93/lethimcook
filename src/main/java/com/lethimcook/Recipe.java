package com.lethimcook;

import java.util.List;

/**
 * A recipe with a serving count, a list of ingredients, and optional metadata.
 * <p>
 * Translates the Python {@code Recipe} Pydantic model from {@code recipe.py}.
 * Validation is performed eagerly in the compact constructor:
 * <ul>
 *   <li>{@code servings} must be strictly positive (greater than zero), mirroring
 *       the Pydantic {@code Field(gt=0)} constraint and the {@code servings_must_be_positive}
 *       field validator.</li>
 *   <li>{@code ingredients} must not be {@code null} (an empty list is allowed).</li>
 * </ul>
 * The {@code name} and {@code prepTime} fields are optional and may be {@code null}.
 * <p>
 * The ingredients list is defensively copied on construction and stored as an
 * unmodifiable list, preserving the immutability guarantee of Java records.
 *
 * @param servings    the number of servings this recipe yields; must be &gt; 0
 * @param ingredients the list of ingredients (must not be null; may be empty)
 * @param name        the recipe name (nullable)
 * @param prepTime    the preparation time description (nullable), e.g. "15 minutes"
 */
public record Recipe(
        int servings,
        List<Ingredient> ingredients,
        String name,
        String prepTime
) {

    /**
     * Compact constructor that validates required fields and defensively copies the
     * ingredients list.
     * <p>
     * Throws {@link IllegalArgumentException} if {@code servings} is zero or negative,
     * or if {@code ingredients} is null. This mirrors Pydantic's construction-time
     * validation and is consistent with the error-handling conventions used throughout
     * the library.
     */
    public Recipe {
        if (servings <= 0) {
            throw new IllegalArgumentException("Servings must be positive");
        }
        if (ingredients == null) {
            throw new IllegalArgumentException("Ingredients must not be null");
        }
        ingredients = List.copyOf(ingredients);
    }

    /**
     * Convenience constructor for recipes without optional metadata.
     *
     * @param servings    the number of servings (must be &gt; 0)
     * @param ingredients the list of ingredients (must not be null)
     */
    public Recipe(int servings, List<Ingredient> ingredients) {
        this(servings, ingredients, null, null);
    }
}
