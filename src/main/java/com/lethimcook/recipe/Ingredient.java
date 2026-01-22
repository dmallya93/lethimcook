package com.lethimcook.recipe;

import jakarta.validation.constraints.NotBlank;

/**
 * A recipe ingredient with optional amount and note.
 *
 * <p>This record represents an ingredient with its amount, unit, name, and optional note.
 * The amount and note fields are optional (nullable), while unit and name are required.
 *
 * <p>Validation is performed in two layers:
 * <ul>
 *   <li>Compact constructor validation for critical invariants (throws IllegalArgumentException)</li>
 *   <li>Bean Validation annotations for declarative constraints (used when explicitly validated)</li>
 * </ul>
 *
 * @param amount The amount of this ingredient (nullable for ingredients without quantities)
 * @param unit The unit of measurement (required, non-blank)
 * @param name The ingredient name (required, non-blank)
 * @param note Optional note about the ingredient (e.g., "all-purpose", "chopped")
 */
public record Ingredient(
    Double amount,
    @NotBlank(message = "Unit cannot be blank") String unit,
    @NotBlank(message = "Name cannot be blank") String name,
    String note
) {
    /**
     * Compact constructor that validates required fields.
     *
     * @throws IllegalArgumentException if unit or name is null or blank
     */
    public Ingredient {
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit cannot be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
