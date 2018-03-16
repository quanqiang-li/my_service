package com.carl.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.carl.conf.NetworkServerUtil;
import com.carl.service.ConsumerService;

/**
 * 监听app启动之后，这里是当表不存在时就建表
 * @author liqq
 *
 */
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ConsumerService consumerService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			log.info("derby服务 准备启动....");
			//启动derby的网络服务
			NetworkServerUtil.main(null);
			//测试数据库实例
			consumerService.count();
			log.info("derby服务 启动成功....");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("derby服务 启动失败....");
		}
	}
	
	
}
