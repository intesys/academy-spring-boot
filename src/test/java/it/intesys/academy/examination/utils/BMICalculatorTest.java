package it.intesys.academy.examination.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class BMICalculatorTest {

    @Test
    @DisplayName("test bmi calculation")
    //1.95, 85 -> 22.35
    void getBMI() {
        double bmi = BMICalculator.calculateBMI(85d, 1.95d);
        assertThat(bmi).isEqualTo(22.35d);
    }

    @Test
    @DisplayName("Throws exeption when negative BMI is passed")
    void testNegativeBMI() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->BMICalculator.getBMICondition(-1));
    }

    @DisplayName("Calculates multiple BMI conditions")
    @ParameterizedTest(name = "a BMI of {0} is categorized as {1}")
    @CsvSource({ "12.2, UNDERWEIGHT", "20, NORMAL", "30, OBESE"})
    void testBmiCondition(double bmi, BMICalculator.BMICondition bmiCondition) {
        assertThat(BMICalculator.getBMICondition(bmi)).isEqualTo(bmiCondition);
    }

}
