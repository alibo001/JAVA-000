package com.example.week11.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: alibobo
 * @Date: 2021/01/08
 * @Description: 订单
 **/
@Data
public class Order implements Serializable {

    private int oid;
    private String orderName;

    public Order(int oid, String orderName) {
        this.oid = oid;
        this.orderName = orderName;
    }

    public Order() {
    }
}
