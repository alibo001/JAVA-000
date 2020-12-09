package com.example.week08tx.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * author: alibobo
 * create: 2020-12-09 11:26
 * description:
 **/
public interface IOrderService {


    void tableMethod();

    @Transactional
    void cleanData();

    @Transactional
    void insert();

    void testRollback();

    @Transactional
    void delete();

    void query();
}
