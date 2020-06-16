package br.edu.utfpr.pb.jeanpeiter.tcc.utils;


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

}
