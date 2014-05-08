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
	 * ��json����ת�ɶ�Ӧ��java����
	 * @param jo
	 * @param c
	 * @return
	 */
	private static Object json2Object(JSONObject jo, Class c) {
		// �жϷ��������Ƿ�����Ӧ
		if (jo != null) {
			try {
				
				if(c == null || c.equals(Object.class)) {
					String cstr = jo.getString("class") ;
					if(cstr == null) return null;
					c = Class.forName(cstr);
				}
				
				// ���������������Ӧ,�򴴽���Ӧ����
				Object o = c.getConstructor(new Class[]{}).newInstance();

				// ������еķ����б�
				Method[] mothods = c.getMethods();

				// ѭ�����÷���,�����set��ͷ������ֵ
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
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 *�ӷ�����ȡ��������(������get��set����)
	 * @param methodName
	 * @return
	 */
	private static String m2aName(String methodName) {
		// �ó�������
		char firstCode = methodName.charAt(3);
		firstCode = (char) ((firstCode >= 'A' && firstCode <= 'Z') ? (firstCode + 32)
				: firstCode);
		String attrName = firstCode + methodName.substring(4);
		
		return attrName;
	}
	
	/**
	 * ��json��ȡֵ�ŵ�
	 * 
	 * @param m
	 * @param o
	 * @param jo
	 */
	private static void set(Method m, Object o, JSONObject jo) {
		if (m != null && m.getName().startsWith("set")) {
			// ��ȡ������
			String methodName = m.getName();
			// ��ȡ��������
			String attrName = m2aName(methodName);

			// ��ȡ�����б�����
			Class<?> paramType = m.getParameterTypes()[0];
			try {
				// ��������ȡ�����ݲ���װ
				if (paramType.equals(String.class)) {
					m.invoke(o, jo.getString(attrName));
				} else if (paramType.equals(Integer.class) || paramType.getName().equals("int")) {
					m.invoke(o, jo.getInt(attrName));
				} else if (paramType.equals(Double.class) || paramType.getName().equals("double")) {
					m.invoke(o, jo.getDouble(attrName));
				} else if (paramType.equals(Boolean.class) || paramType.getName().equals("boolean")) {
					m.invoke(o, jo.getBoolean(attrName));
				} else if (paramType.equals(Float.class) || paramType.getName().equals("float")) {
					m.invoke(o, (float) jo.getDouble(attrName));
				} else if (paramType.equals(Long.class) || paramType.getName().equals("long")) {
					m.invoke(o, jo.getLong(attrName));
				} else if (paramType.equals(Character.class) || paramType.getName().equals("char")) {
					String s = jo.getString(attrName);
					if (s != null) {
						m.invoke(o, s.charAt(0));
					}
				} else if (paramType.equals(Byte.class) || paramType.getName().equals("byte")) {
					m.invoke(o, (byte) jo.getInt(attrName));
				} else if (paramType.equals(Short.class) || paramType.getName().equals("short")) {
					m.invoke(o, (short) jo.getInt(attrName));
				} else if (paramType.equals(Date.class)) {
					m.invoke(o, DateUtils.parseAllPattern(jo.getString(attrName)));
				} else {
					// ���Ϊ����������������,�����json�еĶ�Ӧ���������װ
					JSONObject atJO = jo.getJSONObject(attrName);
					if(atJO != null) {
						Object atO = json2Object(atJO, paramType);
						m.invoke(o, atO);
					}
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

	/**
	 * ��json�ַ���תΪָ�����Ͷ����б�
	 * @param json
	 * @param c
	 * @return
	 * @throws JSONException
	 */
	public static List json2List(String json, Class c) throws JSONException {
		if (json != null) {
			
			System.out.println(json);
			
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
	
	/**
	 * ��jsonת�ɶ�Ӧ�Ķ���
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
	 * ��java����ת��json�ַ���
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
				js.endObject();
				
				return js.toString();
			}
		}
		return null;
	}
}
