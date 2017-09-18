package util;

import application.exception.CommonException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by wxji on 2017-01-10.
 */
public class DataConvert {
    public static <T> T mapToObject(Map source, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return mapToObject(source, t, clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommonException(e.getMessage());
        }
    }

    public static <T> T mapToObject(Map source, Object target, Class<T> clazz) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        beanWrapper.setPropertyValues(source);
        return (T) target;
    }

    public synchronized static Date stringToDate(String s) {
        if (s == null || s.trim().length() <= 0) {
            return null;
        }
        try {
            return DateUtils.parseDate(s, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd hh:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmss");
        } catch (ParseException e) {
            throw new CommonException("日期格式错误");
        }
    }
}
