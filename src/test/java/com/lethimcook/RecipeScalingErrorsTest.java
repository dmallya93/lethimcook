package com.lethimcook;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test error handling in recipe scaling.
 * Ported from Python {@code TestRecipeScalingErrors} in {@code test_recipe.py}.
 */
class RecipeScalingErrorsTest {

    /**
     * Python equivalent: {@code Recipe.model_validate({"ingredients": [...]})}
     * without a servings field raises {@code ValidationError}.
     * <p>
     * In Java, an int field cannot be null/missing at construction time.
     * The closest equivalent is passing 0 (default int), which triggers the
     * servings-must-be-positive validation.
     */
    @Test
    void missingServings() {
        assertThatThrownBy(() -> new Recipe(
                0,
                List.of(new Ingredient(2.0, "cups", "flour"))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Python equivalent: {@code Recipe.model_validate({"servings": 4})}
     * without an ingredients field raises {@code ValidationError}.
     * <p>
     * In Java, passing null for the ingredients list triggers validation.
     */
    @Test
    void missingIngredients() {
        assertThatThrownBy(() -> new Recipe(
                4,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Python equivalent: {@code Recipe(servings=0, ingredients=[])}
     * raises {@code ValidationError} due to {@code Field(gt=0)}.
     */
    @Test
    void zeroServings() {
        assertThatThrownBy(() -> new Recipe(
                0,
                List.of()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Python equivalent: {@code scale_recipe(recipe, -2)}
     * raises {@code ValueError} with message containing "positive".
     */
    @Test
    void negativeNewServings() {
        final Recipe recipe = new Recipe(4, List.of());
        assertThatThrownBy(() -> RecipeScaler.scale(recipe, -2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }
}
