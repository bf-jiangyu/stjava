package priv.bingfeng.stjava.mongo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import priv.bingfeng.stjava.common.RunApplication;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class TestMainMongo implements RunApplication {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void execute() {
        RedisBean bean = new RedisBean();
        bean.setKey("hello_world");
        bean.setId(bean.getKey());
        mongoTemplate.save(bean, "test");

        Query query = Query.query(Criteria.where("_id").is(bean.getKey()));
        List<RedisBean> test = mongoTemplate.find(query, RedisBean.class, "test");
        System.out.println(test.size());
    }

    public static void main(String[] args) {
        RunApplication.run(TestMainMongo.class, args);
    }

}
