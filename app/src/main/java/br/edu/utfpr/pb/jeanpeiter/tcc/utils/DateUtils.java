package br.edu.utfpr.pb.jeanpeiter.tcc.utils;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public long minutosEntre(Long millisInicio, Long millisFinal) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        TimeZone timezone = cal.getTimeZone();
        int offsetStart = timezone.getOffset(millisInicio);
        int offsetEnd = timezone.getOffset(millisFinal);
        int offset = offsetEnd - offsetStart;
        return TimeUnit.MILLISECONDS.toMinutes(millisFinal - millisInicio + offset);
    }

    public LocalDateTime millisToLocalDateTime(Long millis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public LocalDate millisToLocalDate(Long millis){
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Long  localDateToMillis(LocalDate localDate,LocalTime opcao ){
        return localDate.atTime(opcao).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
