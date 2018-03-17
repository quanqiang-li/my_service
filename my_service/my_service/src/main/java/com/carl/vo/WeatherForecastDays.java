package com.carl.vo;

import java.util.ArrayList;

/**
 * 和风天气预报，按天结果
 * 
 * @author liqq
 *
 */

public class WeatherForecastDays {
	/**
	 * 接口状态
	 */
	public String status;
	public Basic basic;
	public Update update;
	public ArrayList<DailyForecast> daily_forecast;
 
	/**
	 * 城市基本信息
	 * 
	 * @author liqq
	 *
	 */
	public static class Basic {
		/**
		 * 地区／城市ID CN101080402
		 */
		public String cid;
		/**
		 * 地区／城市名称 卓资
		 */
		public String location;
		/**
		 * 该地区／城市的上级城市 乌兰察布
		 */
		public String parent_city;
	}

	/**
	 * 更新时间
	 * 
	 * @author liqq
	 *
	 */
	public static class Update {
		/**
		 * 当地时间，24小时制，格式yyyy-MM-dd HH:mm
		 */
		public String loc;
	}

	/**
	 * 按天预报。
	 * @author liqq
	 *
	 */
	public static class DailyForecast {
		public String date; // 预报日期 2013-12-30
		public String sr; // 日出时间 07:36
		public String ss; // 日落时间 16:58
		public String mr; // 月升时间 04:47
		public String ms; // 月落时间 14:59
		public String tmp_max; // 最高温度 4
		public String tmp_min; // 最低温度 -5
		public String cond_txt_d; // 白天天气状况描述 晴
		public String cond_txt_n; // 晚间天气状况描述 晴
		public String wind_dir; // 风向 西北风
		public String wind_sc; // 风力 1-2
		public String hum; // 相对湿度 37
		public String pcpn; // 降水量 0
		public String pop; // 降水概率 0
		public String pres; // 大气压强 1018
		public String uv_index; // 紫外线强度指数 3
		public String vis; // 能见度，单位：公里 10
	}

	public static void main(String[] args) {
		System.out.println("invalid key".equals("invalid key"));
	}

}
