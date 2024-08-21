package com.globant.trainingnewgen.service.client.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentValidation.class)
public @interface ValidDocument {

    String message() default "Invalid document format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
