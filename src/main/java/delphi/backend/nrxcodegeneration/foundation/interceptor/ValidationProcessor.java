package delphi.backend.nrxcodegeneration.foundation.interceptor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.micronaut.core.type.Argument;
import io.micronaut.http.annotation.Body;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;

import delphi.backend.nrxcodegeneration.foundation.utils.ValidatorUtility;

@Singleton
public class ValidationProcessor implements MethodInterceptor<Object, Object> {
    @Inject
    private ValidatorUtility validatorUtility;

    public ValidationProcessor() {
    }

    /**
     * For each method invocation, validate the body parameter if it is annotated with @Body
     *
     * @param context The MethodInvocationContext object.
     * @return The return value of the method invocation.
     */
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Object[] parameterValues = context.getParameterValues();
        Argument[] arguments = context.getArguments();

        for (int index = 0; index < arguments.length; ++index) {
            if (arguments[index].isAnnotationPresent(Body.class)) {
                this.validatorUtility.validate(parameterValues[index]);
            }
        }

        return context.proceed();
    }
}
