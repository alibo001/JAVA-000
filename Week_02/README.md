> **1.**使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。
>
> **2.**使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
>
> 根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。

**1.GC数据**

以下数据:GC次数,生成对象数, GC用时 都是都是几次的平均数:

|      | -Xmx  -Xms | youngGC次数 | fullGC次数 | 生成对象数<br />(省略后2位) | GC用时                      |
| ---- | ---------- | ----------- | ---------- | --------------------------- | --------------------------- |
| 串行 | 512m       | 18          | 7          | 113xx                       | 22-30毫秒  fullGC:37-48毫秒 |
|      | 1g         | 10          | 1          | 149xx                       | 41-60毫秒  fullGC:45-49毫秒 |
|      | 2g         | 6           | 0          | 145xx                       | 72-92毫秒                   |
|      | 4g         | 2           | 0          | 125xx                       | 114-150毫秒                 |
|      |            |             |            |                             |                             |
| 并行 | 512m       | 27          | 14         | 94xx                        | 4-15毫秒  fullGC:39-77毫秒  |
|      | 1g         | 29          | 2          | 167xx                       | 12-19毫秒  fullGC:56毫秒    |
|      | 2g         | 14          | 0          | 190xx                       | 25-35毫秒                   |
|      | 4g         | 4           | 0          | 195xx                       | 37-49毫秒                   |
|      |            | ParNew      | CMS        |                             |                             |
| CMS  | 512m       | 20          | 9          | 110xx                       | 10-32毫秒                   |
|      | 1g         | 13          | 3          | 140xx                       | 16-57毫秒                   |
|      | 2g         | 6           | 1          | 143xx                       | 27-90毫秒                   |
|      | 4g         | 5           | 0          | 142xx                       | 32-92毫秒                   |
|      |            |             |            |                             |                             |
| G1   | 512m       |             |            | 85xx                        | 5-10毫秒                    |
|      | 1g         |             |            | 11xxx                       | 6-16毫秒                    |
|      | 2g         | 12          | 0          | 118xxx                      | 10-17毫秒                   |
|      | 4g         | 13          | 0          | 139xxx                      | 14-32毫秒                   |

**2. 测压数据**

RPS: 

|      | 512m | 1g   | 4g   |
| ---- | ---- | ---- | ---- |
| 并行 | 3616 | 5065 | 4594 |
| CMS  | 1986 | 2067 | 2078 |

最大时延:

|      | 512m  | 1g    | 4g    |
| ---- | ----- | ----- | ----- |
| 并行 | 57ms  | 100ms | 77ms  |
| CMS  | 109ms | 116ms | 135ms |

**分析:**

 在总体上, 随着堆内存的增大, GC频率减少, 生成的对象数量增加, 而且每次GC的时间也在增加, 因为内存变大, GC需要操作更多的空间. 

横向比较4个GC, 串行GC生成对象次数最少, GC暂停时间最长, 这是因为串行GC是暂停所有业务线程, 用单线程去做GC, 而且随着内存增加, GC暂停时间越长达到百毫秒级别.

并行GC和CMS,并行GC注重吞吐量, CMS注重降低业务系统延迟.  在内存小的时候, 并行GC执行的次数更多, 由于需要暂停业务线程, 所以生成的对象数比CMS少. 但随着内存增加, 并行GC下生成的对象超过CMS. 并行GC的吞吐量超越CMS. 

测压数据上看, 并行GC的吞吐量一直比CMS要大, 而且并行的时延比CMS低, 并行GC还是比CMS有优势的.

在相同堆内存时, G1的GC次数比其他GC要多, 并且随着堆内存增大,GC划分的region增多, GC次数明显比其他GC多, 但是G1的每次GC时间是相对比较短的. 延迟很低. 对延迟敏感的系统可以考虑用G1.





> **写一段代码，使用 HttpClient 或 OkHttp 访问 [http://localhost:8801 ](http://localhost:8801/)，代码提交到 Github。**

```java
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
```

