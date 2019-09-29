package priv.bingfeng.stjava.common;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DemoResourceFile {

    public static void main(String[] args) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:keyword.json");
            byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            JSONArray.parseArray(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
