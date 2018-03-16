package com.carl.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;
/**
 * 阿里数据监控页面 放过的资源
 * @author liqq
 *
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = { 
		@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") })
public class DruidStatFilter extends WebStatFilter {

}
