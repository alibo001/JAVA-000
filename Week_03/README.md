**作业:** 

> 实现一个简单的网关
>
> 1,基础版: nettyServer使用httpClient访问后端服务,拿到返回的数据,返回给浏览器
>
> 2,升级版: 把httpClient替换成nettyClient
>
> 3,高级版: 实现过滤器,在请求头里面添加一个key-value
>
> 4.终极版: 实现一个路由,多代理几个服务,随机选择一个服务转发



**我的实现:**

> 我的版本用的是nettyClient, 加上了 过滤器 和 路由转发.

**作业说明:**

> 1.路由的位置不是太确定,目前是在创建client之前, 随机获取一个代理地址,然后new一个Client

> 2.过滤器直接在原request里添加了一个header,直接发给后端服务,并没有创建新的request.

> 3.通过nettyClient调用返回后,没有新建response, 直接用的返回的response.

**缺陷:**

> 每次请求都创建一个新的nettyClient, 压测数据只有100多rps, 后台已经崩了, 报错端口已经占用.
>
> 貌似应该使用nettyClient连接池, 目前没有实现. 



