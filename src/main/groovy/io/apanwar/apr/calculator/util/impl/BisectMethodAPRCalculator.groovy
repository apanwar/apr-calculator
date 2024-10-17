package io.apanwar.apr.calculator.util.impl

import io.apanwar.apr.calculator.data.InputData

import static java.lang.Math.pow

class BisectMethodAPRCalculator extends AbstractAPRCalculator {

    private static final BigDecimal EPSILON = 1e-6

    @Override
    BigDecimal calculateAPR(InputData inputData, BigDecimal monthlyPayment) {
        BigDecimal low = 0.0 // Assuming a minimum monthly interest rate of 10% (which is a low assumption)
        BigDecimal high = 1.0  // Assuming a maximum monthly interest rate of 50% (which is a high assumption)
        BigDecimal guessRate = 0.0

        def numberOfMonths = inputData.periodInMonths
        def fv = monthlyPayment * numberOfMonths
        def calculatedFV

        while ((high - low) > EPSILON) {
            guessRate = (low + high) / 2
            def estimatedMonthlyPayment = calculateMonthlyPayment(inputData.principal, guessRate / 12, numberOfMonths)
            calculatedFV = estimatedMonthlyPayment * numberOfMonths

            // If the estimated payment is too high, the rate is too high
            if (calculatedFV > fv) {
                high = guessRate
            } else {
                low = guessRate
            }
        }

        return (pow(1 + guessRate / 12, 12) - 1) * 100
    }

    private static BigDecimal calculateMonthlyPayment(BigDecimal price, BigDecimal monthlyRate, int numberOfMonths) {

        if (monthlyRate == 0) {
            return price / numberOfMonths  // If there's no interest rate, it's just a simple division
        }

        return price * ((monthlyRate * pow(1 + monthlyRate, numberOfMonths)) / (pow(1 + monthlyRate, numberOfMonths) - 1))
    }

}
