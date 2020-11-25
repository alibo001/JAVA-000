-- 用户表
create table table_user
(
    uid         char(36) primary key comment '用户id',
    username    varchar(50) unique not null comment '用户名',
    password    varchar(128)       not null comment '密码',
    phone       varchar(20) comment '手机号',
    email       varchar(50) comment '邮箱',
    gender      varchar(1) comment '性别',
    birthday    varchar(36) comment '出生日期',
    create_time varchar(36)        not null comment '创建时间',
    update_time varchar(36) comment '更新时间',
    is_delete   varchar(1) comment '是否删除'
);

-- 商品表
create table table_goods
(
    id         char(36) primary key comment '商品id',
    seller_id  char(36) not null comment '商家id',
    goods_name varchar(100) comment '商品名',
    price      decimal(10, 2) comment '价格',
    is_delete  varchar(1) comment '是否删除'
);

-- 订单表
create table table_order
(
    order_id          char(36) not null comment '订单id',
    user_id           char(36) comment '下单用户id',
    rec_name          varchar(50) comment '收货人',
    rec_phone         varchar(12) comment '收货人手机',
    rec_addr          varchar(100) comment '收货人地址',
    payment           decimal(20, 2) comment '实付金额',
    goods_id          char(36) comment '商品id',
    goods_snapshot_id char(36) comment '商品快照id',
    status            varchar(1) comment '状态',
    create_time       varchar(36) comment '订单创建时间',
    update_time       varchar(36) comment '订单更新时间',
    payment_time      varchar(36) comment '付款时间',
    consign_time      varchar(36) comment '发货时间',
    end_time          varchar(36) comment '交易完成时间',
    close_time        varchar(36) comment '交易关闭时间',
    shipping_name     varchar(20) comment '物流名称',
    shipping_code     varchar(20) comment '物流单号',
    expire            varchar(36) comment '过期时间，定期清理',
    is_delete         varchar(1) comment '是否删除'
);


