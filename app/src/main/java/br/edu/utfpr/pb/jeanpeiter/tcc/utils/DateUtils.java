package br.edu.utfpr.pb.jeanpeiter.tcc.utils;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateUtils {

    public LocalDateTime millisToLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public LocalDate millisToLocalDate(Long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Long localDateToMillis(LocalDate localDate, LocalTime opcao) {
        return localDate.atTime(opcao).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public Long localDateTimeToMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public String diaSemanaEData(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    public String horario(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public String diaSemanaDataEHora(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        String data = diaSemanaEData(localDate);
        String hora = horario(localTime);
        return data.concat(" - ").concat(hora);
    }


}
