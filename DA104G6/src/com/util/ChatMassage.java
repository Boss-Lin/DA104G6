package com.util;

import redis.clients.jedis.Jedis;

import java.util.List;

public class ChatMassage {
//	private static JedisPool pool = MyUtil.getJedisPool();
	
//	=============================================單人聊天訊息(包含客服)..copy老師的===============================================
	public static List<String> getHistoryMsg(String sender_mem_no, String receiver_mem_no) {
		String key = new StringBuilder(sender_mem_no).append(":").append(receiver_mem_no).toString();
		Jedis jedis = new Jedis("localhost", 6379);
//		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, jedis.llen(key) - 1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender_mem_no, String receiver_mem_no, String message) {
		String senderKey = new StringBuilder(sender_mem_no).append(":").append(receiver_mem_no).toString();
		String receiverKey = new StringBuilder(receiver_mem_no).append(":").append(sender_mem_no).toString();
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
//	=============================================單人聊天訊息(包含客服)..copy老師的==============================================
	
//	=======================================================揪團聊天存取====================================================
	public static List<String> getGroupMsg(String chatRoom){
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		List<String> groupMsg = jedis.lrange(chatRoom, jedis.llen(chatRoom)-6, jedis.llen(chatRoom) - 1);
		
		if(jedis != null)
			jedis.close();
		
		return groupMsg;
	}
	
	public static void saveGroupMsg(String chatRoom, String message) {
//		Jedis jedis = pool.getResource();
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.rpush(chatRoom, message);
		
		if(jedis != null)
			jedis.close();
	}
//	=======================================================揪團聊天存取====================================================
}
