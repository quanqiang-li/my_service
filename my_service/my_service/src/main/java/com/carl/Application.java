package com.carl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springboot 官方文档：
 * http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
 * @author zhangkm
 *
 */
//springboot在/static, /public, META-INF/resources, /resources等存放静态资源的目录
@Controller
@SpringBootApplication
//@MapperScan(basePackages = "com.ixinnuo.financial.dao.mysql")
//@EnableTransactionManagement
public class Application extends WebMvcConfigurerAdapter {
    /**
     * 工程程序入口
     * @Description: 工程程序入口
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home(HttpServletRequest request) {
       //return "redirect:/swagger-ui.html";
        return getRedirectUrl(request);
    }
    
    /**
     * 跳转，如：http://localhost:22378?method=/root/index；转发到/root/index
     * @param request
     * @return
     */
    String getRedirectUrl(HttpServletRequest request){
    	//要跳转的method，如【/root/index】
    	String method = request.getParameter("method");
    	if(StringUtils.isEmpty(method)){
    		//重定向，url变化，参数丢弃
    		return "redirect:/root/index";
    	}
    	//直接转发，url不变，参数携带过去
    	return method;
    	//return "redirect:" + method;
    }
    
}