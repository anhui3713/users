package com.example.users.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.users.exception.DateParseException;

public class DateUtils {
    
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    
    public static final String[] PATTERNS = {
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd",
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd",
		"yyyy.MM.dd HH:mm:ss",
		"yyyy.MM.dd",
		"yyyy年MM月dd日HH时mm分ss秒",
		"yyyy年MM月dd日"
    };
    
    /**
     * 将字符串转按默认格式转成时间
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parse(String source){
    	return parse(source, PATTERNS[0]);
    }
    
    /**
     * 按照指定格式转时间
     * @param source
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String source, String pattern) {
		if(source == null) {
		    return null;
		}
		
		try {
			sdf.applyPattern(pattern);
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new DateParseException(e);
		}
    }
    
    /**
     * 将字符串转成时间,所有格式都试一遍
     * @param source
     * @return
     */
    public static Date parseAllPattern(String source) {
    	
		for(String pattern : PATTERNS) {
		    try {
		    	return parse(source, pattern);
		    } catch (Exception e) {
		    }
		}
		
		return null;
    }
}
