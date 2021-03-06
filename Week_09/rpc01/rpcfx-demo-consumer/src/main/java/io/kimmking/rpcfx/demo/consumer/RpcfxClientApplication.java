package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

	public static void main(String[] args) {

	    // 用户
		UserService userService = Rpcfx.create(UserService.class, "http://localhost:9291/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
        User user1 = userService.findByIdAndName(new Integer(3), "xxx");
        System.out.println(user1);
        User user2 = userService.findByIdAndName(3, "yyy");
        System.out.println(user2);

        // 订单
        OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:9291/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

	}

}
