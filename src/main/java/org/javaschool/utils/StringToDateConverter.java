package org.javaschool.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@javax.persistence.Converter(autoApply = true)
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date parsedDate = new Date();
        try {
            parsedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}