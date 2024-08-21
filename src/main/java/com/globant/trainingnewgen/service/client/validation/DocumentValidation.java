package com.globant.trainingnewgen.service.client.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class DocumentValidation implements ConstraintValidator<ValidDocument, String> {

    private static final String DOCUMENT_PATTERN = "^(CE|CC|TI|P)-[0-9]{1,17}$";

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {
        boolean isValid = document != null && document.matches(DOCUMENT_PATTERN);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Document %s does not match with the required format", document))
                    .addConstraintViolation();
        }

        return isValid;
    }
}