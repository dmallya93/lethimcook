package com.lethimcook.recipe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for recipe scaling functionality.
 * <p>
 * Ported from Python tests/test_recipe.py
 */
@DisplayName("Recipe Scaling")
class RecipeScalerTest {

    @Nested
    @DisplayName("Recipe Scaling Functionality")
    class RecipeScalingFunctionality {

        @Test
        @DisplayName("test_double_recipe - scales recipe from 4 to 8 servings")
        void testDoubleRecipe() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(2.0, "cups", "flour"),
                    new Ingredient(1.0, "tsp", "salt")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 8);

            assertThat(scaled.servings()).isEqualTo(8);
            assertThat(scaled.ingredients()).hasSize(2);
            assertThat(scaled.ingredients().get(0).amount()).isEqualTo(4.0);
            assertThat(scaled.ingredients().get(1).amount()).isEqualTo(2.0);
        }

        @Test
        @DisplayName("test_halve_recipe - scales recipe from 4 to 2 servings")
        void testHalveRecipe() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(2.0, "cups", "flour"),
                    new Ingredient(4.0, "tbsp", "butter")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 2);

            assertThat(scaled.servings()).isEqualTo(2);
            assertThat(scaled.ingredients().get(0).amount()).isEqualTo(1.0);
            assertThat(scaled.ingredients().get(1).amount()).isEqualTo(2.0);
        }

        @Test
        @DisplayName("test_scale_to_odd_number - scales recipe from 4 to 6 servings")
        void testScaleToOddNumber() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(2.0, "cups", "flour")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 6);

            assertThat(scaled.servings()).isEqualTo(6);
            assertThat(scaled.ingredients().get(0).amount()).isEqualTo(3.0);
        }

        @Test
        @DisplayName("test_fractional_scaling - scales recipe from 4 to 3 servings producing fractional amount")
        void testFractionalScaling() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(3.0, "cups", "flour")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 3);

            assertThat(scaled.servings()).isEqualTo(3);
            assertThat(scaled.ingredients().get(0).amount()).isNotNull();
            assertThat(scaled.ingredients().get(0).amount()).isCloseTo(2.25, within(0.01));
        }

        @Test
        @DisplayName("test_preserve_ingredient_properties - unit, name, and note are preserved")
        void testPreserveIngredientProperties() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(2.0, "cups", "flour", "all-purpose")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 8);

            assertThat(scaled.ingredients().get(0).unit()).isEqualTo("cups");
            assertThat(scaled.ingredients().get(0).name()).isEqualTo("flour");
            assertThat(scaled.ingredients().get(0).note()).isEqualTo("all-purpose");
        }

        @Test
        @DisplayName("test_ingredient_without_amount - ingredients without amount remain null")
        void testIngredientWithoutAmount() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(null, "pinch", "salt")
                )
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 8);

            assertThat(scaled.ingredients().get(0).amount()).isNull();
            assertThat(scaled.ingredients().get(0).unit()).isEqualTo("pinch");
            assertThat(scaled.ingredients().get(0).name()).isEqualTo("salt");
        }

        @Test
        @DisplayName("test_preserve_additional_recipe_fields - name and prep_time are preserved")
        void testPreserveAdditionalRecipeFields() {
            Recipe recipe = new Recipe(
                4,
                List.of(
                    new Ingredient(2.0, "cups", "flour")
                ),
                "Chocolate Chip Cookies",
                "15 minutes"
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 8);

            assertThat(scaled.name()).isEqualTo("Chocolate Chip Cookies");
            assertThat(scaled.prepTime()).isEqualTo("15 minutes");
        }

        @Test
        @DisplayName("test_empty_ingredients_list - handles empty ingredients list")
        void testEmptyIngredientsList() {
            Recipe recipe = new Recipe(
                4,
                new ArrayList<>()
            );
            Recipe scaled = RecipeScaler.scaleRecipe(recipe, 8);

            assertThat(scaled.servings()).isEqualTo(8);
            assertThat(scaled.ingredients()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Recipe Scaling Errors")
    class RecipeScalingErrors {

        @Test
        @DisplayName("test_negative_servings - throws error for negative new servings")
        void testNegativeServings() {
            Recipe recipe = new Recipe(
                4,
                new ArrayList<>()
            );

            assertThatThrownBy(() -> RecipeScaler.scaleRecipe(recipe, -2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
        }

        @Test
        @DisplayName("test_zero_servings - throws error for zero new servings")
        void testZeroServings() {
            Recipe recipe = new Recipe(
                4,
                new ArrayList<>()
            );

            assertThatThrownBy(() -> RecipeScaler.scaleRecipe(recipe, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
        }
    }
}
