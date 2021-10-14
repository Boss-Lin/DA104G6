package com.inform.model;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class InformRedisDAO implements InformDAO_interface{
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//	private static JedisPool pool = MyUtil.getJedisPool();
	
	@Override
	public void insert(InformVO vo) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		String key = vo.getMem_no();
		String value = gson.toJson(vo);
		
		jedis.lpush(key, value);
		if(jedis != null)
			jedis.close();
	}

	@Override
	public void delInform(String key) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		jedis.del(key);
		if(jedis != null) 
			jedis.close();
	}

	@Override
	public InformVO findByTime(String mem_no, LocalDateTime time) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		InformVO returnvo = null;
		
		Type collectionType = new TypeToken<List<InformVO>>() {
		}.getType();
		
		List<String> list = jedis.lrange(mem_no, 0, jedis.llen(mem_no)-1);
		System.out.println(list.toString());
		List<InformVO> informlist = gson.fromJson(list.toString(), collectionType);
		
		for(InformVO vo : informlist) {
			if(time.equals(vo.getNotice_time())) {
				returnvo = vo;
			}
		}
		
		if(jedis != null)
			jedis.close();
		return returnvo;
	}

	@Override
	public List<InformVO> findAll(String key) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		Type collectionType = new TypeToken<List<InformVO>>() {
		}.getType();
		
		List<String> list = jedis.lrange(key, 0, jedis.llen(key)-1);
		List<InformVO> informlist = gson.fromJson(list.toString(), collectionType);
		
		if(jedis != null)
			jedis.close();
		return informlist;
	}
	
	public static void main(String[] args) {
		InformRedisDAO dao = new InformRedisDAO();
		
		InformVO vo = new InformVO();
//		vo.setMem_no("m0001");
//		vo.setNotice_content("測試拉拉拉拉");
//		vo.setNotice_time(LocalDateTime.now());
//		vo.setNotice_title("測試4");
//		vo = dao.findByTime("m0001", LocalDateTime.of(2019, 12, 22, 11, 6, 54, 748000000));
		
		List<InformVO> list = dao.findAll("m0001");
		for(InformVO votest : list)
			System.out.println(votest);
		
		
	}
}
