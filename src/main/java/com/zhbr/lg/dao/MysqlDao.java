package com.zhbr.lg.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.zhbr.lg.entity.Exercises;

@Service
public class MysqlDao {

	Logger logger = Logger.getLogger(MysqlDao.class);
	/**
	 * mysql连接
	 */
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	JdbcTemplate jdbcTemplate1;

	/**
	 * mysql连接测试
	 * 
	 * @return boolean
	 */
	public boolean mysqlTest() {
		String result = "";
		try {
			List<Map<String, Object>> list = jdbcTemplate1.queryForList("select 'mysql' as mysql from dual");
			result = list.get(0).get("mysql").toString();
		} catch (DataAccessException e) {
			logger.info("连接mysql数据库失败:", e);
		}
		logger.info("连接mysql数据库成功");
		return "mysql".equals(result);
	}

	/**
	 * 分页得到exercises表中的数据
	 * 
	 * @param index
	 *            开始数据行
	 * @param size
	 *            每页数据量
	 * @return
	 */
	public List<Exercises> getAllByPage(int index, int size) {
		String sql = "select guid,ssfl,sswd,txfl,tm,xxa,xxb,xxc,xxd,da,js from exercises limit ?,?";
		RowMapper<Exercises> rowMapper = new BeanPropertyRowMapper<>(Exercises.class);
		List<Exercises> exercises = null;
		try {
			exercises = jdbcTemplate1.query(sql, rowMapper, index, size);
		} catch (DataAccessException e) {
			logger.info("mysql数据库查询exercises表数据失败:", e);
		}
		logger.info("调用mysql的getAllByPage方法：index = " + index + "，size = " + size);
		return exercises;
	}

	/**
	 * 得到mysql数据库中exercises表的数据量
	 * 
	 * @return exercises表总的数据量
	 */
	public int getCount() {
		String sql = "select count(1) from exercises";
		int count = 0;
		try {
			count = jdbcTemplate1.queryForObject(sql, Integer.class);
		} catch (DataAccessException e) {
			logger.info("mysql数据库查询exercises表总数量失败:", e);
		}
		logger.info("mysql数据库exercises表总数据：" + count + "条");
		return count;
	}

}
