package io.apanwar.apr.calculator.util.impl

import io.apanwar.apr.calculator.data.InputData

import static java.lang.Math.pow

class UniCreditAPRCalculator extends AbstractAPRCalculator {

    @Override
    BigDecimal calculateAPR(InputData inputData, BigDecimal monthlyPayment) {
        try {
            final BigDecimal[] values = new BigDecimal[inputData.periodInMonths + 1]

            values[0] = (-1) * inputData.principal.doubleValue()
            for (int i = 1; i < values.length; i++) {
                if (inputData.periodInMonths >= i) {
                    values[i] = monthlyPayment
                } else {
                    break
                }
            }

            final BigDecimal inc = 0.000001 //0.000001
            BigDecimal guess = 0.005 //0.005
            BigDecimal NPV
            do {
                guess += inc
                NPV = 0
                for (int j = 0; j < values.length; j++) {
                    NPV += values[j] / pow((1 + guess), j)
                }

            }
            while (NPV > 0)

            return (pow(1 + guess, 12) - 1) * 100
        }
        catch (final Exception ignored) {
            return 0.0
        }
    }
}
