package org.example.utils;

import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class StudentUtilsTest {
    @Test
    public void testDateFormatterFormatting() {
        // Проверка форматирования даты
        SimpleDateFormat dateFormatter = StudentUtils.dateFormatter;
        Date testDate = new Date(100, 0, 1); // 1 января 2000 года (в старом API месяц начинается с 0)

        String formattedDate = dateFormatter.format(testDate);

        // Ожидаемая строка формата
        assertEquals("2000-01-01", formattedDate);
    }

    @Test
    public void testDateFormatterParsing() throws ParseException {
        // Проверка парсинга строки в дату
        SimpleDateFormat dateFormatter = StudentUtils.dateFormatter;
        String dateString = "2024-10-06";

        Date parsedDate = dateFormatter.parse(dateString);

        // Убедимся, что дата парсится корректно
        assertNotNull(parsedDate);

        // Дата "2024-10-06" ожидается
        SimpleDateFormat checkFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String checkFormattedDate = checkFormatter.format(parsedDate);

        assertEquals("2024-10-06", checkFormattedDate);
    }

    @Test
    public void testInvalidDateParsing() {
        // Проверка того, что некорректная дата вызывает исключение
        SimpleDateFormat dateFormatter = StudentUtils.dateFormatter;
        String invalidDateString = "2024-02-30";  // Некорректная дата

        assertThrows(ParseException.class, () -> {
            dateFormatter.parse(invalidDateString);
        });
    }

}