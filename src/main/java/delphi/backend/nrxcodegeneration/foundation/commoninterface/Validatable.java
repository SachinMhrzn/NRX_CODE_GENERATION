package delphi.backend.nrxcodegeneration.foundation.commoninterface;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import delphi.backend.nrxcodegeneration.foundation.interceptor.ValidationProcessor;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An annotation that can be applied to a method to validate its argument which is annotated with
 * body annotation.
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD})
@Around
@Type(ValidationProcessor.class)
public @interface Validatable {

}
