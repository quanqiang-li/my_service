package com.carl.vo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 和风天气预报，按天
 * {"HeWeather6": [{WeatherForecastDays}]}
 * @author liqq
 *
 */
public class HeWeather6 {

	@JsonProperty("HeWeather6")
	public ArrayList<WeatherForecastDays> weatherForecastDays;
}
