package com.carl.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.carl.util.HttpClientUtil;
import com.carl.util.json.JsonUtil;
import com.carl.vo.HeWeather6;
import com.carl.vo.WeatherForecastDays;

/**
 * 天气预报服务
 * 
 * @author liqq
 *
 */
@Service
public class WeatherForecastService {

	@Value("${weatherForecastDaysUrl}")
	private String weatherForecastDaysUrl;
	@Value("${heWeatherUsername}")
	private String heWeatherUsername;
	@Value("${heWeatherKey}")
	private String heWeatherKey;

	/**
	 * 错误代码说明
	 */
	public static final Map<String, String> statusMap = new HashMap<>();
	static {
		statusMap.put("ok", "数据正常");
		statusMap.put("invalid key", "错误的key，请检查你的key是否输入以及是否输入有误");
		statusMap.put("unknown location", "未知或错误城市/地区");
		statusMap.put("unknown city", "未知或错误城市/地区");
		statusMap.put("no data for this location", "该城市/地区没有你所请求的数据");
		statusMap.put("no more requests", "超过访问次数，需要等到当月最后一天24点后进行访问次数的重置或升级你的访问量");
		statusMap.put("param invalid", "参数错误，请检查你传递的参数是否正确");
		statusMap.put("too fast", "请求太频繁，超过限定的200次每分钟");
		statusMap.put("dead", "接口服务异常");
		statusMap.put("permission denied", "无访问权限，你没有购买你所访问的这部分服务");
		statusMap.put("sign error", "签名错误，请参考签名算法");
	}

	public WeatherForecastDays getWFD(String location) throws IOException {
		long seconds = System.currentTimeMillis() / 1000;
		StringBuffer url = new StringBuffer(weatherForecastDaysUrl);
		HashMap<String, String> params = new HashMap<>();
		params.put("location", location);
		params.put("t", String.valueOf(seconds));
		params.put("username", heWeatherUsername);
		params.put("lang", "zh-cn");
		String signature = getSignature(params, heWeatherKey);
		params.put("sign", signature);

		Set<Map.Entry<String, String>> entrys = params.entrySet();
		for (Map.Entry<String, String> param : entrys) {
			url.append(param.getKey().trim()).append("=").append(param.getValue().trim()).append("&");
		}
		// 删除最后一个&
		url.deleteCharAt(url.length() - 1);
		String requestByGet = HttpClientUtil.requestByGet(url.toString(), 1000 * 5);
		JsonUtil jsonUtil = new JsonUtil();
		//从结果看，虽然是数组格式的，但是只会有一个结果
		HeWeather6 heWeather6 = jsonUtil.jsonStr2Object(requestByGet, HeWeather6.class);
		WeatherForecastDays weatherForecastDays = heWeather6.weatherForecastDays.get(0);
		return weatherForecastDays;
	}

	/**
	 * 和风天气签名生成算法-JAVA版本
	 * 
	 * @param HashMap<String,String>
	 *            params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String
	 *            secret 签名密钥（用户的认证key）
	 * @return 签名
	 * @throws IOException
	 */
	private String getSignature(HashMap<String, String> params, String secret) throws IOException {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<>(params);
		Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起

		StringBuilder baseString = new StringBuilder();
		for (Map.Entry<String, String> param : entrys) {
			// sign参数 和 空值参数 不加入算法
			if (param.getValue() != null && !"".equals(param.getKey().trim()) && !"sign".equals(param.getKey().trim()) && !"key".equals(param.getKey().trim())
					&& !"".equals(param.getValue().trim())) {
				baseString.append(param.getKey().trim()).append("=").append(param.getValue().trim()).append("&");
			}
		}
		if (baseString.length() > 0) {
			baseString.deleteCharAt(baseString.length() - 1).append(secret);
		}

		// 使用MD5对待签名串求签
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(bytes);
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
	}

	public static void main(String[] args) {
		WeatherForecastService weatherForecastService = new WeatherForecastService();
		long seconds = System.currentTimeMillis() / 1000;
		StringBuffer url = new StringBuffer("https://free-api.heweather.com/s6/weather/forecast?");
		HashMap<String, String> params = new HashMap<>();
		params.put("location", "auto_ip");
		params.put("t", String.valueOf(seconds));
		params.put("username", "HE1803161415121438");
		params.put("lang", "zh-cn");
		try {
			String signature = weatherForecastService.getSignature(params, "df3a66eae78d431495b418552a79db0e");
			params.put("sign", signature);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Map.Entry<String, String>> entrys = params.entrySet();
		for (Map.Entry<String, String> param : entrys) {
			url.append(param.getKey().trim()).append("=").append(param.getValue().trim()).append("&");
		}
		// 删除最后一个&
		url.deleteCharAt(url.length() - 1);
		String requestByGet = HttpClientUtil.requestByGet(url.toString(), 1000 * 5);
		JsonUtil jsonUtil = new JsonUtil();
		// Map map = jsonUtil.jsonStr2Object(requestByGet, Map.class);
		HeWeather6 heWeather6 = jsonUtil.jsonStr2Object(requestByGet, HeWeather6.class);
		String status = heWeather6.weatherForecastDays.get(0).status;
		System.out.println(status);
	}

}
