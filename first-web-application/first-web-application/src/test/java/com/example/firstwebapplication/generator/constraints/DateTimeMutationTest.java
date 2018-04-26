package com.example.firstwebapplication.generator.constraints;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


class DateTimeMutationTest {

    @Rule
    private final ExpectedException expectedException = ExpectedException.none();


    @Test
    void testMutateOptions() {
    }


    @Test
    void testCreateTodayDate() {
        assertEquals(new Date().toString(), DateTimeMutation.createTodayDate());
    }


    @Test
    void testCreatePastOrFutureDateValidDate() {
        String dateString = "01/15/2018";
        String oneDateToThePast = DateTimeMutation.createPastOrFutureDate(dateString, -1);
        String oneDateToTheFuture = DateTimeMutation.createPastOrFutureDate(dateString, 1);

        assertEquals("Sun Jan 14 00:00:00 CST 2018", oneDateToThePast);
        assertEquals("Tue Jan 16 00:00:00 CST 2018", oneDateToTheFuture);
    }


    @Test
    void testCreatePastOrFutureDateInvalidDate() {
        // invalid input date == today by default
        String oneDateToThePast = DateTimeMutation.createPastOrFutureDate("-2", -1);
        String oneDateToTheFuture = DateTimeMutation.createPastOrFutureDate("", 1);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        calendar.add(Calendar.DATE, 2);
        Date tomorrow =  calendar.getTime();

        assertEquals(yesterday.toString(), oneDateToThePast);
        assertEquals(tomorrow.toString(), oneDateToTheFuture);
    }


    @Test
    void testCreateAlwaysInvalidDates() {
        Calendar cal = Calendar.getInstance();

        expectedException.expect(Exception.class);
        Date invalidDate = new Date(DateTimeMutation.createAlwaysInvalidDates());
        cal.setLenient(false);
        cal.setTime(invalidDate);
        cal.getTime();
    }


    @Test
    void createDateInAllFormatsExcept() {
        for(int i = 0; i < 10; i++) {
            assertFalse(DateTimeMutation.createDateInAllFormatsExcept(
                    "MM/dd/yyyy").matches("\\d{2}/\\d{2}/\\d{4}"));
        }

        for(int i = 0; i < 10; i++) {
            assertFalse(DateTimeMutation.createDateInAllFormatsExcept(
                    "yyyy-MM-dd hh:mm:ss").matches(
                            "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
        }

    }


    @Test
    void createDateInFormat() {
        String date = DateTimeMutation.createDateInFormat("MM/dd/yyyy");
        String regex = "\\d{2}/\\d{2}/\\d{4}";
        assertTrue(date.matches(regex));

        date = DateTimeMutation.createDateInFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        regex = "\\d{4}.\\d{2}.\\d{2} \\S{2} at \\d{2}:\\d{2}:\\d{2} \\S+$";
        assertTrue(date.matches(regex));
    }
}