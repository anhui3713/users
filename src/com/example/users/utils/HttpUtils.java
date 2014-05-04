package com.example.users.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONException;


public class HttpUtils {

	/**
	 * ��������ַ
	 */
	public static final String BASE_PATH = "http://localhost:1337/";



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

		try {
			return JSONUtils.json2Object(result, c);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new HttpException("�������ݿ��ܲ���json��ʽ.", e);
		}
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
		
		try {
			return JSONUtils.json2List(result, c);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new HttpException("�������ݿ��ܲ���json��ʽ", e);
		}
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

		String result = null;

		// ����http client����
		HttpClient httpClient = new DefaultHttpClient();
		// post�������
		HttpPost post = new HttpPost(BASE_PATH + action);
		// ��������
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", data));

		// ִ����������Ӧ����
		HttpResponse response = null;

		try {
			// ���ò�����post����
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			// ִ������
			response = httpClient.execute(post);
			// ���ִ�н��ok ��ȡ��Ӧ���
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ��ȡ��Ӧ�е�����
				HttpEntity entity = response.getEntity();
				long cl = entity.getContentLength();

				// �ж���Ӧ�����Ƿ�Ϊ��
				if (cl > 0) {
					BufferedReader br = null;
					try {
						// ��ȡ��Ӧ������
						br = new BufferedReader(new InputStreamReader(
								entity.getContent()));

						// ����Ӧ�������ж�ȡ����
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null) {
							builder.append(line + "\n");
						}

						// �������������õ����ؽ������
						result = builder.toString();

					} finally {
						if (br != null) {
							br.close();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpException("�����������:" + e.getMessage(), e);
		}

		return result;
	}

	public static Object get(String action, HttpParams httpParams) {
		return null;
	}

}
