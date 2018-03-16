package com.carl.mapper.derby;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.carl.entity.Consumer;

@Mapper
public interface ConsumerMapper {
	/**
	 * 创建consumer表
	 * 
	 * @throws Exception
	 */
	@Update("create table consumer(id bigint,name varchar(255),age smallint,sex varchar(1),primary key (id))")
	void create() throws Exception;

	/**
	 * 统计表总数
	 * 
	 * @return
	 * @throws Exception
	 */
	@Select("select count(1) from consumer")
	Integer count() throws Exception;

	/**
	 * 查询表所有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Select("select * from consumer")
	List<Consumer> selectAll() throws Exception;

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
	@Select("select * from consumer order by id asc {limit ${limit} offset ${offset}}")
	List<Consumer> selectSeveral(@Param("limit") int limit, @Param("offset") int offset) throws Exception;

	/**
	 * 添加一条记录
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Insert("insert into consumer(id,name,age,sex) values(#{o.id}, #{o.name}, #{o.age}, #{o.sex})")
	void insert(@Param("o") Consumer obj) throws Exception;

	/**
	 * 批量删除，按主键
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@Delete("<script>delete from consumer where id in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>#{item}</foreach></script>")
	void delete(@Param("ids") List<String> ids) throws Exception;

	/**
	 * 更新数据，按主键
	 * @param obj
	 * @throws Exception
	 */
	@Update("update consumer set name = #{o.name}, age = #{o.age}, sex = #{o.sex} where id = #{o.id}")
	void update(@Param("o") Consumer obj) throws Exception;
}
