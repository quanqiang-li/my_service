package com.carl.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.carl.Application;
import com.carl.vo.WeatherForecastDays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestWeatherForecastService {

	@Autowired
	private WeatherForecastService weatherForecastService;
	
	@Test
	public void testGetWFD(){
		//区,市
		String location = "丛台,邯郸";
		//自动ip
		//String location = "auto_ip";
		//指定ip
		//String location = "58.135.76.143";
		try {
			WeatherForecastDays wfd = weatherForecastService.getWFD(location);
			String statusMsg = WeatherForecastService.statusMap.get(wfd.status);
			System.out.println(statusMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String location = "丛台，邯郸";
		if(location.contains("，")){
			location = location.replace("，", ",");
		}

		System.out.println(location);
	}
}
