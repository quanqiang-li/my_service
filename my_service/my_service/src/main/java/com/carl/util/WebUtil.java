package com.carl.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * web环境常用方法
 * 
 * @author liqq
 *
 */
public class WebUtil {

	/**
	 * request.getRemoteAddr() 192.168.239.196<br>
	 * 
	 * request.getHeader("X-Forwarded-For") 58.63.227.162, 192.168.237.178,
	 * 192.168.238.218<br>
	 * 
	 * request.getHeader("X-Real-IP") 192.168.238.218<br>
	 * 
	 * 所以访问的流程应该是这样，
	 * <p>
	 * 客户端58.63.227.162发出请求，经过192.168.237.178,
	 * 192.168.238.218两层转发，到了192.168.239.196这台NGINX上，NGINX就把X-Real-IP头设成了自己看到的remote_addr，
	 * 也就是直接发给到他的192.168.238.218，这时候服务端收到这个包，对服务端来说直接发给他的remote_addr就是NGINX的ip，也就是192.168.239.196，
	 * 那么服务端里面的request.getRemoteAddr()就是192.168.239.196，那么在服务端里拿最原始的ip逻辑（也就是拿能够知道的最外层的ip）应该是这样：
	 * <p>
	 * 如果XFF不为空，拿XFF的左边第一个
	 * 
	 * 如果XFF为空，拿XRI
	 * 
	 * 如果XRI为空，只能拿request.getRemoteAddr()，也就是只能拿到最直接发给他的机器ip了，
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientAddrIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
