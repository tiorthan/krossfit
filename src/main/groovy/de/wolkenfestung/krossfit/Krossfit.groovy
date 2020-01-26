package de.wolkenfestung.krossfit

class Krossfit {
    static <T> T wrapSuspendFunctions(T instance) {
        instance.metaClass.invokeMethod = { methodName, arguments ->
            return SuspendToolsKt.blockingSuspendCall(instance, methodName, arguments)
        }

        return instance
    }
}
