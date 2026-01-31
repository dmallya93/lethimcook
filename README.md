# LetHimCook - Java Unit Conversion Library

A Java library for cooking-related unit conversions, natural language parsing of conversion queries, and recipe scaling.

## Overview

LetHimCook is a modern Java 17+ library that provides:

- **Unit Conversion**: Convert between volume, weight, and temperature units
- **Natural Language Parsing**: Express conversion queries in plain English
- **Recipe Scaling**: Adjust ingredient quantities for different serving sizes (coming soon)
- **CLI Interface**: Interactive command-line tool for conversions

This is a Java migration of the [original Python lethimcook library](https://github.com/dmallya93/lethimcook).

## Project Structure

The project is organized as a Maven multi-module project:

```
lethimcook/
├── lethimcook-core/     # Core conversion library
└── lethimcook-cli/      # Command-line interface
```

## Requirements

- Java 17 or higher
- Maven 3.9+

## Building

Build all modules:

```bash
mvn clean install
```

Run tests:

```bash
mvn test
```

## Usage

### Core Library

```java
import com.lethimcook.LetHimCook;

LetHimCook converter = new LetHimCook();

// Direct conversion
double result = converter.convert(2.0, "cups", "ml");
System.out.println(result); // 473.176

// Natural language conversion
String result = converter.convertNatural("2 cups to ml");
System.out.println(result); // "2 cups = 473.18 ml"
```

### Natural Language Patterns

The library supports multiple natural language patterns:

- Direct conversion: `"2 cups to ml"`
- Imperative form: `"convert 1 pound to grams"`
- Question form: `"how many ml in 3 teaspoons"`
- Temperature: `"350 fahrenheit to celsius"`

### Command-Line Interface

Run the interactive shell:

```bash
cd lethimcook-cli
mvn spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar lethimcook-cli/target/lethimcook-cli-0.1.0.jar
```

Use the `convert` command:

```
shell:>convert "2 cups to ml"
2 cups = 473.18 ml
```

## Supported Units

### Volume
- teaspoons (tsp), tablespoons (tbsp), fluid ounces (fl oz)
- cups, pints, quarts, gallons
- milliliters (ml), liters (l)

### Weight
- ounces (oz), pounds (lb, lbs)
- grams (g), kilograms (kg)

### Temperature
- Fahrenheit (f), Celsius (c), Kelvin (k)

### Count
- count, item, piece, whole

## Design Decisions

### Regex Pattern Management
Pre-compiled Pattern objects are used as static final fields for optimal performance. Patterns are matched sequentially as in the original Python implementation.

### Number Formatting
Integer values are formatted without decimal places (e.g., "5" instead of "5.0") to match Python behavior and provide cleaner output.

### Result String Templates
String.format is used directly inline to maintain consistency with Python f-strings and ensure test compatibility.

## Testing

The project uses JUnit 5 with AssertJ for testing. All 10 tests from the original Python test suite have been migrated and pass successfully:

- 7 pattern tests (basic, convert, how many, decimal, temperature, case-insensitive, multi-word)
- 3 error tests (unparseable, missing value, invalid unit)

Run tests:

```bash
mvn test
```

## License

This project is a migration of the original Python lethimcook library.

## Development Status

**Current Milestone**: Milestone 2 - Natural Language Parsing

✅ Completed:
- Maven project structure
- Unit conversion core (Milestone 1)
- Natural language parsing (Milestone 2, Task 1)
- Basic CLI application

🔄 In Progress:
- Recipe models and scaling (Milestone 3)
- Full CLI features (Milestone 4)
