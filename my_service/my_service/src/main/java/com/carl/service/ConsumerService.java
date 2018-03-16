package com.carl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.carl.entity.Consumer;
import com.carl.mapper.derby.ConsumerMapper;

@Service
public class ConsumerService {
	@Autowired
	private ConsumerMapper dao;

	/**
	 * 统计表总数，如果没有建表，则建表，返回结果0
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer count() throws Exception {
		try {
			return dao.count();
		} catch (BadSqlGrammarException e) {
			if (e.getSQLException().getSQLState().equals("42X05")) {
				dao.create();
			}
		}
		return 0;
	}

	/**
	 * 创建一条记录
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param sex
	 * @throws Exception
	 */
	public void create(String id, String name, String age, String sex) throws Exception {
		Consumer obj = new Consumer();
		obj.setId(Long.parseLong(id));
		obj.setName(name);
		obj.setAge(age);
		obj.setSex(sex);
		dao.insert(obj);
	}

	/**
	 * 查询表所有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Consumer> retrieveAll() throws Exception {
		return dao.selectAll();
	}

	/**
	 * 分页查询，按id升序
	 * 
	 * @param limit
	 *            每页条数
	 * @param offset
	 *            起始位置
	 * @return
	 * @throws Exception
	 */
	public List<Consumer> retrieveSeveral(String limit, String offset) throws Exception {
		return dao.selectSeveral(Integer.parseInt(limit), Integer.parseInt(offset));
	}

	/**
	 * 更新数据，按主键
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param sex
	 * @throws Exception
	 */
	public void update(String id, String name, String age, String sex) throws Exception {
		Consumer obj = new Consumer();
		obj.setId(Long.parseLong(id));
		obj.setName(name);
		obj.setAge(age);
		obj.setSex(sex);
		dao.update(obj);
	}

	/**
	 * 更新数据，按主键
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(List<String> ids) throws Exception {
		dao.delete(ids);
	}
}