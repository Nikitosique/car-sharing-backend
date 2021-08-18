package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersValidatorTest {
    @Test
    void validatePageNumber_ShouldThrownException_WhenPageNumberIsNegativeInteger() {
        int pageNumber = -1;
        assertThrows(QueryParametersMismatchException.class,
                () -> ParametersValidator.validatePageNumber(pageNumber));
    }

    @Test
    void validatePageNumber_ShouldThrownNoException_WhenPageNumberIsZero() {
        int pageNumber = 0;
        assertDoesNotThrow(() -> ParametersValidator.validatePageNumber(pageNumber));
    }

    @Test
    void validatePageNumber_ShouldThrownNoException_WhenPageNumberIsPositiveInteger() {
        int pageNumber = 1;
        assertDoesNotThrow(() -> ParametersValidator.validatePageNumber(pageNumber));
    }

    @Test
    void validatePageSize_ShouldThrownException_WhenPageSizeIsNegativeInteger() {
        int pageSize = -1;
        assertThrows(QueryParametersMismatchException.class, () -> ParametersValidator.validatePageSize(pageSize));
    }

    @Test
    void validatePageSize_ShouldThrownException_WhenPageSizeIsZero() {
        int pageSize = 0;
        assertThrows(QueryParametersMismatchException.class, () -> ParametersValidator.validatePageSize(pageSize));
    }

    @Test
    void validatePageSize_ShouldThrownNoException_WhenPageSizeIsPositiveInteger() {
        int pageSize = 1;
        assertDoesNotThrow(() -> ParametersValidator.validatePageSize(pageSize));
    }

    @Test
    void validateRecordId_ShouldThrownException_WhenRecordIsNegativeInteger() {
        int recordId = -1;
        assertThrows(QueryParametersMismatchException.class, () -> ParametersValidator.validateRecordId(recordId));
    }

    @Test
    void validateRecordId_ShouldThrownException_WhenRecordIsZero() {
        int recordId = 0;
        assertThrows(QueryParametersMismatchException.class, () -> ParametersValidator.validateRecordId(recordId));
    }

    @Test
    void validateRecordId_ShouldThrownNoException_WhenRecordIsPositiveInteger() {
        int recordId = 1;
        assertDoesNotThrow(() -> ParametersValidator.validateRecordId(recordId));
    }

}