package com.lethimcook.recipe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Recipe domain models (Ingredient and Recipe records).
 *
 * <p>These tests verify basic model construction and validation behavior,
 * ensuring that the compact constructor validation works correctly.
 */
@DisplayName("Recipe Domain Models")
class RecipeModelTest {

    @Nested
    @DisplayName("Ingredient")
    class IngredientTests {

        @Test
        @DisplayName("creates ingredient with all fields")
        void testCreateIngredientWithAllFields() {
            Ingredient ingredient = new Ingredient(2.0, "cups", "flour", "all-purpose");

            assertThat(ingredient.amount()).isEqualTo(2.0);
            assertThat(ingredient.unit()).isEqualTo("cups");
            assertThat(ingredient.name()).isEqualTo("flour");
            assertThat(ingredient.note()).isEqualTo("all-purpose");
        }

        @Test
        @DisplayName("creates ingredient without amount")
        void testCreateIngredientWithoutAmount() {
            Ingredient ingredient = new Ingredient(null, "pinch", "salt", null);

            assertThat(ingredient.amount()).isNull();
            assertThat(ingredient.unit()).isEqualTo("pinch");
            assertThat(ingredient.name()).isEqualTo("salt");
            assertThat(ingredient.note()).isNull();
        }

        @Test
        @DisplayName("throws exception for blank unit")
        void testBlankUnit() {
            assertThatThrownBy(() -> new Ingredient(2.0, "", "flour", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit cannot be blank");
        }

        @Test
        @DisplayName("throws exception for null unit")
        void testNullUnit() {
            assertThatThrownBy(() -> new Ingredient(2.0, null, "flour", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit cannot be blank");
        }

        @Test
        @DisplayName("throws exception for blank name")
        void testBlankName() {
            assertThatThrownBy(() -> new Ingredient(2.0, "cups", "", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be blank");
        }

        @Test
        @DisplayName("throws exception for null name")
        void testNullName() {
            assertThatThrownBy(() -> new Ingredient(2.0, "cups", null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be blank");
        }
    }

    @Nested
    @DisplayName("Recipe")
    class RecipeTests {

        @Test
        @DisplayName("creates recipe with all fields")
        void testCreateRecipeWithAllFields() {
            Ingredient ingredient = new Ingredient(2.0, "cups", "flour", null);
            Recipe recipe = new Recipe(
                4,
                List.of(ingredient),
                "Chocolate Chip Cookies",
                "15 minutes",
                null
            );

            assertThat(recipe.servings()).isEqualTo(4);
            assertThat(recipe.ingredients()).hasSize(1);
            assertThat(recipe.name()).isEqualTo("Chocolate Chip Cookies");
            assertThat(recipe.prep_time()).isEqualTo("15 minutes");
        }

        @Test
        @DisplayName("creates recipe with convenience constructor")
        void testConvenienceConstructor() {
            Ingredient ingredient = new Ingredient(2.0, "cups", "flour", null);
            Recipe recipe = new Recipe(4, List.of(ingredient));

            assertThat(recipe.servings()).isEqualTo(4);
            assertThat(recipe.ingredients()).hasSize(1);
            assertThat(recipe.name()).isNull();
            assertThat(recipe.prep_time()).isNull();
        }

        @Test
        @DisplayName("throws exception for empty ingredients list")
        void testEmptyIngredientsList() {
            // Per task specification, empty ingredients list should throw exception
            assertThatThrownBy(() -> new Recipe(4, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Recipe must have at least one ingredient");
        }

        @Test
        @DisplayName("throws exception for null ingredients")
        void testNullIngredients() {
            assertThatThrownBy(() -> new Recipe(4, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ingredients cannot be null");
        }

        @Test
        @DisplayName("throws exception for zero servings")
        void testZeroServings() {
            // Per test_recipe.py line 132-137, zero servings should throw ValidationError
            assertThatThrownBy(() -> new Recipe(0, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Servings must be positive");
        }

        @Test
        @DisplayName("throws exception for negative servings")
        void testNegativeServings() {
            assertThatThrownBy(() -> new Recipe(-1, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Servings must be positive");
        }
    }
}
