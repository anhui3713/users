package com.example.users.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    public static Date parse(String source) throws ParseException {
	return parse(source, PATTERNS[0]);
    }
    
    public static Date parse(String source, String pattern) throws ParseException {
	if(source == null) {
	    return null;
	}
	
	sdf.applyPattern(pattern);
	return sdf.parse(source);
    }
    
    public static Date parseAllPattern(String source) {
	
	for(String pattern : PATTERNS) {
	    try {
		return parse(source, pattern);
	    } catch (ParseException e) {
	    }
	}
	return null;
    }
}
