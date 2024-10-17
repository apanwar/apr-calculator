package io.apanwar.apr.calculator.util.impl

import io.apanwar.apr.calculator.data.InputData
import io.apanwar.apr.calculator.util.APRCalculator

import static java.lang.Math.pow

abstract class AbstractAPRCalculator implements APRCalculator {

    @Override
    BigDecimal calculateMonthlyPayment(InputData inputData) {
        def referenceMonthlyRate = inputData.referenceAnnualRate / 12
        def monthlyPayment = inputData.principal * (referenceMonthlyRate * pow(1 + referenceMonthlyRate, inputData.periodInMonths)) / (pow(1 + referenceMonthlyRate, inputData.periodInMonths) - 1)
        return BigDecimal.valueOf(Math.ceil(monthlyPayment / 0.05) * 0.05)
    }

    @Override
    BigDecimal calculateAmortizedValue(InputData inputData, BigDecimal monthlyPayment) {
        return monthlyPayment * inputData.periodInMonths
    }

    @Override
    abstract BigDecimal calculateAPR(InputData inputData, BigDecimal monthlyPayment)

}
