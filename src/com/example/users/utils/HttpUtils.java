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
	 * 服务器地址
	 */
	public static final String BASE_PATH = "http://localhost:1337/";



	/**
	 * 采用post请求,并返回指定对象
	 * 
	 * @param action
	 * @param data
	 * @param c
	 * @return
	 * @throws HttpException
	 */
	public static Object postJSON(String action, String data, Class<?> c)
			throws HttpException {
		// 执行http请求得到响应结果
		String result = postJSON(action, data);

		try {
			return JSONUtils.json2Object(result, c);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new HttpException("返回数据可能不是json格式.", e);
		}
	}

	/**
	 * 采用post请求,并返回指定对象列表
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
			throw new HttpException("返回数据可能不是json格式", e);
		}
	}

	/**
	 * 采用post发送数据到服务器端
	 * 
	 * @param action
	 * @param data
	 * @return
	 * @throws HttpException
	 */
	public static String postJSON(String action, String data)
			throws HttpException {

		String result = null;

		// 创建http client对象
		HttpClient httpClient = new DefaultHttpClient();
		// post请求对象
		HttpPost post = new HttpPost(BASE_PATH + action);
		// 构建参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", data));

		// 执行请求后的响应对象
		HttpResponse response = null;

		try {
			// 设置参数到post对象
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			// 执行请求
			response = httpClient.execute(post);
			// 如果执行结果ok 获取响应结果
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取响应中的数据
				HttpEntity entity = response.getEntity();
				long cl = entity.getContentLength();

				// 判断响应数据是否不为空
				if (cl > 0) {
					BufferedReader br = null;
					try {
						// 获取响应数据流
						br = new BufferedReader(new InputStreamReader(
								entity.getContent()));

						// 从响应数据流中读取数据
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null) {
							builder.append(line + "\n");
						}

						// 将读出数据设置到返回结果集中
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
			throw new HttpException("发送请求出错:" + e.getMessage(), e);
		}

		return result;
	}

	public static Object get(String action, HttpParams httpParams) {
		return null;
	}

}
