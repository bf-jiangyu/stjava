package priv.bingfeng.stjava.sharding.mapper.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test_user")
public class User {

    @TableId
    private int userId;
    private String userName;
    private int age;

}
