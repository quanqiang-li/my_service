package com.carl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carl.service.WeatherForecastService;
import com.carl.util.WebUtil;
import com.carl.vo.Code;
import com.carl.vo.ReturnData;
import com.carl.vo.WeatherForecastDays;
/**
 * 和风天气预报
 * @author liqq
 *
 */
@Controller
@RequestMapping("/weathForecast")
public class WeathForecastController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WeatherForecastService weatherForecastService;
	
	/**
	 * 去往页面
	 * @return
	 */
	@RequestMapping(value="index" ,method=RequestMethod.GET)
	public String index(){
		return "/html/weathForecast.html";
	}
	
	/**
	 * 最新天气预报情况
	 * @param request
	 * @param location 位置：城市，或者区县,城市；如：邯郸、丛台,邯郸
	 */
	@RequestMapping(value="latestDays",method=RequestMethod.GET)
	@ResponseBody
	public ReturnData latestDays(HttpServletRequest request,String location) {
		//如果没有指定location，自动获取客户端ip
		if(StringUtils.isEmpty(location)){
			location = WebUtil.getClientAddrIP(request);
		}
		//避免中文符号的问题
		if(location.contains("，")){
			location = location.replace("，", ",");
		}
		log.info("查询{}天气预报",location);
		ReturnData returnData = new ReturnData();
		WeatherForecastDays wfd = null;
		try {
			wfd = weatherForecastService.getWFD(location);
			if("ok".equalsIgnoreCase(wfd.status)){
				returnData.setCode(Code.OK);
			}else{
				returnData.setCode(Code.ERROR);
			}
			String statusMsg = WeatherForecastService.statusMap.get(wfd.status);
			wfd.status = statusMsg==null?"服务异常":statusMsg;
		} catch (IOException e) {
			returnData.setCode(Code.ERROR);
			e.printStackTrace();
		}
		returnData.getDataBody().put("weatherForecastDays", wfd);
		return returnData;
	}
}
