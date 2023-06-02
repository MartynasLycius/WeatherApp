package com.proit.application.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DateTimeUtilTest {
    @Test
    void testConvertDateStringToDayAndDateString() {
        String dateString = "2023-06-01";
        String expectedFormattedDate = "Thursday, June 1";

        String formattedDate = DateTimeUtil.convertDateStringToDayAndDateString(dateString);

        assertEquals(expectedFormattedDate, formattedDate);
    }

    @Test
    void testConvertDateStringToDayAndDateString_NullInput() {
        String dateString = null;

        assertThrows(NullPointerException.class, () -> DateTimeUtil.convertDateStringToDayAndDateString(dateString));
    }

    @Test
    void testConvertDateTimeStringToTimeAmPmString() {
        String dateTimeString = "2023-06-01T13:30:00";
        String expectedFormattedTime = "1:30 PM";

        String formattedTime = DateTimeUtil.convertDateTimeStringToTimeAmPmString(dateTimeString);

        assertEquals(expectedFormattedTime, formattedTime);
    }

    @Test
    void testConvertDateTimeStringToTimeAmPmString_NullInput() {
        String dateTimeString = null;

        assertThrows(NullPointerException.class, () -> DateTimeUtil.convertDateTimeStringToTimeAmPmString(dateTimeString));
    }

    @Test
    void testIsDateInBetweenTwoDates() {
        String date = "2023-06-02T10:00:00";
        String startDate = "2023-06-01T00:00:00";
        String endDate = "2023-06-03T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertTrue(result);
    }

    @Test
    void testIsDateInBetweenTwoDates_OutsideRange() {
        String date = "2023-06-04T12:00:00";
        String startDate = "2023-06-01T00:00:00";
        String endDate = "2023-06-03T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertFalse(result);
    }

    @Test
    void testIsDateInBetweenTwoDates_StartDateEqualToEndDate() {
        String date = "2023-06-01T12:00:00";
        String startDate = "2023-06-01T00:00:00";
        String endDate = "2023-06-01T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertTrue(result);
    }

    @Test
    void testIsDateInBetweenTwoDates_DateEqualToStartDate() {
        String date = "2023-06-01T12:00:00";
        String startDate = "2023-06-01T12:00:00";
        String endDate = "2023-06-02T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertFalse(result);
    }

    @Test
    void testIsDateInBetweenTwoDates_DateBeforeStartDate() {
        String date = "2023-05-31T12:00:00";
        String startDate = "2023-06-01T00:00:00";
        String endDate = "2023-06-03T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertFalse(result);
    }

    @Test
    void testIsDateInBetweenTwoDates_DateAfterEndDate() {
        String date = "2023-06-04T12:00:00";
        String startDate = "2023-06-01T00:00:00";
        String endDate = "2023-06-03T23:59:59";

        boolean result = DateTimeUtil.isDateInBetweenTwoDates(date, startDate, endDate);

        assertFalse(result);
    }
}