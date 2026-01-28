package com.lethimcook;

import com.lethimcook.converter.UnitConverter;

/**
 * Main facade class for the LetHimCook library.
 * Provides a unified API for unit conversions, natural language parsing,
 * and recipe scaling operations.
 *
 * <p>This facade mirrors the Python __init__.py exports and provides a clean,
 * user-friendly API for the library. It serves as the primary entry point
 * for all library functionality and hides internal implementation details.</p>
 *
 * <p>The facade is stateless and thread-safe, suitable for use in concurrent
 * environments and as a Spring bean.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * LetHimCook converter = new LetHimCook();
 *
 * // Basic unit conversion
 * double result = converter.convert(2.0, "cups", "ml");
 * System.out.println("2 cups = " + result + " ml");
 *
 * // Future capabilities (Milestone 2+):
 * // String natural = converter.convertNatural("2 cups to ml");
 * // Recipe scaled = converter.scaleRecipe(recipe, 4);
 * }</pre>
 *
 * @since 0.1.0
 */
public class LetHimCook {

    /**
     * The unit converter instance used for all conversion operations.
     * This field is final to ensure thread-safety and immutability.
     */
    private final UnitConverter unitConverter;

    /**
     * Constructs a new LetHimCook facade with default configuration.
     * Creates a new UnitConverter instance internally.
     *
     * <p>This constructor is suitable for direct instantiation. In Spring Boot
     * applications, this class can also be managed as a bean.</p>
     */
    public LetHimCook() {
        this.unitConverter = new UnitConverter();
    }

    /**
     * Constructs a new LetHimCook facade with an injected UnitConverter.
     * This constructor supports dependency injection for enhanced testability
     * and flexibility.
     *
     * @param unitConverter the unit converter to use for conversions
     * @throws NullPointerException if unitConverter is null
     */
    public LetHimCook(UnitConverter unitConverter) {
        if (unitConverter == null) {
            throw new NullPointerException("UnitConverter cannot be null");
        }
        this.unitConverter = unitConverter;
    }

    /**
     * Converts a value from one unit to another.
     *
     * <p>This is the primary conversion method for the library. It supports
     * conversions between volume units (cups, ml, liters, etc.), weight units
     * (grams, ounces, pounds, etc.), temperature units (Celsius, Fahrenheit,
     * Kelvin), and count units (units, pieces, etc.).</p>
     *
     * <p>Units are case-insensitive and support common aliases. For example,
     * "cup", "cups", "c" all refer to the same volume unit.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * LetHimCook converter = new LetHimCook();
     *
     * // Volume conversion
     * double ml = converter.convert(2.0, "cups", "ml");  // 473.176 ml
     *
     * // Weight conversion
     * double grams = converter.convert(1.0, "lb", "g");   // 453.592 g
     *
     * // Temperature conversion
     * double fahrenheit = converter.convert(100.0, "c", "f");  // 212.0 F
     * }</pre>
     *
     * @param value the numeric value to convert (must be finite)
     * @param fromUnit the source unit (e.g., "cups", "g", "celsius")
     * @param toUnit the target unit (e.g., "ml", "oz", "fahrenheit")
     * @return the converted value
     * @throws IllegalArgumentException if units are incompatible (e.g., converting
     *         volume to weight), if units are unknown, or if the value is infinite
     *         or NaN
     * @since 0.1.0
     */
    public double convert(double value, String fromUnit, String toUnit) {
        return unitConverter.convert(value, fromUnit, toUnit);
    }

    /**
     * Converts units using natural language query parsing.
     *
     * <p><strong>Note:</strong> This method is a placeholder and will be
     * implemented in Milestone 2 (Natural Language Parsing). Currently,
     * calling this method will throw an UnsupportedOperationException.</p>
     *
     * <p>Future functionality will support queries like:</p>
     * <ul>
     *   <li>"2 cups to ml"</li>
     *   <li>"1.5 kg in grams"</li>
     *   <li>"convert 100 celsius to fahrenheit"</li>
     * </ul>
     *
     * <p>Example usage (after Milestone 2 implementation):</p>
     * <pre>{@code
     * LetHimCook converter = new LetHimCook();
     * String result = converter.convertNatural("2 cups to ml");
     * // Returns: "2.0 cups = 473.176 ml"
     * }</pre>
     *
     * @param query the natural language conversion query
     * @return a formatted string with the conversion result
     * @throws UnsupportedOperationException always, until Milestone 2 is implemented
     * @throws IllegalArgumentException (future) if the query cannot be parsed or
     *         contains invalid units
     * @see #convert(double, String, String)
     * @since 0.1.0 (placeholder)
     */
    public String convertNatural(String query) {
        throw new UnsupportedOperationException(
            "Natural language conversion will be implemented in Milestone 2. " +
            "Use convert(double, String, String) for direct unit conversion."
        );
    }

    /**
     * Scales a recipe to a different number of servings.
     *
     * <p><strong>Note:</strong> This method is a placeholder and will be
     * implemented in Milestone 3 (Recipe Scaling). Currently, calling this
     * method will throw an UnsupportedOperationException.</p>
     *
     * <p>Future functionality will scale all ingredient amounts proportionally
     * while preserving recipe structure and metadata.</p>
     *
     * <p>Example usage (after Milestone 3 implementation):</p>
     * <pre>{@code
     * LetHimCook converter = new LetHimCook();
     * Recipe original = new Recipe(2, List.of(
     *     new Ingredient(1.0, "cup", "flour", null)
     * ), "Cookies", "15 minutes");
     *
     * Recipe scaled = converter.scaleRecipe(original, 4);
     * // Scaled recipe will have 2.0 cups of flour for 4 servings
     * }</pre>
     *
     * @param recipe the original recipe to scale
     * @param targetServings the desired number of servings (must be positive)
     * @return a new Recipe instance with scaled ingredient amounts
     * @throws UnsupportedOperationException always, until Milestone 3 is implemented
     * @throws IllegalArgumentException (future) if targetServings is not positive
     * @throws NullPointerException (future) if recipe is null
     * @since 0.1.0 (placeholder)
     */
    public Object scaleRecipe(Object recipe, int targetServings) {
        throw new UnsupportedOperationException(
            "Recipe scaling will be implemented in Milestone 3. " +
            "Recipe and Ingredient models are not yet available."
        );
    }
}
