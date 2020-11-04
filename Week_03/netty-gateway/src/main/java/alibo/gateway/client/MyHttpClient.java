package alibo.gateway.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class MyHttpClient {

    private final CloseableHttpClient client = HttpClients.createDefault();
    private final String url;
    public MyHttpClient(String url) {
        this.url = url;
    }

    // 发送请求
    public HttpResponse request(String type) {
        HttpUriRequest request = null;
        if ("post".equalsIgnoreCase(type)) {
            request = new HttpPost(this.url);
        } else{
            request = new HttpGet(this.url);
        }
        try {
            return client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 请求示例
    public static void main(String[] args) {
        // 请求路径

    }
}
