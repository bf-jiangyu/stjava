package priv.bingfeng.stjava.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import priv.bingfeng.stjava.common.RunApplication;

import javax.annotation.Resource;
import java.time.Duration;

@SpringBootApplication
public class TestMainRedis implements RunApplication {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void execute() {
        stringRedisTemplate.opsForValue().set(
                "key", "hello world", Duration.ofSeconds(6));

        String value = stringRedisTemplate.opsForValue().get("key");
        System.out.println(value);
    }

    public static void main(String[] args) {
        RunApplication.run(TestMainRedis.class, args);
    }

}
