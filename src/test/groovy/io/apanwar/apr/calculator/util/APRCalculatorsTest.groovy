package io.apanwar.apr.calculator.util

import io.apanwar.apr.calculator.data.InputData
import io.apanwar.apr.calculator.util.impl.ApachePOIAPRCalculator
import io.apanwar.apr.calculator.util.impl.BisectMethodAPRCalculator
import io.apanwar.apr.calculator.util.impl.UniCreditAPRCalculator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.math.RoundingMode

class APRCalculatorsTest {

    private APRCalculator uniCreditCalculator

    private APRCalculator bisectMethodCalculator

    private APRCalculator apachePOICalculator

    @BeforeEach
    void before() {
        uniCreditCalculator = new UniCreditAPRCalculator()
        bisectMethodCalculator = new BisectMethodAPRCalculator()
        apachePOICalculator = new ApachePOIAPRCalculator()
    }

    @Test
    void compareCalculators() {
        int[] periods = [6, 9, 12, 15, 18, 24, 36]
        def principal = 299.99
        def referenceAnnualRate = 0.21

        periods.each {
            def inputData = new InputData()
            inputData.principal = principal
            inputData.periodInMonths = it
            inputData.referenceAnnualRate = referenceAnnualRate

            def monthlyPayment = uniCreditCalculator.calculateMonthlyPayment(inputData).setScale(2, RoundingMode.HALF_UP)
            def amortizationValue = uniCreditCalculator.calculateAmortizedValue(inputData, monthlyPayment).setScale(2, RoundingMode.HALF_UP)

            def startTime = System.currentTimeMillis()
            def uniCreditAPR = uniCreditCalculator.calculateAPR(inputData, monthlyPayment).setScale(2, RoundingMode.HALF_UP)
            def endTime = System.currentTimeMillis()
            def uniCreditExecutionTime = endTime - startTime

            assert uniCreditAPR > 0

            startTime = System.currentTimeMillis()
            def bisectMethodAPR = bisectMethodCalculator.calculateAPR(inputData, monthlyPayment).setScale(2, RoundingMode.HALF_UP)
            endTime = System.currentTimeMillis()
            def bisectMethodExecutionTime = endTime - startTime

            assert bisectMethodAPR > 0

            startTime = System.currentTimeMillis()
            def apachePOIAPR = apachePOICalculator.calculateAPR(inputData, monthlyPayment).setScale(2, RoundingMode.HALF_UP)
            endTime = System.currentTimeMillis()
            def apachePOIExecutionTime = endTime - startTime

            //assert apachePOIAPR > 0

            println("Principal: ${principal}, Amortization Value: ${amortizationValue}, Period: ${it} months, Monthly Payment: ${monthlyPayment} :")
            println("\t- UniCredit APR: ${uniCreditAPR}%, UniCredit Time: ${uniCreditExecutionTime} ms")
            println("\t- Bisect Method APR: ${bisectMethodAPR}%, Bisect Method Time: ${bisectMethodExecutionTime} ms")
            println("\t- Apache POI APR: ${apachePOIAPR}%, Apache POI Time: ${apachePOIExecutionTime} ms")
            println("----------------------------------------------------------------------------------------")

        }

    }
}
