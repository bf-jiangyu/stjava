package priv.bingfeng.stjava.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import priv.bingfeng.stjava.common.support.DaoQueryCriterion;
import priv.bingfeng.stjava.sharding.mapper.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    void createTableIfNotExists(int userId);

    List<User> listBySuffix(DaoQueryCriterion queryCriterion);

}
