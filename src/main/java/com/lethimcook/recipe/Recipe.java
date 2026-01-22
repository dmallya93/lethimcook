package com.lethimcook.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

/**
 * A recipe with servings, ingredients, and optional metadata.
 *
 * <p>This record represents a recipe with its servings count, list of ingredients,
 * and optional fields like name and prep_time. The additionalProperties map allows
 * storing arbitrary extra fields, mirroring Pydantic's extra="allow" behavior.
 *
 * <p>Validation is performed in two layers:
 * <ul>
 *   <li>Compact constructor validation for critical invariants (throws IllegalArgumentException)</li>
 *   <li>Bean Validation annotations for declarative constraints (used when explicitly validated)</li>
 * </ul>
 *
 * @param servings The number of servings (must be positive)
 * @param ingredients The list of ingredients (required, must be non-null, validated recursively)
 * @param name Optional recipe name
 * @param prep_time Optional preparation time
 * @param additionalProperties Optional map for storing extra fields (mirrors Pydantic's extra="allow")
 */
public record Recipe(
    @Positive(message = "Servings must be positive") int servings,
    @NotNull(message = "Ingredients cannot be null") @Valid List<Ingredient> ingredients,
    String name,
    String prep_time,
    Map<String, Object> additionalProperties
) {
    /**
     * Compact constructor that validates required fields.
     *
     * <p>Validation mirrors Python's Pydantic behavior where Field(gt=0) and field_validator
     * enforce constraints at construction time. The @Positive annotation provides additional
     * declarative validation for Bean Validation contexts.
     *
     * @throws IllegalArgumentException if servings is not positive or if ingredients is null
     */
    public Recipe {
        if (servings <= 0) {
            throw new IllegalArgumentException("Servings must be positive");
        }
        if (ingredients == null) {
            throw new IllegalArgumentException("Ingredients cannot be null");
        }
        // Note: The Python code allows empty ingredients list (test_empty_ingredients_list
        // in test_recipe.py lines 104-112). We only check for null, not empty.
    }

    /**
     * Convenience constructor for recipes without additional properties.
     */
    public Recipe(int servings, List<Ingredient> ingredients, String name, String prep_time) {
        this(servings, ingredients, name, prep_time, null);
    }

    /**
     * Convenience constructor for recipes with only servings and ingredients.
     */
    public Recipe(int servings, List<Ingredient> ingredients) {
        this(servings, ingredients, null, null, null);
    }
}
