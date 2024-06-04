package com.example.netflop.utils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateHelpers {
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        return dateFormat.format(date);
    }

    public static String formatDateByMonth(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");
        return dateFormat.format(date);
    }

    public static String convertIsoToReadableDateTime(String dateTimeString) {
        // Định dạng đầu vào từ ISO 8601 sang ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString);

        // Định dạng thời gian theo yêu cầu (giờ, phút, ngày, tháng, năm)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Chuyển ZonedDateTime sang chuỗi đã định dạng
        String formattedDateTime = zonedDateTime.format(formatter);

        return formattedDateTime;
    }

}
