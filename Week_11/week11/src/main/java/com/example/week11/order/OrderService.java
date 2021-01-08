package com.example.week11.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: alibobo
 * @Date: 2021/01/08
 * @Description: 订单服务
 **/
@Slf4j
@Service
public class OrderService {

    /**
     * 获取消息,并处理订单
     * @return
     */
    public Object getOrderMessage(Order order) {
        log.info("收到订单,订单id为{}",order.getOid());
        return order;
    }
}
