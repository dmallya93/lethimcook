"""
Functional tests for the origin Python lethimcook.convert function.

These tests call lethimcook.convert directly via Python subprocess to get
baseline values used in origin_and_target comparisons.
"""

import subprocess
import pytest
import sys
import os

REPO_DIR = "/app/workspace/jobs/fb2d6547-a8f8-4713-9406-c81b79836e0a/workspace/lethimcook"


def run_python(code, timeout=30):
    """Run inline Python code with lethimcook installed."""
    result = subprocess.run(
        [sys.executable, "-c", code],
        cwd=REPO_DIR,
        capture_output=True,
        text=True,
        timeout=timeout,
        env={**os.environ, "PYTHONPATH": REPO_DIR},
    )
    return result


class TestVolumeConversions:
    """lethimcook.convert — volume conversions (origin baseline)."""

    def test_cups_to_ml(self):
        result = run_python("from lethimcook import convert; print(convert(2, 'cups', 'ml'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 473.176) < 0.01

    def test_tsp_to_tbsp(self):
        result = run_python("from lethimcook import convert; print(convert(3, 'tsp', 'tbsp'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 1.0) < 0.01

    def test_gallon_to_liter(self):
        result = run_python("from lethimcook import convert; print(convert(1, 'gallon', 'l'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 3.785) < 0.01

    def test_floz_to_ml(self):
        result = run_python("from lethimcook import convert; print(convert(8, 'fl oz', 'ml'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 236.588) < 0.01

    def test_same_unit_cup_to_cup(self):
        result = run_python("from lethimcook import convert; print(convert(5, 'cup', 'cup'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 5.0) < 0.001


class TestWeightConversions:
    """lethimcook.convert — weight conversions (origin baseline)."""

    def test_pounds_to_grams(self):
        result = run_python("from lethimcook import convert; print(convert(1, 'pound', 'g'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 453.592) < 0.01

    def test_oz_to_grams(self):
        result = run_python("from lethimcook import convert; print(convert(16, 'oz', 'g'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 453.592) < 0.01

    def test_kg_to_lbs(self):
        result = run_python("from lethimcook import convert; print(convert(1, 'kg', 'lb'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 2.205) < 0.01

    def test_grams_to_ounces(self):
        result = run_python("from lethimcook import convert; print(convert(100, 'g', 'oz'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 3.527) < 0.01


class TestTemperatureConversions:
    """lethimcook.convert — temperature conversions (origin baseline)."""

    def test_fahrenheit_to_celsius_freezing(self):
        result = run_python("from lethimcook import convert; print(convert(32, 'fahrenheit', 'celsius'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 0.0) < 0.01

    def test_fahrenheit_to_celsius_boiling(self):
        result = run_python("from lethimcook import convert; print(convert(212, 'f', 'c'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 100.0) < 0.01

    def test_fahrenheit_to_celsius_oven(self):
        result = run_python("from lethimcook import convert; print(convert(350, 'f', 'c'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 176.67) < 0.1

    def test_celsius_to_fahrenheit_freezing(self):
        result = run_python("from lethimcook import convert; print(convert(0, 'celsius', 'fahrenheit'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 32.0) < 0.01

    def test_celsius_to_fahrenheit_boiling(self):
        result = run_python("from lethimcook import convert; print(convert(100, 'c', 'f'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 212.0) < 0.01

    def test_celsius_to_kelvin(self):
        result = run_python("from lethimcook import convert; print(convert(0, 'celsius', 'kelvin'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 273.15) < 0.01

    def test_kelvin_to_celsius(self):
        result = run_python("from lethimcook import convert; print(convert(273.15, 'kelvin', 'celsius'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert abs(value - 0.0) < 0.01


class TestCountConversions:
    """lethimcook.convert — count conversions (origin baseline)."""

    def test_count_to_item(self):
        result = run_python("from lethimcook import convert; print(convert(5, 'count', 'item'))")
        assert result.returncode == 0
        value = float(result.stdout.strip())
        assert value == 5.0


class TestErrorHandling:
    """lethimcook.convert — error handling (origin baseline)."""

    def test_incompatible_units_cups_to_grams(self):
        code = """
from lethimcook import convert
try:
    convert(1, 'cups', 'grams')
    print('NO_ERROR')
except ValueError as e:
    print(f'ValueError: {e}')
"""
        result = run_python(code)
        assert result.returncode == 0
        assert "ValueError" in result.stdout
        assert "Cannot convert between" in result.stdout

    def test_unknown_unit_blorg(self):
        code = """
from lethimcook import convert
try:
    convert(1, 'blorg', 'ml')
    print('NO_ERROR')
except ValueError as e:
    print(f'ValueError: {e}')
"""
        result = run_python(code)
        assert result.returncode == 0
        assert "ValueError" in result.stdout
        assert "Unknown unit" in result.stdout

    def test_temperature_weight_incompatible(self):
        code = """
from lethimcook import convert
try:
    convert(100, 'celsius', 'grams')
    print('NO_ERROR')
except ValueError as e:
    print(f'ValueError: {e}')
"""
        result = run_python(code)
        assert result.returncode == 0
        assert "ValueError" in result.stdout
        assert "Cannot convert between" in result.stdout


class TestUnitVariations:
    """lethimcook.convert — unit aliases and case-insensitivity (origin baseline)."""

    def test_teaspoon_variations_equivalent(self):
        r1 = run_python("from lethimcook import convert; print(convert(1, 'tsp', 'ml'))")
        r2 = run_python("from lethimcook import convert; print(convert(1, 'teaspoon', 'ml'))")
        assert r1.returncode == 0 and r2.returncode == 0
        v1, v2 = float(r1.stdout.strip()), float(r2.stdout.strip())
        assert abs(v1 - v2) < 0.001

    def test_pound_variations_equivalent(self):
        r1 = run_python("from lethimcook import convert; print(convert(1, 'lb', 'g'))")
        r2 = run_python("from lethimcook import convert; print(convert(1, 'lbs', 'g'))")
        r3 = run_python("from lethimcook import convert; print(convert(1, 'pound', 'g'))")
        for r in (r1, r2, r3):
            assert r.returncode == 0
        v1, v2, v3 = float(r1.stdout.strip()), float(r2.stdout.strip()), float(r3.stdout.strip())
        assert abs(v1 - v2) < 0.001
        assert abs(v1 - v3) < 0.001

    def test_case_insensitive(self):
        r1 = run_python("from lethimcook import convert; print(convert(1, 'CUP', 'ML'))")
        r2 = run_python("from lethimcook import convert; print(convert(1, 'cup', 'ml'))")
        assert r1.returncode == 0 and r2.returncode == 0
        v1, v2 = float(r1.stdout.strip()), float(r2.stdout.strip())
        assert abs(v1 - v2) < 0.001
