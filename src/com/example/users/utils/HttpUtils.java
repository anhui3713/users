package com.example.users.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;


public class HttpUtils {

	/**
	 * ��������ַ
	 */
	public static final String BASE_PATH = "http://192.168.125.41:3000/";


	/**
	 * ��������
	 * @param request
	 * @return
	 * @throws HttpException 
	 */
	public static String request(HttpUriRequest request) throws HttpException {

		// ����http client����
		HttpClient httpClient = new DefaultHttpClient();
		// ִ����������Ӧ����
		HttpResponse response = null;
		
		try {
			// ִ������
			response = httpClient.execute(request);
			// ���ִ�н��ok ��ȡ��Ӧ���
			int sc = response.getStatusLine().getStatusCode();
			if (sc == HttpStatus.SC_OK) {
				// ��ȡ��Ӧ�е�����
				HttpEntity entity = response.getEntity();
				long cl = entity.getContentLength();

				// �ж���Ӧ�����Ƿ�Ϊ��
				if (cl > 0) {
					BufferedReader br = null;
					try {
						// ��ȡ��Ӧ������
						br = new BufferedReader(new InputStreamReader(entity.getContent()));

						// ����Ӧ�������ж�ȡ����
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null) {
							builder.append(line + "\n");
						}

						// �������������õ����ؽ������
						return builder.toString();

					} finally {
						if (br != null) {
							br.close();
						}
					}
				}
			} else {
				throw new HttpException("������Դ����:" + sc);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpException("�����������:" + e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * ��jsonת�ɶ���
	 * @param json
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	private static Object json2object(String json, Class<?> c) throws HttpException {
		try {
			return JSONUtils.json2Object(json, c);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new HttpException("�������ݿ��ܲ���json��ʽ.", e);
		}
	}
	
	/**
	 * ��jsonת�ɶ���list
	 * @param json
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	private static List<?> json2list(String json, Class<?> c) throws HttpException {
		try {
			return JSONUtils.json2List(json, c);
		} catch (JSONException e) {
			e.printStackTrace();

			throw new HttpException("�������ݿ��ܲ���json��ʽ", e);
		}
	}
	
	/**
	 * �ӷ�������ȡ��Ӧ����
	 * @param action
	 * @param data
	 * @return
	 * @throws HttpException 
	 */
	public static String getJSON(String action, String data) throws HttpException {
		
		// ����get��ַ
		StringBuilder uri = new StringBuilder(BASE_PATH).append(action);
		if(data != null) {
			uri.append("?").append(data);
		}
		
		// ����get����
		HttpGet get = new HttpGet(uri.toString());
		
		// ��������
		return request(get);
	}
	
	/**
	 * �ӷ�������ȡ���ݳɶ���
	 * @param action
	 * @param c
	 * @return
	 * @throws HttpException 
	 */
	public static Object getObject(String action, String data, Class<?> c) throws HttpException {
		
		String result = getJSON(action, data);
		
		return json2object(result, c);
	}
	
	/**
	 * �ӷ�������ȡ�����б�
	 * @param action
	 * @param data
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	public static List<?> getList(String action, String data, Class<?> c) throws HttpException {
		String result = getJSON(action, data);
		return json2list(result, c);
	}
	
	/**
	 * ����post�������ݵ���������
	 * 
	 * @param action
	 * @param data
	 * @return
	 * @throws HttpException
	 */
	public static String postJSON(String action, String data)
			throws HttpException {
		
		// post�������
		HttpPost post = new HttpPost(BASE_PATH + action);
		// ��������
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", data));

		// ���ò�����post����
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException("������װ����", e);
		}

		return request(post);
	}

	/**
	 * ����post����,������ָ������
	 * 
	 * @param action
	 * @param data
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	public static Object postJSON(String action, String data, Class<?> c)
			throws HttpException {
		// ִ��http����õ���Ӧ���
		String result = postJSON(action, data);

		return json2object(result, c);
	}
	
	/**
	 * ����post����,������ָ�������б�
	 * 
	 * @param action
	 * @param data
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	public static List<?> postJSON4ListResult(String action, String data, Class<?> c)
			throws HttpException {
		String result = postJSON(action, data);
		
		return json2list(result, c);
	}
}
