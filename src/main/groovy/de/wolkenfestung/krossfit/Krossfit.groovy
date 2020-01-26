package de.wolkenfestung.krossfit

class Krossfit {
    static <T> T wrapFunctionCalls(T instance) {
        instance.metaClass.invokeMethod = { methodName, arguments ->
            return SuspendToolsKt.blockingSuspendCall(instance, methodName, arguments)
        }

        return instance
    }
}
