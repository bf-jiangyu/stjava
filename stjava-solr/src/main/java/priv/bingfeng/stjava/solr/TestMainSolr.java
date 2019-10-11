package priv.bingfeng.stjava.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.bingfeng.stjava.common.RunApplication;
import priv.bingfeng.stjava.common.support.JsonUtil;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
public class TestMainSolr implements RunApplication {

    private static String SOLR_USERNAME = "";
    @Value("${const.solr.username}")
    public void setSolrUsername(String value) {
        SOLR_USERNAME = value;
    }

    private static String SOLR_PASSWORD = "";
    @Value("${const.solr.password}")
    public void setSolrPassword(String value) {
        SOLR_PASSWORD = value;
    }

    private static String SOLR_CORE = "";
    @Value("${const.solr.core}")
    public void setSolrCore(String value) {
        SOLR_CORE = value;
    }


    @Resource
    private SolrClient solrClient;

    @Override
    public void execute() {
        UpdateRequest solrAuth = this.getSolrAuth();

        SolrAsset solrAsset = JsonUtil.parseToObject("{\"assetCode\":\"11_58329cc6faa0526a24258902_0\",\"assetId\":\"7448260366028984888\",\"commentCode\":\"0\",\"content\":\"哈喽。po主这回又带来了两部超赞的动作悬疑韩剧。Healer和The K2。\\\\n1⃣️Healer。中文名：治愈者。动作悬疑搞笑爱情类韩剧 男主角池昌旭 女主角朴敏英，炒鸡萌。这部剧要说悬疑其实没有那么烧脑、大部分都在按剧情走。虽然感觉结局有些仓促、但是不拖沓、很好看。  概括一下这部剧就是：记者们和政府高层恶势力之间的斗争的同时来揭露并调查一些秘密的一部剧。男主扮演的是一个夜间跑腿人。随时随地变换身份.姿势等等。女主是一名记者。总之题材很新颖 po主超级喜欢看这部剧 虽然有时候一言不合就虐狗。哈哈。强烈推荐！！\\\\n另外剧中的那些ost很有感觉 尤其重点片段或者正能量片段时就会想起的那个轻音乐 真的是很有感觉。Healer-V.A.OST！这个这个。还有一首You-Ben.ost 虐狗片段ost 真的很有画面感的一首ost 墙裂推荐。去听去听！\\\\n2⃣️The K2 最近才完结的一部收视率爆点的韩剧。也是一部动作爱情悬疑类韩剧。男主依然是池昌旭 在剧中扮演保镖。女主林允儿。po主就觉得这里的男主是healer的强化版。更厉害了 哈哈。      这部剧画面感超级强。而且前几集的打斗场景还蛮紧张的。和healer有一丢丢类似吧。也超级好看。推荐类。\\\\n个人喜欢程度：Healer》The K2\",\"createDate\":0,\"createUserCode\":0,\"forwardCount\":0,\"likeCount\":0,\"rebackCount\":0,\"repostCount\":0,\"taskId\":1,\"type\":0,\"websiteId\":11}", SolrAsset.class);
        try {
            solrAuth.add(new DocumentObjectBinder().toSolrInputDocument(solrAsset));

            System.out.println(solrAuth.getDocuments().size());
            solrAuth.commit(solrClient, SOLR_CORE);
            System.out.println(solrAuth.getDocuments().size());
            solrAuth.clear();
            System.out.println(solrAuth.getDocuments().size());
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }

        try {
            SolrQuery query = new SolrQuery();
            query.set("q", "taskId:1");
            query.setStart(0).setRows(10);
            QueryResponse response = solrClient.query(SOLR_CORE, query);
            System.out.println(response.getResults().size());
            System.out.println(response.getResults().getNumFound());
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

    }

    private UpdateRequest getSolrAuth() {
        UpdateRequest request = new UpdateRequest();
        request.setBasicAuthCredentials(SOLR_USERNAME, SOLR_PASSWORD);
        return request;
    }

    public static void main(String[] args) {
        RunApplication.run(TestMainSolr.class, args);
    }

}
