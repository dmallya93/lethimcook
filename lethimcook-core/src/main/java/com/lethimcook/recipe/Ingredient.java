package com.lethimcook.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * A recipe ingredient with optional amount and required unit and name.
 *
 * <p>This record mirrors the Python Pydantic model with Jakarta Bean Validation annotations.
 * The amount is nullable to support ingredients without specific measurements (e.g., "salt to taste").
 *
 * <p>Validation constraints:
 * <ul>
 *   <li>amount: Must be positive if present (nullable)</li>
 *   <li>unit: Required, non-blank</li>
 *   <li>name: Required, non-blank</li>
 *   <li>note: Optional</li>
 * </ul>
 *
 * @param amount The quantity of the ingredient (nullable for unmeasured ingredients)
 * @param unit The unit of measurement (required)
 * @param name The ingredient name (required)
 * @param note Optional additional note about the ingredient
 */
public record Ingredient(
    @Positive(message = "Amount must be positive")
    Double amount,

    @NotBlank(message = "Unit is required")
    String unit,

    @NotBlank(message = "Name is required")
    String name,

    String note
) {
    /**
     * Creates an Ingredient with all fields.
     * Note: Validation is not automatically triggered in the constructor.
     * Use RecipeValidator or factory methods for validated construction.
     */
    public Ingredient {
        // Compact constructor - no additional logic needed
        // Validation will be handled by RecipeValidator
    }

    /**
     * Creates an Ingredient without a note.
     */
    public Ingredient(Double amount, String unit, String name) {
        this(amount, unit, name, null);
    }
}
