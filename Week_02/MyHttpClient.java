package com.example.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class MyHttpClient {

    private final CloseableHttpClient client = HttpClients.createDefault();
    private final String url;
    MyHttpClient(String url) {
        this.url = url;
    }

    // 发送请求
    public String request(String type) {
        HttpUriRequest request = null;
        if ("post".equalsIgnoreCase(type)) {
            request = new HttpPost(this.url);
        } else{
            request = new HttpGet(this.url);
        }
        try {
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    // 请求示例
    public static void main(String[] args) {
        // 请求路径
        String url = "http://localhost:8088/api/hello";
        MyHttpClient client = new MyHttpClient(url);
        String relult = client.request(url);
        System.out.println(relult);
    }
}
