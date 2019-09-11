package priv.bingfeng.stjava.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.bingfeng.stjava.common.RunApplication;
import priv.bingfeng.stjava.common.support.DaoQueryCriterion;
import priv.bingfeng.stjava.sharding.mapper.UserMapper;
import priv.bingfeng.stjava.sharding.mapper.entity.User;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@MapperScan("priv.bingfeng.stjava.sharding.mapper")
public class MoveTableOrganToTaskId implements RunApplication {

    @Resource
    private UserMapper userDao;

    @Override
    public void execute() {
        User user = new User();
        user.setUserId(4);
        user.setUserName("bf");
        user.setAge(3);

        userDao.createTableIfNotExists(user.getUserId() / 10);
        userDao.insert(user);

        DaoQueryCriterion queryCriterion = new DaoQueryCriterion(user.getUserId() / 10, null);
        List<User> users = userDao.listBySuffix(queryCriterion);
        System.out.println(users.size());

        List<User> userList = userDao.selectList(new QueryWrapper<User>().eq("userId", user.getUserId()));
        System.out.println(userList.size());
    }

    public static void main(String[] args) {
        RunApplication.run(MoveTableOrganToTaskId.class, args);
    }

}
