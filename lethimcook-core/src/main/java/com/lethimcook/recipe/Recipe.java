package com.lethimcook.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * A recipe with servings and ingredients.
 *
 * <p>This record mirrors the Python Pydantic model with Jakarta Bean Validation annotations.
 *
 * <p>Validation constraints:
 * <ul>
 *   <li>servings: Required, must be positive (greater than zero)</li>
 *   <li>ingredients: Required, non-empty list, each ingredient validated recursively</li>
 *   <li>name: Optional recipe name</li>
 *   <li>prepTime: Optional preparation time description</li>
 * </ul>
 *
 * @param servings The number of servings this recipe makes (must be positive)
 * @param ingredients The list of ingredients (must not be empty)
 * @param name Optional recipe name
 * @param prepTime Optional preparation time description
 */
public record Recipe(
    @NotNull(message = "Servings is required")
    @Positive(message = "Servings must be positive")
    Integer servings,

    @NotEmpty(message = "Ingredients list cannot be empty")
    @Valid
    List<Ingredient> ingredients,

    String name,

    String prepTime
) {
    /**
     * Creates a Recipe with all fields.
     * Note: Validation is not automatically triggered in the constructor.
     * Use RecipeValidator or factory methods for validated construction.
     */
    public Recipe {
        // Compact constructor - no additional logic needed
        // Validation will be handled by RecipeValidator
    }

    /**
     * Creates a Recipe without optional name and prepTime.
     */
    public Recipe(Integer servings, List<Ingredient> ingredients) {
        this(servings, ingredients, null, null);
    }

    /**
     * Creates a Recipe with name but without prepTime.
     */
    public Recipe(Integer servings, List<Ingredient> ingredients, String name) {
        this(servings, ingredients, name, null);
    }
}
