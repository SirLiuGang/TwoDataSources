package com.zhbr.lg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhbr.lg.dao.MysqlDao;
import com.zhbr.lg.dao.OracleDao;
import com.zhbr.lg.entity.Exercises;

@RestController
@RequestMapping("/test")
public class TestController {

	Logger logger = Logger.getLogger(TestController.class);

	/**
	 * mysql数据源
	 */
	@Resource
	MysqlDao mysqlDao;

	/**
	 * oracle数据源
	 */
	@Resource
	OracleDao oracleDao;
	
	public boolean flag = false;

	@RequestMapping("/ping")
	public String Ping() {
		if(mysqlDao.mysqlTest() && oracleDao.oracleTest()){
			flag = true;
			return "数据库连接正常！";
		} else {
			return "数据库连接失败！";
		}
		
	}

	@RequestMapping("/sync")
	public String sync() {
		if(!flag){
			return "请先检查数据库连接";
		}
		Long beginTime = System.currentTimeMillis();
		int begin = 0;// 分页开始行，从0行开始
		int size = Container.PAGE_SIZE;// 分页大小
		int count = mysqlDao.getCount();// 总记录数

		// 每次同步数据前先删除oracle表中的数据
		oracleDao.deleteData();

		for (; begin < count; begin += size) {
			List<Exercises> exercriseList = mysqlDao.getAllByPage(begin, size);
			oracleDao.batchInsert(exercriseList);
		}

		Long endTime = System.currentTimeMillis();
		logger.info("同步数据：" + count + "条，用时：" + (endTime - beginTime) + "ms");
		return "同步数据：" + count + "条，用时：" + (endTime - beginTime) + "ms";
	}

	// --------------------------------------------------------测试
	@RequestMapping("/test1")
	public String test1() {
		return "123";
	}
	// ------------------------------------------------------------

}
