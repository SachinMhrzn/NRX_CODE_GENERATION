package delphi.backend.nrxcodegeneration.foundation.utils;

import java.util.Set;
import java.util.Iterator;
import javax.inject.Inject;
import javax.validation.Path.Node;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;

import com.google.gson.JsonObject;

import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;
import delphi.backend.nrxcodegeneration.foundation.error.exception.ValidationException;

public class ValidatorUtility {
    @Inject
    private Validator validator;

    public ValidatorUtility() {
    }

    /**
     * It takes an object and a validator, and it uses the validator to validate the object. If the object is valid, it
     * returns silently. If the object is invalid, it throws a BadRequestException with a message string that contains all
     * the violations
     *
     * @param object the object to validate
     */
    public void validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = this.validator.validate(object, new Class[0]);
        Iterator<ConstraintViolation<Object>> violationIterator = constraintViolations.iterator();
        this.throwBadRequestExceptionWithMessageString(violationIterator);
    }

    /**
     * This function takes a list of constraint violations and turns it into a JSON object
     *
     * @param violationIterator The Iterator of ConstraintViolations.
     */
    private void throwValidationExceptionWithMessageObject(Iterator<ConstraintViolation<Object>> violationIterator) {
        JsonObject errorObject = new JsonObject();

        while (violationIterator.hasNext()) {
            ConstraintViolation<?> violation = (ConstraintViolation) violationIterator.next();
            Iterator var4 = violation.getPropertyPath().iterator();

            while (var4.hasNext()) {
                Node node = (Node) var4.next();
                errorObject.addProperty(node.getName(), violation.getMessage());
            }
        }

        if (!errorObject.entrySet().isEmpty()) {
            throw new ValidationException(errorObject.toString());
        }
    }

    /**
     * It throws a BadRequestException with the message string.
     *
     * @param violationIterator The Iterator of ConstraintViolations.
     */
    private void throwBadRequestExceptionWithMessageString(Iterator<ConstraintViolation<Object>> violationIterator) {
        StringBuilder errorMessage = new StringBuilder();

        while (violationIterator.hasNext()) {
            ConstraintViolation<?> violation = (ConstraintViolation) violationIterator.next();
            Iterator var4 = violation.getPropertyPath().iterator();

            while (var4.hasNext()) {
                Node node = (Node) var4.next();
                errorMessage.append(violation.getMessage());
                errorMessage.append(". ");
            }
        }

        if (errorMessage.length() != 0) {
            throw new BadRequestException(errorMessage.toString());
        }
    }
}
