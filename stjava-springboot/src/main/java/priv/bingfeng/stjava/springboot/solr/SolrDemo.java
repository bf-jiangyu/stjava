package priv.bingfeng.stjava.springboot.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.bingfeng.stjava.springboot.RunApplication;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
public class SolrDemo implements RunApplication {

    private static String SOLR_USERNAME = "";
    @Value("${const.solr.username}")
    public void setSolrUsername(String value) { SOLR_USERNAME = value; }

    private static String SOLR_PASSWORD = "";
    @Value("${const.solr.password}")
    public void setSolrPassword(String value) { SOLR_PASSWORD = value; }

    private static String SOLR_CORE = "";
    @Value("${const.solr.core}")
    public void setSolrCore(String value) { SOLR_CORE = value; }


    @Resource
    private SolrClient solrClient;

    @Override
    public void execute() {
        UpdateRequest solrAuth = this.getSolrAuth();

        SolrAsset solrAsset = new SolrAsset();
        solrAuth.add(new DocumentObjectBinder().toSolrInputDocument(solrAsset));

        try {
            solrAuth.commit(solrClient, SOLR_CORE);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }

    private UpdateRequest getSolrAuth() {
        UpdateRequest request = new UpdateRequest();
        request.setBasicAuthCredentials(SOLR_USERNAME, SOLR_PASSWORD);
        return request;
    }

    public static void main(String[] args) {
        RunApplication.run(SolrDemo.class, args);
    }

}
