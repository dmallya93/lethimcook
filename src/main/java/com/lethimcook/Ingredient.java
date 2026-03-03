package com.lethimcook;

/**
 * A recipe ingredient with an optional amount, a required unit and name,
 * and an optional note.
 * <p>
 * Translates the Python {@code Ingredient} Pydantic model from {@code recipe.py}.
 * Validation is performed eagerly in the compact constructor, mirroring Pydantic's
 * construction-time validation: {@code unit} and {@code name} must be non-null and
 * non-empty. {@code amount} and {@code note} are nullable (an ingredient may not
 * have a measured amount, e.g. "a pinch of salt").
 *
 * @param amount the quantity of the ingredient (nullable; {@code null} means unmeasured)
 * @param unit   the measurement unit (e.g. "cups", "tsp", "pinch"); must not be null or empty
 * @param name   the ingredient name (e.g. "flour", "salt"); must not be null or empty
 * @param note   an optional note about the ingredient (e.g. "all-purpose"); may be null
 */
public record Ingredient(
        Double amount,
        String unit,
        String name,
        String note
) {

    /**
     * Compact constructor that validates required fields.
     * <p>
     * Throws {@link IllegalArgumentException} if {@code unit} or {@code name}
     * is null or empty, consistent with the Pydantic model's required-field
     * semantics and the error-handling conventions used throughout the library.
     */
    public Ingredient {
        if (unit == null || unit.isEmpty()) {
            throw new IllegalArgumentException("Ingredient unit must not be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ingredient name must not be null or empty");
        }
    }

    /**
     * Convenience constructor for ingredients with all common fields.
     *
     * @param amount the quantity (may be null)
     * @param unit   the measurement unit (required)
     * @param name   the ingredient name (required)
     */
    public Ingredient(final Double amount, final String unit, final String name) {
        this(amount, unit, name, null);
    }
}
