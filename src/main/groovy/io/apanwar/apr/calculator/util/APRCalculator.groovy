package io.apanwar.apr.calculator.util

import io.apanwar.apr.calculator.data.InputData

interface APRCalculator {

    BigDecimal calculateMonthlyPayment(InputData inputData)

    BigDecimal calculateAmortizedValue(InputData inputData, BigDecimal monthlyPayment)

    BigDecimal calculateAPR(InputData inputData, BigDecimal monthlyPayment)

}
