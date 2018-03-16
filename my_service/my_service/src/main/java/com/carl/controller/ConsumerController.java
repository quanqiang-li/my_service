package com.carl.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carl.entity.Consumer;
import com.carl.service.ConsumerService;
import com.carl.vo.Code;
import com.carl.vo.ReturnData;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ConsumerService consumerService;

	@Value("${page.limit}")
	private String limit;

	@ApiOperation("创建一条consumer记录")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ReturnData create(@RequestParam String id, @RequestParam String name, @RequestParam String age, @RequestParam String sex) {
		ReturnData response;
		try {
			consumerService.create(id, name, age, sex);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			response = new ReturnData(Code.ERROR);
			response.getDataBody().put("msg", "data exists");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response = new ReturnData(Code.ERROR);
			response.getDataBody().put("msg", "server is busy,try again later");
			return response;
		}
		log.info("创建一条consumer记录，id{},name{},age{},sex{}", id, name, age, sex);
		return new ReturnData(Code.OK);
	}

	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ReturnData retrieve(@RequestParam String offset) {
		ReturnData response;
		List<Consumer> list = null;
		try {
			if ("-1".equals(offset)) {
				list = consumerService.retrieveAll();
			} else {
				list = consumerService.retrieveSeveral(limit, offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ReturnData(Code.ERROR);
			response.getDataBody().put("msg", "server is busy,try again later");
			return response;
		}
		response = new ReturnData(Code.OK);
		response.getDataBody().put("result", list);
		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ReturnData update(@RequestParam String id, @RequestParam String name, @RequestParam String age, @RequestParam String sex) {
		ReturnData response;
		try {
			consumerService.update(id, name, age, sex);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ReturnData(Code.ERROR);
			response.getDataBody().put("msg", "server is busy,try again later");
			return response;
		}
		return new ReturnData(Code.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ReturnData delete(@RequestParam String[] ids) {
		ReturnData response;
		try {
			consumerService.delete(Arrays.asList(ids));
		} catch (Exception e) {
			e.printStackTrace();
			response = new ReturnData(Code.ERROR);
			response.getDataBody().put("msg", "server is busy,try again later");
			return response;
		}
		return new ReturnData(Code.OK);
	}
}
