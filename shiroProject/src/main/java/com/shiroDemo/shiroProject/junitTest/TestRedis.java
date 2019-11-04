package com.shiroDemo.shiroProject.junitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.shiroDemo.shiroProject.redisModule.RedisManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRedis {
	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";
	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;
	@Autowired
	private RedisManager redisManager;

	private String getRedisKickoutKey(String username) {
		return this.keyPrefix + username;
	}

	@Test
	public void test() {
		List lists = new ArrayList();
		lists.add("下标1");
		lists.add("下标2");
		Map maps = new HashMap<String,List>();
		redisManager.set("listsKey", lists);
		maps.put("maps", lists);
		redisManager.set("mapsKey", maps);
//		redisManager.del("test");
		System.out.println(JSONObject.toJSON(redisManager.get("listsKey")));
		System.out.println(JSONObject.toJSON(redisManager.get("mapsKey")));
		 Map json = (Map) JSONObject.parse(JSONObject.toJSONString(redisManager.get("mapsKey")));  
	        System.out.println("这个是用JSONObject类的parse方法来解析JSON字符串!!!");  
	        for (Object map : json.entrySet()){  
	        	List list = (List)((Map.Entry)map).getValue();
	        	System.out.println(list.get(0));
	        	System.out.println(((Map.Entry)map).getKey()+"  "+String.valueOf(((Map.Entry)map).getValue()));  
	        } 
		System.out.println("-----测试完毕-------");
	}
}
