package lllr.test.breast.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;


    //get
    public  <T> T get(KeyPrefix keyPrefix,String key,Class<T> clazz)
    {
          Jedis jedis = null;
          try{
              jedis = jedisPool.getResource();
              String realKey = keyPrefix.getPrefix() + key;
              String res = jedis.get(realKey);
              T t = StringToBean(res,clazz);
              return t;
          }finally {
              returnToPool(jedis);
          }
    }

    //set
    public <T> boolean set(KeyPrefix keyPrefix,String key,T value)
    {
        Jedis jedis = jedisPool.getResource();
        try{
             String strValue = BeanToString(value);
             if(strValue == null || strValue.length() <= 0)
                 return false;
             String realKey = keyPrefix.getPrefix()+key;
             Integer exSeconds = keyPrefix.expireSeconds();
             if(exSeconds <= 0)
                jedis.set(realKey,strValue);
             else
                 jedis.setex(realKey,exSeconds,strValue);
             return true;
        }finally {
            returnToPool(jedis);
        }
    }



    //bean 转换成 value
    public <T> String BeanToString(T value)
    {
        if(value == null )
            return null;
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class)
            return ""+value;
        else if(clazz == String.class)
            return (String)value;
        else if(clazz == long.class || clazz == Long.class)
            return ""+value;
        else
            return JSON.toJSONString(value);
    }

    //value 转换成 bean
    public <T> T StringToBean(String res,Class<T> clazz)
    {
        if(res == null || res.length()==0 || clazz == null)
            return  null;
        if(clazz == int.class || clazz == Integer.class)
            return (T)Integer.valueOf(res);
        else  if(clazz == String.class)
            return (T)res;
        else if(clazz == long.class || clazz == Long.class)
            return (T)Long.valueOf(res);
        else
            return (T)JSON.toJavaObject(JSON.parseObject(res), clazz);
    }

    //回收资源
    public void returnToPool(Jedis jedis)
    {
        if(jedis!=null)
            jedis.close();
    }
}
