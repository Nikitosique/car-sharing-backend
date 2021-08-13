package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;

public interface ParametersValidator {
    static void validatePageNumber(int pageNumber) {
        if (pageNumber < 0) {
            throw new QueryParametersMismatchException("Page number should be zero or a positive integer. ");
        }
    }

    static void validatePageSize(int pageSize) {
        if (pageSize <= 0) {
            throw new QueryParametersMismatchException("Page size should be a positive integer. ");
        }
    }

    static void validateRecordId(long id) {
        if (id <= 0) {
            throw new QueryParametersMismatchException("Record ID should be a positive integer. ");
        }
    }

}
