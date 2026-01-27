# LetHimCook - Java Unit Conversion Library

A lightweight Java 21 library for unit conversions, especially for cooking. This is a Java translation of the original Python [lethimcook](https://github.com/dmallya93/lethimcook.git) library.

## Features

- **Unit Conversions**: Convert between volume, weight, temperature, and count units
- **Natural Language Support**: (Coming in Milestone 2) Parse conversions in natural language
- **Recipe Scaling**: (Coming in Milestone 3) Scale recipes by adjusting ingredient quantities
- **Command-line Interface**: Easy-to-use CLI for quick conversions
- **Lightweight**: Minimal dependencies, fast startup

## Requirements

- Java 21 or higher
- Maven 3.9+ (for building)

## Installation

Clone and build the project:

```bash
git clone <repository-url>
cd lethimcook
mvn clean package
```

This creates an executable JAR at `target/lethimcook.jar`.

## Usage

### Command Line Interface

The CLI accepts conversions in a simple format:

```bash
java -jar target/lethimcook.jar <value> <from_unit> <to_unit>
```

#### Examples

```bash
# Volume conversions
java -jar target/lethimcook.jar 2 cups ml
# Output: 473.176

# Weight conversions
java -jar target/lethimcook.jar 1 pound grams
# Output: 453.592

# Temperature conversions
java -jar target/lethimcook.jar 100 celsius fahrenheit
# Output: 212.0

# Using unit aliases
java -jar target/lethimcook.jar 1 tsp ml
# Output: 4.92892
```

#### Help and Version

```bash
# Display help
java -jar target/lethimcook.jar --help

# Display version
java -jar target/lethimcook.jar --version
```

### Supported Units

**Volume**: tsp, teaspoon, tbsp, tablespoon, fl oz, fluid ounce, cup, pint, quart, gallon, ml, milliliter, liter

**Weight**: oz, ounce, pound, lb, gram, g, kilogram, kg

**Temperature**: fahrenheit, f, celsius, c, kelvin, k

**Count**: item, piece, unit, count

### As a Library

```java
import io.github.lethimcook.converter.Converter;

public class Example {
    public static void main(String[] args) {
        // Convert 2 cups to milliliters
        double result = Converter.convert(2, "cups", "ml");
        System.out.println(result); // 473.176

        // Convert temperature
        double temp = Converter.convert(100, "celsius", "fahrenheit");
        System.out.println(temp); // 212.0
    }
}
```

## Building and Testing

```bash
# Run all tests
mvn test

# Build the executable JAR
mvn clean package

# Generate Javadoc
mvn javadoc:javadoc
```

## Project Structure

```
src/
├── main/java/io/github/lethimcook/
│   ├── cli/           # Command-line interface
│   ├── converter/     # Core conversion logic
│   ├── units/         # Unit definitions and constants
│   ├── natural/       # (Coming) Natural language parser
│   └── recipe/        # (Coming) Recipe modeling and scaling
└── test/java/io/github/lethimcook/
    ├── cli/           # CLI tests
    ├── converter/     # Converter tests
    └── units/         # Units tests
```

## Development Status

### Milestone 1: Foundation ✅
- [x] Unit definitions and constants
- [x] Core conversion logic
- [x] Command-line interface with simplified parser
- [x] Comprehensive test coverage

### Milestone 2: Natural Language (In Progress)
- [ ] Natural language query parser
- [ ] Pattern matching for various query formats
- [ ] Integration with CLI

### Milestone 3: Recipe Scaling (Planned)
- [ ] Recipe and Ingredient models
- [ ] Bean validation
- [ ] Recipe scaling logic

## License

See original project at https://github.com/dmallya93/lethimcook.git

## Credits

Java translation by the lethimcook modernization team.
Original Python version by https://github.com/dmallya93/lethimcook.git

Created: Tue Jan 27 16:52:44 UTC 2026
