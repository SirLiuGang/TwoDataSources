package com.zhbr.lg.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zhbr.lg.entity.Exercises;

@Service
public class OracleDao {

	Logger logger = Logger.getLogger(OracleDao.class);

	/**
	 * oracle连接
	 */
	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	JdbcTemplate jdbcTemplate2;

	/**
	 * oracle连接测试
	 * 
	 * @return boolean
	 */
	public boolean oracleTest() {
		String result = "";
		try {
			List<Map<String, Object>> list = jdbcTemplate2.queryForList("select 'oracle' as oracle from dual");
			result = list.get(0).get("oracle").toString();
		} catch (DataAccessException e) {
			logger.info("连接oracle数据库失败:", e);
		}
		logger.info("连接oracle数据库成功");
		return "oracle".equals(result);
	}

	/**
	 * 删除exercises表中的数据
	 * 
	 * @return
	 */
	public boolean deleteData() {
		Long beginTime = System.currentTimeMillis();
		try {
			jdbcTemplate2.update("delete from bysj.exercises");
		} catch (DataAccessException e) {
			logger.info("连接oracle数据库失败:", e);
			return false;
		}
		Long endTime = System.currentTimeMillis();
		logger.info("调用oracle的deleteData方法，执行时间：" + (endTime - beginTime));
		return true;
	}

	public boolean batchInsert(List<Exercises> exerciseslist) {
		final List<Exercises> exe = exerciseslist;
		String sql = "INSERT INTO BYSJ.EXERCISES VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			jdbcTemplate2.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, exe.get(i).getGuid());
					ps.setString(2, exe.get(i).getSsfl());
					ps.setString(3, exe.get(i).getSswd());
					ps.setString(4, exe.get(i).getTxfl());
					ps.setString(5, exe.get(i).getTm());
					ps.setString(6, exe.get(i).getXxa());
					ps.setString(7, exe.get(i).getXxb());
					ps.setString(8, exe.get(i).getXxc());
					ps.setString(9, exe.get(i).getXxd());
					ps.setString(10, exe.get(i).getDa());
					ps.setString(11, exe.get(i).getJs());
				}

				@Override
				public int getBatchSize() {
					return exe.size();
				}
			});
		} catch (DataAccessException e) {
			logger.info("数据批量插入oracle数据库失败:", e);
			return false;
		}
		return true;
	}
}
// --oracle中的建表语句
// CREATE TABLE EXERCISES(
// GUID VARCHAR2(100),
// SSFL VARCHAR2(100),
// SSWD VARCHAR2(50),
// TXFL VARCHAR2(50),
// TM VARCHAR2(4000),
// XXA VARCHAR2(4000),
// XXB VARCHAR2(4000),
// XXC VARCHAR2(4000),
// XXD VARCHAR2(4000),
// DA VARCHAR2(50),
// JS VARCHAR2(4000)
// );
