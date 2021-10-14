package com.systemParameter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SystemParameterRedisDAO implements SystemParameterDAO_interface{
//	private static JedisPool pool = MyUtil.getJedisPool();
	
	@Override
	public void sysParaInsertOrUpdate(SysParaVO vo) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.set(vo.getParameter(), vo.getValue());
		if(jedis != null) {
			jedis.close();
		}
	}

	@Override
	public long delSysPara(String key) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		if(jedis != null) {
			jedis.close();
		}
		return jedis.del(key);
	}

	@Override
	public SysParaVO selectOne(String key) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		SysParaVO vo = new SysParaVO();
		vo.setParameter(key);
		vo.setValue(jedis.get(key));
		
		if(jedis != null) {
			jedis.close();
		}
		
		return vo;
	}

	@Override
	public List<SysParaVO> selectAll(List<String> keys) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		SysParaVO vo = null;
		List<SysParaVO> list = new ArrayList<SysParaVO>();
		
		Iterator<String> it = keys.iterator();
		String key = "";
		while(it.hasNext()) {
			vo = new SysParaVO();
			key = it.next();
			vo.setParameter(key);
			vo.setValue(jedis.get(key));
			list.add(vo);
		}
		
		if(jedis != null) 
			jedis.close();
		
		return list;
	}
	
	public static void main(String[] args) {
		SystemParameterRedisDAO dao = new SystemParameterRedisDAO();
		SysParaVO vo = new SysParaVO();
		vo.setParameter("test3");
		vo.setValue("測試3");
		List<String> keys = new ArrayList<String>();
		keys.add("test");
		keys.add("test1");
		keys.add("test2");
		List<SysParaVO> list = dao.selectAll(keys);
		for(SysParaVO vo1 : list) {
			System.out.println(vo1);
		}
	}
}
