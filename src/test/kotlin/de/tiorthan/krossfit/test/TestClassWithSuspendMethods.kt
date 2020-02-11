package de.tiorthan.krossfit.test

import kotlinx.coroutines.delay

class TestClassWithSuspendMethods(val callback: (String) -> Unit) {

    suspend fun noArgsReturnsUnit() {
        delay(1)
        callback("noArgsReturnsUnit")
    }

    fun noArgsNonSuspendFunction() {
        callback("noArgsNonSuspendFunction")
    }

    suspend fun ambiguousFunctionName(first: String, second: Int = 50, third: Any?): Any? {
        delay(1)
        callback("first: $first, second: $second, third: $third")
        return third
    }

    fun ambiguousFunctionName(arg: Int): String {
        callback("arg: $arg")
        return "foo"
    }

    fun ambiguousFunctionName(first: Long, second: String, third: String): Long {
        callback("another overload $second")
        return 2 * first
    }

    suspend fun ambiguousFunctionName(first: Int, second: Int, third: Int) {
        delay(1)
        callback("overloaded $first $second $third")
    }
}