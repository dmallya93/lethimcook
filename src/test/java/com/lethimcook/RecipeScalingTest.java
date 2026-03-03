package com.lethimcook;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test recipe scaling functionality.
 * Ported from Python {@code TestRecipeScaling} in {@code test_recipe.py}.
 */
class RecipeScalingTest {

    @Test
    void doubleRecipe() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(2.0, "cups", "flour"),
                        new Ingredient(1.0, "tsp", "salt")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 8);

        assertThat(scaled.servings()).isEqualTo(8);
        assertThat(scaled.ingredients()).hasSize(2);
        assertThat(scaled.ingredients().get(0).amount()).isEqualTo(4.0);
        assertThat(scaled.ingredients().get(1).amount()).isEqualTo(2.0);
    }

    @Test
    void halveRecipe() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(2.0, "cups", "flour"),
                        new Ingredient(4.0, "tbsp", "butter")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 2);

        assertThat(scaled.servings()).isEqualTo(2);
        assertThat(scaled.ingredients().get(0).amount()).isEqualTo(1.0);
        assertThat(scaled.ingredients().get(1).amount()).isEqualTo(2.0);
    }

    @Test
    void scaleToOddNumber() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(2.0, "cups", "flour")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 6);

        assertThat(scaled.servings()).isEqualTo(6);
        assertThat(scaled.ingredients().get(0).amount()).isEqualTo(3.0);
    }

    @Test
    void fractionalScaling() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(3.0, "cups", "flour")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 3);

        assertThat(scaled.servings()).isEqualTo(3);
        assertThat(scaled.ingredients().get(0).amount()).isNotNull();
        assertThat(scaled.ingredients().get(0).amount()).isCloseTo(2.25, within(0.01));
    }

    @Test
    void preserveIngredientProperties() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(2.0, "cups", "flour", "all-purpose")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 8);

        assertThat(scaled.ingredients().get(0).unit()).isEqualTo("cups");
        assertThat(scaled.ingredients().get(0).name()).isEqualTo("flour");
        assertThat(scaled.ingredients().get(0).note()).isEqualTo("all-purpose");
    }

    @Test
    void ingredientWithoutAmount() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(null, "pinch", "salt")
                )
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 8);

        assertThat(scaled.ingredients().get(0).unit()).isEqualTo("pinch");
        assertThat(scaled.ingredients().get(0).name()).isEqualTo("salt");
    }

    @Test
    void preserveAdditionalRecipeFields() {
        final Recipe recipe = new Recipe(
                4,
                List.of(
                        new Ingredient(2.0, "cups", "flour")
                ),
                "Chocolate Chip Cookies",
                "15 minutes"
        );
        final Recipe scaled = RecipeScaler.scale(recipe, 8);

        assertThat(scaled.name()).isEqualTo("Chocolate Chip Cookies");
        assertThat(scaled.prepTime()).isEqualTo("15 minutes");
    }

    @Test
    void emptyIngredientsList() {
        final Recipe recipe = new Recipe(4, List.of());
        final Recipe scaled = RecipeScaler.scale(recipe, 8);

        assertThat(scaled.servings()).isEqualTo(8);
        assertThat(scaled.ingredients()).isEmpty();
    }
}
