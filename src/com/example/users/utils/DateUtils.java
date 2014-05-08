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
		"yyyy��MM��dd��HHʱmm��ss��",
		"yyyy��MM��dd��"
    };
    
    /**
     * ���ַ���ת��Ĭ�ϸ�ʽת��ʱ��
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parse(String source){
    	return parse(source, PATTERNS[0]);
    }
    
    /**
     * ����ָ����ʽתʱ��
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
     * ���ַ���ת��ʱ��,���и�ʽ����һ��
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
