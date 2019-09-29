package priv.bingfeng.stjava.springboot.solr;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

@Data
public class SolrAsset {

    @Field private String assetCode;// 索引主键 website_assetCode_taskId
    @Field private String assetId;
    @Field private String content;
    @Field private long createDate;
    @Field private int type;// 0正文; 1评论

    @Field private int likeCount;
    @Field private int rebackCount;
    @Field private int repostCount;
    @Field private int forwardCount;

    @Field private String commentCode = "0";
    @Field private long createUserCode;

    @Field private int websiteId;
    @Field private int taskId;

}
