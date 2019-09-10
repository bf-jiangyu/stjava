package priv.bingfeng.stjava.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import priv.bingfeng.stjava.springboot.RunApplication;

@SpringBootApplication
public class RedisDemo implements RunApplication {

    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public RedisDemo(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void execute() {
        redisTemplate.opsForValue().set("hello", "world");
        String s = redisTemplate.opsForValue().get("hello");
        System.out.println(s);
    }

    public static void main(String[] args) {
        RunApplication.run(RedisDemo.class, args);
    }

}
