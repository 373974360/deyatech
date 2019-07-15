package com.deyatech.common.jackson;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <p>
 * string日期转换类
 * </p>
 *
 * @author: lee.
 * @since: 2019/4/29 16:14
 */
@Component
public class String2DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        String dateTimeFormat = this.getDateTimeFormat(source);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDateTimeFormat(String date){
        // yyyy-MM-dd HH:mm:ss
        String dateTimePattern1 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
        // yyyy-MM-dd
        String datePattern1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        // HH:mm:ss
        String timePattern1 = "[0-9]{2}:[0-9]{2}:[0-9]{2}";
        String dateTimeFormat = "";
        if (Pattern.compile(dateTimePattern1).matcher(date).matches()) {
            dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        }
        if (Pattern.compile(datePattern1).matcher(date).matches()) {
            dateTimeFormat = "yyyy-MM-dd";
        }
        if (Pattern.compile(timePattern1).matcher(date).matches()) {
            dateTimeFormat = "HH:mm:ss";
        }
        return dateTimeFormat;
    }
}
