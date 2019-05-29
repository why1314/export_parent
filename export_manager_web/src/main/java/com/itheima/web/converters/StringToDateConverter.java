package com.itheima.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-06-16:32
 */
public class StringToDateConverter implements Converter<String, Date> {

    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date convert(String s) {
        try {
            if (StringUtils.isEmpty(s)) {
                throw new NullPointerException("日期不能为空");
            }
            if (StringUtils.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd";
            }

            Date date = new SimpleDateFormat(pattern).parse(s);
            return date;
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不对，请输入正确的日期格式");
        }


    }
}
