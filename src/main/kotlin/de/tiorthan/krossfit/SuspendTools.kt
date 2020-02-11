package de.tiorthan.krossfit

import kotlinx.coroutines.runBlocking
import kotlin.reflect.KCallable
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.jvm.jvmErasure

infix fun KCallable<Any?>.canBeCalledWith(arguments: Array<Any?>): Boolean {
    if (this.parameters.size != arguments.size + 1) return false

    arguments.forEachIndexed { index, argument ->
        val functionArgumentType = parameters[index + 1].type

        if (argument == null) {
            if (!functionArgumentType.isMarkedNullable) return false
        } else {
            if (!functionArgumentType.jvmErasure.isSuperclassOf(argument::class)) return false
        }
    }

    return true
}

@Suppress("unused")
fun Any.blockingSuspendCall(methodName: String, arguments: Array<Any?>): Any? = this::class.members
    .filter { it.name == methodName }
    .filter { it canBeCalledWith arguments }
    .first()
    .let {
        runBlocking { it.callSuspend(this@blockingSuspendCall, *arguments) }
    }