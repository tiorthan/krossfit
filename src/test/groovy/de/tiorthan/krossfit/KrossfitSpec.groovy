package de.tiorthan.krossfit

import de.tiorthan.krossfit.test.TestClassWithSuspendMethods
import kotlin.Unit
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class KrossfitSpec extends Specification {

    TestClassWithSuspendMethods testClass
    def callBackCondition = new AsyncConditions()
    String expectedValue

    def setup() {
        testClass = Krossfit.wrapFunctionCalls(new TestClassWithSuspendMethods({ callValue ->
            callBackCondition.evaluate {
                assert callValue == expectedValue
            }
        }))
    }

    def "no arguments no return called correctly"() {
        given:
        expectedValue = "noArgsReturnsUnit"

        when:
        def result = testClass.noArgsReturnsUnit()

        then:
        result == Unit.INSTANCE
        callBackCondition.await()
    }

    def "calling a non-suspending function should work as well"() {
        given:
        expectedValue = "noArgsNonSuspendFunction"

        when:
        def result = testClass.noArgsNonSuspendFunction()

        then:
        result == Unit.INSTANCE
        callBackCondition.await()
    }

    def "calling a function with multiple arguments"() {
        given:
        expectedValue = "first: $first, second: $second, third: $third"

        when:
        def result = testClass.ambiguousFunctionName(first, second, third)

        then:
        result == third
        callBackCondition.await()

        where:
        first | second | third
        "foo" | 1      | 50
        "bar" | -134   | "foobar"
    }

    def "calling an ambiguous function should call the correct function"() {
        given:
        expectedValue = "arg: $intVal"

        when:
        def result = testClass.ambiguousFunctionName(intVal)

        then:
        result == "foo"
        callBackCondition.await()

        where:
        intVal << [1, -500, 12345]
    }

    def "calling overloaded functions should call the correct overload"() {
        given:
        expectedValue = expectedCallback

        when:
        def result = testClass.ambiguousFunctionName(first, second, third)

        then:
        result == expectedResult
        callBackCondition.await()

        where:
        first | second | third    | expectedResult | expectedCallback
        "foo" | 1      | 50       | 50             | "first: foo, second: 1, third: 50"
        1     | 2      | 3        | Unit.INSTANCE  | "overloaded 1 2 3"
        "bar" | -134   | "foobar" | "foobar"       | "first: bar, second: -134, third: foobar"
        5L    | "foo"  | "bar"    | 10             | "another overload foo"
    }
}
