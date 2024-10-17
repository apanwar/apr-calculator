package io.apanwar.apr.calculator.util.impl

import io.apanwar.apr.calculator.data.InputData
import org.apache.poi.ss.formula.functions.Irr

import static java.lang.Math.pow

class ApachePOIAPRCalculator extends AbstractAPRCalculator {

    private static final BigDecimal GUESS = 1e-3


    @Override
    BigDecimal calculateAPR(InputData inputData, BigDecimal monthlyPayment) {
        double[] cashFlows = [0 - inputData.principal.doubleValue()] + (1..inputData.periodInMonths).collect { monthlyPayment.doubleValue() }

        def irr = Irr.irr(cashFlows, GUESS)

        return (pow(1 + irr, 12) - 1) * 100
    }


}
