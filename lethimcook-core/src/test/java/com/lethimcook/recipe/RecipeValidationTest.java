package com.lethimcook.recipe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Recipe and Ingredient validation.
 *
 * <p>These tests verify that Jakarta Bean Validation constraints are properly
 * enforced when using RecipeValidator, mirroring the Python Pydantic validation tests.
 */
@DisplayName("Recipe and Ingredient Validation")
class RecipeValidationTest {

    @Nested
    @DisplayName("Recipe Validation Tests")
    class RecipeValidationTests {

        @Test
        @DisplayName("Should reject Recipe with missing servings")
        void testMissingServings() {
            // Python test: test_missing_servings()
            // In Python, this is caught by Pydantic during model validation
            // In Java, we test with null servings
            List<Ingredient> ingredients = List.of(
                new Ingredient(2.0, "cups", "flour")
            );

            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(null, ingredients)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Servings is required");
        }

        @Test
        @DisplayName("Should reject Recipe with missing ingredients")
        void testMissingIngredients() {
            // Python test: test_missing_ingredients()
            // In Python, Pydantic raises ValidationError when ingredients field is missing
            // In Java, we test with null ingredients list
            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(4, null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ingredients list cannot be empty");
        }

        @Test
        @DisplayName("Should reject Recipe with zero servings")
        void testZeroServings() {
            // Python test: test_zero_servings()
            // servings must be positive (greater than zero)
            List<Ingredient> ingredients = new ArrayList<>();

            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(0, ingredients)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Servings must be positive");
        }

        @Test
        @DisplayName("Should reject Recipe with negative servings")
        void testNegativeServings() {
            // Python test: test_negative_servings() (in TestRecipeScalingErrors)
            // This tests the validation in Recipe model itself
            List<Ingredient> ingredients = new ArrayList<>();

            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(-2, ingredients)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Servings must be positive");
        }

        @Test
        @DisplayName("Should reject Recipe with empty ingredients list")
        void testEmptyIngredientsList() {
            // The @NotEmpty annotation should prevent empty lists
            // Note: Python allows empty lists, but the spec says @NotEmpty should be used
            List<Ingredient> emptyIngredients = new ArrayList<>();

            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(4, emptyIngredients)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ingredients list cannot be empty");
        }

        @Test
        @DisplayName("Should accept valid Recipe with all required fields")
        void testValidRecipe() {
            List<Ingredient> ingredients = List.of(
                new Ingredient(2.0, "cups", "flour"),
                new Ingredient(1.0, "tsp", "salt")
            );

            Recipe recipe = RecipeValidator.validate(new Recipe(4, ingredients));

            assertThat(recipe).isNotNull();
            assertThat(recipe.servings()).isEqualTo(4);
            assertThat(recipe.ingredients()).hasSize(2);
        }

        @Test
        @DisplayName("Should accept valid Recipe with optional fields")
        void testValidRecipeWithOptionalFields() {
            List<Ingredient> ingredients = List.of(
                new Ingredient(2.0, "cups", "flour")
            );

            Recipe recipe = RecipeValidator.validate(
                new Recipe(4, ingredients, "Chocolate Chip Cookies", "15 minutes")
            );

            assertThat(recipe).isNotNull();
            assertThat(recipe.name()).isEqualTo("Chocolate Chip Cookies");
            assertThat(recipe.prepTime()).isEqualTo("15 minutes");
        }

        @Test
        @DisplayName("Should reject Recipe with invalid ingredient")
        void testRecipeWithInvalidIngredient() {
            // Test cascading validation with @Valid annotation
            List<Ingredient> ingredients = List.of(
                new Ingredient(2.0, "", "flour")  // Empty unit should fail @NotBlank
            );

            assertThatThrownBy(() -> RecipeValidator.validate(new Recipe(4, ingredients)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit is required");
        }
    }

    @Nested
    @DisplayName("Ingredient Validation Tests")
    class IngredientValidationTests {

        @Test
        @DisplayName("Should reject Ingredient with blank unit")
        void testBlankUnit() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(2.0, "", "flour")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit is required");
        }

        @Test
        @DisplayName("Should reject Ingredient with null unit")
        void testNullUnit() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(2.0, null, "flour")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit is required");
        }

        @Test
        @DisplayName("Should reject Ingredient with blank name")
        void testBlankName() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(2.0, "cups", "")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name is required");
        }

        @Test
        @DisplayName("Should reject Ingredient with null name")
        void testNullName() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(2.0, "cups", null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name is required");
        }

        @Test
        @DisplayName("Should reject Ingredient with negative amount")
        void testNegativeAmount() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(-1.0, "cups", "flour")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
        }

        @Test
        @DisplayName("Should reject Ingredient with zero amount")
        void testZeroAmount() {
            assertThatThrownBy(() -> RecipeValidator.validate(new Ingredient(0.0, "cups", "flour")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
        }

        @Test
        @DisplayName("Should accept Ingredient with null amount")
        void testNullAmount() {
            // Ingredients without amounts are allowed (e.g., "salt to taste")
            Ingredient ingredient = RecipeValidator.validate(new Ingredient(null, "pinch", "salt"));

            assertThat(ingredient).isNotNull();
            assertThat(ingredient.amount()).isNull();
            assertThat(ingredient.unit()).isEqualTo("pinch");
            assertThat(ingredient.name()).isEqualTo("salt");
        }

        @Test
        @DisplayName("Should accept valid Ingredient with all fields")
        void testValidIngredientWithAllFields() {
            Ingredient ingredient = RecipeValidator.validate(
                new Ingredient(2.0, "cups", "flour", "all-purpose")
            );

            assertThat(ingredient).isNotNull();
            assertThat(ingredient.amount()).isEqualTo(2.0);
            assertThat(ingredient.unit()).isEqualTo("cups");
            assertThat(ingredient.name()).isEqualTo("flour");
            assertThat(ingredient.note()).isEqualTo("all-purpose");
        }

        @Test
        @DisplayName("Should accept valid Ingredient without note")
        void testValidIngredientWithoutNote() {
            Ingredient ingredient = RecipeValidator.validate(
                new Ingredient(2.0, "cups", "flour", null)
            );

            assertThat(ingredient).isNotNull();
            assertThat(ingredient.note()).isNull();
        }
    }

    @Nested
    @DisplayName("RecipeValidator Factory Methods")
    class RecipeValidatorFactoryMethodTests {

        @Test
        @DisplayName("Should create valid Recipe using factory method")
        void testCreateRecipeFactoryMethod() {
            List<Ingredient> ingredients = List.of(
                new Ingredient(2.0, "cups", "flour")
            );

            Recipe recipe = RecipeValidator.createRecipe(4, ingredients, "Test Recipe", "10 minutes");

            assertThat(recipe).isNotNull();
            assertThat(recipe.servings()).isEqualTo(4);
            assertThat(recipe.name()).isEqualTo("Test Recipe");
        }

        @Test
        @DisplayName("Should create valid Ingredient using factory method")
        void testCreateIngredientFactoryMethod() {
            Ingredient ingredient = RecipeValidator.createIngredient(2.0, "cups", "flour", "sifted");

            assertThat(ingredient).isNotNull();
            assertThat(ingredient.amount()).isEqualTo(2.0);
            assertThat(ingredient.note()).isEqualTo("sifted");
        }

        @Test
        @DisplayName("Should reject invalid Recipe using factory method")
        void testCreateRecipeFactoryMethodInvalid() {
            assertThatThrownBy(() -> RecipeValidator.createRecipe(0, new ArrayList<>(), null, null))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should reject invalid Ingredient using factory method")
        void testCreateIngredientFactoryMethodInvalid() {
            assertThatThrownBy(() -> RecipeValidator.createIngredient(-1.0, "cups", "flour", null))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
