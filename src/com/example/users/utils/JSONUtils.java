package com.example.users.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class JSONUtils {

	/**
	 *从方法中取出属性名(必须是get和set方法)
	 * @param methodName
	 * @return
	 */
	private static String m2aName(String methodName) {
		// 得出属性名
		char firstCode = methodName.charAt(3);
		firstCode = (char) ((firstCode >= 'A' && firstCode <= 'Z') ? (firstCode + 32)
				: firstCode);
		String attrName = firstCode + methodName.substring(4);
		
		return attrName;
	}
	
	/**
	 * 将java对象转成json字符串
	 * @param o
	 * @return
	 * @throws JSONException 
	 */
	public static String object2JSON(Object o) throws JSONException {
		if(o != null) {
			Class<?> c = o.getClass();
			Method[] ms = c.getMethods();
			
			JSONStringer js = new JSONStringer().object();
			
			if(ms != null && ms.length > 0) {
				for(Method m : ms) {
					String methodName = m.getName();
					if(methodName.startsWith("get")) {
						String attrName = m2aName(methodName);
						
						js.key(attrName);
						try {
							js.value(m.invoke(o, new Object[]{}));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						
					}
				}
				
				return js.toString();
			}
		}
		return null;
	}
	
	/**
	 * 从json中取值放到
	 * 
	 * @param m
	 * @param o
	 * @param jo
	 */
	public static void set(Method m, Object o, JSONObject jo) {
		if (m != null && m.getName().startsWith("set")) {
			// 获取方法名
			String methodName = m.getName();
			// 获取出属性名
			String attrName = m2aName(methodName);

			// 获取参数列表类型
			Class<?> paramType = m.getParameterTypes()[0];
			try {
				// String
				if (paramType.equals(String.class)) {
					m.invoke(o, jo.getString(attrName));
				} else if (paramType.equals(Integer.class)
						|| paramType.getName().equals("int")) {
					m.invoke(o, jo.getInt(attrName));
				} else if (paramType.equals(Double.class)
						|| paramType.getName().equals("double")) {
					m.invoke(o, jo.getDouble(attrName));
				} else if (paramType.equals(Boolean.class)
						|| paramType.getName().equals("boolean")) {
					m.invoke(o, jo.getBoolean(attrName));
				} else if (paramType.equals(Float.class)
						|| paramType.getName().equals("float")) {
					m.invoke(o, (float) jo.getDouble(attrName));
				} else if (paramType.equals(Long.class)
						|| paramType.getName().equals("long")) {
					m.invoke(o, jo.getLong(attrName));
				} else if (paramType.equals(Character.class)
						|| paramType.getName().equals("char")) {
					String s = jo.getString(attrName);
					if (s != null) {
						m.invoke(o, s.charAt(0));
					}
				} else if (paramType.equals(Byte.class)
						|| paramType.getName().equals("byte")) {
					m.invoke(o, (byte) jo.getInt(attrName));
				} else if (paramType.equals(Short.class)
						|| paramType.getName().equals("short")) {
					m.invoke(o, (short) jo.getInt(attrName));
				} else if (paramType.equals(Date.class)) {
					m.invoke(o,
							DateUtils.parseAllPattern(jo.getString(attrName)));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private static Object json2Object(JSONObject jo, Class c) {
		// 判断服务器端是否有响应
		if (jo != null) {
			try {
				// 如果服务器端有响应,则创建对应对象
				Object o = c.getConstructor(new Class[]{}).newInstance();

				// 获得类中的方法列表
				Method[] mothods = c.getMethods();

				// 循环调用方法,如果是set开头就设置值
				for (Method m : mothods) {
					set(m, o, jo);
				}

				return o;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 将json转成对应的对象
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	public static Object json2Object(String json, Class<?> c) throws JSONException {
		JSONObject jo = new JSONObject(json);
		
		return json2Object(jo, c);
	}
	
	/**
	 * 将json字符串转为指定类型对象列表
	 * @param json
	 * @param c
	 * @return
	 * @throws JSONException
	 */
	public static List json2List(String json, Class c) throws JSONException {
		if (json != null) {
			List list = new ArrayList();
			JSONArray ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);

				Object o = JSONUtils.json2Object(jo, c);
				
				list.add(o);
			}
			return list;
		}
		
		return null;
	}
}
