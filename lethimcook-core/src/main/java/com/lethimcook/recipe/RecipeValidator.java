package com.lethimcook.recipe;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validation utility for Recipe and Ingredient models.
 *
 * <p>This class provides a central point for triggering Bean Validation
 * without requiring Spring Boot dependency in the core module. It wraps
 * the Jakarta Bean Validation API and provides consistent error formatting.
 *
 * <p>Validation errors are thrown as {@link IllegalArgumentException} to
 * match the Python Pydantic behavior where validation errors are raised
 * as ValueError on model construction.
 */
public final class RecipeValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private RecipeValidator() {
        // Utility class - prevent instantiation
    }

    /**
     * Validates a Recipe instance and throws if constraints are violated.
     *
     * @param recipe The recipe to validate
     * @return The validated recipe (for fluent usage)
     * @throws IllegalArgumentException if validation constraints are violated
     */
    public static Recipe validate(Recipe recipe) {
        Set<ConstraintViolation<Recipe>> violations = VALIDATOR.validate(recipe);
        if (!violations.isEmpty()) {
            String message = formatViolations(violations);
            throw new IllegalArgumentException(message);
        }
        return recipe;
    }

    /**
     * Validates an Ingredient instance and throws if constraints are violated.
     *
     * @param ingredient The ingredient to validate
     * @return The validated ingredient (for fluent usage)
     * @throws IllegalArgumentException if validation constraints are violated
     */
    public static Ingredient validate(Ingredient ingredient) {
        Set<ConstraintViolation<Ingredient>> violations = VALIDATOR.validate(ingredient);
        if (!violations.isEmpty()) {
            String message = formatViolations(violations);
            throw new IllegalArgumentException(message);
        }
        return ingredient;
    }

    /**
     * Creates and validates a Recipe.
     *
     * @param servings The number of servings
     * @param ingredients The list of ingredients
     * @param name Optional recipe name
     * @param prepTime Optional preparation time
     * @return The validated recipe
     * @throws IllegalArgumentException if validation constraints are violated
     */
    public static Recipe createRecipe(Integer servings, java.util.List<Ingredient> ingredients,
                                     String name, String prepTime) {
        Recipe recipe = new Recipe(servings, ingredients, name, prepTime);
        return validate(recipe);
    }

    /**
     * Creates and validates an Ingredient.
     *
     * @param amount The quantity (nullable)
     * @param unit The unit of measurement
     * @param name The ingredient name
     * @param note Optional note
     * @return The validated ingredient
     * @throws IllegalArgumentException if validation constraints are violated
     */
    public static Ingredient createIngredient(Double amount, String unit, String name, String note) {
        Ingredient ingredient = new Ingredient(amount, unit, name, note);
        return validate(ingredient);
    }

    /**
     * Formats constraint violations into a human-readable error message.
     *
     * @param violations The set of constraint violations
     * @return A formatted error message
     */
    private static <T> String formatViolations(Set<ConstraintViolation<T>> violations) {
        if (violations.size() == 1) {
            return violations.iterator().next().getMessage();
        }
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
    }
}
