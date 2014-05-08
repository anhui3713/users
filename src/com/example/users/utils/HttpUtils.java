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
	 * 服务器地址
	 */
	public static final String BASE_PATH = "http://192.168.125.41:3000/";


	/**
	 * 发起请求
	 * @param request
	 * @return
	 * @throws HttpException 
	 */
	public static String request(HttpUriRequest request) throws HttpException {

		// 创建http client对象
		HttpClient httpClient = new DefaultHttpClient();
		// 执行请求后的响应对象
		HttpResponse response = null;
		
		try {
			// 执行请求
			response = httpClient.execute(request);
			// 如果执行结果ok 获取响应结果
			int sc = response.getStatusLine().getStatusCode();
			if (sc == HttpStatus.SC_OK) {
				// 获取响应中的数据
				HttpEntity entity = response.getEntity();
				long cl = entity.getContentLength();

				// 判断响应数据是否不为空
				if (cl > 0) {
					BufferedReader br = null;
					try {
						// 获取响应数据流
						br = new BufferedReader(new InputStreamReader(entity.getContent()));

						// 从响应数据流中读取数据
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null) {
							builder.append(line + "\n");
						}

						// 将读出数据设置到返回结果集中
						return builder.toString();

					} finally {
						if (br != null) {
							br.close();
						}
					}
				}
			} else {
				throw new HttpException("请求资源出错:" + sc);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpException("发送请求出错:" + e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * 将json转成对象
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
			throw new HttpException("返回数据可能不是json格式.", e);
		}
	}
	
	/**
	 * 将json转成对象list
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

			throw new HttpException("返回数据可能不是json格式", e);
		}
	}
	
	/**
	 * 从服务器获取对应数据
	 * @param action
	 * @param data
	 * @return
	 * @throws HttpException 
	 */
	public static String getJSON(String action, String data) throws HttpException {
		
		// 构造get地址
		StringBuilder uri = new StringBuilder(BASE_PATH).append(action);
		if(data != null) {
			uri.append("?").append(data);
		}
		
		// 创建get对象
		HttpGet get = new HttpGet(uri.toString());
		
		// 发起请求
		return request(get);
	}
	
	/**
	 * 从服务器获取数据成对象
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
	 * 从服务器获取数据列表
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
	 * 采用post发送数据到服务器端
	 * 
	 * @param action
	 * @param data
	 * @return
	 * @throws HttpException
	 */
	public static String postJSON(String action, String data)
			throws HttpException {
		
		// post请求对象
		HttpPost post = new HttpPost(BASE_PATH + action);
		// 构建参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", data));

		// 设置参数到post对象
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException("参数封装出错", e);
		}

		return request(post);
	}

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

		return json2object(result, c);
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
		
		return json2list(result, c);
	}
}
