package priv.bingfeng.stjava.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisBean {

    @Id
    String id;
    int database;
    String type;
    String key;
    long size_in_bytes;

    String encoding;
    int num_elements;
    int len_largest_element;
    String expiry;
    String value;

}
