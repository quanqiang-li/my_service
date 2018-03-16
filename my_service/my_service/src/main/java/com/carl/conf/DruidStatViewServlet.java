package com.carl.conf;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;
/**
 * 阿里数据库连接池监控
 * http://yourhost/yourproject/druid/index.html
 * @author liqq
 *
 */
@WebServlet(urlPatterns = "/druid/*", initParams = { 
		@WebInitParam(name = "allow", value = ""), //没有配置，允许所有
		@WebInitParam(name = "deny", value = ""),//没有配置，不限制
		@WebInitParam(name = "loginUsername", value = "test"), 
		@WebInitParam(name = "loginPassword", value = "test"), 
		@WebInitParam(name = "resetEnable", value = "false") })
public class DruidStatViewServlet extends StatViewServlet {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

}