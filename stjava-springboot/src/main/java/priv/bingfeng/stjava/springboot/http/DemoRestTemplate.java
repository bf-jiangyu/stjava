package priv.bingfeng.stjava.springboot.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DemoRestTemplate<main> {

    public static SimpleClientHttpRequestFactory HTTP_CLIENT = new SimpleClientHttpRequestFactory();

    public static HttpEntity<String> HTTP_BASE_ENTITY;
    static {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.12) Gecko/20101026 Firefox/3.6.12 GTB7.1");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.add("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
        headers.add("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        HTTP_BASE_ENTITY = new HttpEntity<>(headers);
    }

    @Value("${constconfig.http_proxy}")
    public void setHttpProxy(boolean value) {
        if (value) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080));
            HTTP_CLIENT.setProxy(proxy);
        }
    }

    public static void downFile() {
        File file = new RestTemplate().execute("", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
    }

    public static void downByte() {
        RestTemplate restTemplate = new RestTemplate(HTTP_CLIENT);
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> response = restTemplate.exchange("", HttpMethod.GET, HTTP_BASE_ENTITY, byte[].class);
        response.getStatusCode();
        response.getBody();
    }

    public static void downString() {
        String content = new RestTemplate().exchange("http://localhost:21234/get_user", HttpMethod.GET, HttpEntity.EMPTY, String.class).getBody();
        System.out.println(content);
    }

    public static void post() {
        MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
        postData.add("accountJson", "hello world");

        String content = new RestTemplate().exchange("http://localhost:21234/lbs_back/WeixinOpen!pushLbsData.action", HttpMethod.POST, new HttpEntity<>(postData, null), String.class).getBody();
        System.out.println(content);
    }

    public static void main(String[] args) {
        post();
    }

}
