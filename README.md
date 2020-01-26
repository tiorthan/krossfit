# krossfit
Simplified blocking calls of Kotlin suspend functions from Groovy code.

This little helper was written for the purpose of unit testing Kotlin code with the Spock test framework. It allows
calling suspend functions that need to be tested from your Groovy code without having to provide a Continuation.

## Usage

```
def instance = Krossfit.wrapFunctionCalls(new KotlinClassWithSuspendMethods())
instance.aSuspendFunction()
```

By calling `Krossfit.wrapFunctionCalls()` all functions calls from Groovy to an instance are intercepted and redirected
to a bit of Kotlin wrapper code calling the function from within a `runBlocking {}` block.

### Restrictions
Right now the interceptor does not provide a sophisticated matching of function arguments So far, it is not possible to
call arguments by name and you cannot omit optional arguments.