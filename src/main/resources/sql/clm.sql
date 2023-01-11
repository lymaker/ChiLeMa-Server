DROP DATABASE IF EXISTS clm;

CREATE DATABASE clm;

USE clm;

CREATE TABLE clm_user
(
    id               VARCHAR(50) PRIMARY KEY COMMENT '用户id',
    nickname         VARCHAR(20)        NOT NULL COMMENT '用户昵称',
    username         VARCHAR(15) UNIQUE NOT NULL COMMENT '用户名',
    password         VARCHAR(200)       NOT NULL COMMENT '密码',
    email            VARCHAR(50)        NOT NULL COMMENT '邮箱',
    phone            VARCHAR(11)        NOT NULL COMMENT '手机号',
    avatar_image_url VARCHAR(255)       NOT NULL COMMENT '用户头像',
    money            DOUBLE    DEFAULT 0 COMMENT '余额',
    register_time    TIMESTAMP DEFAULT NOW() COMMENT '用户注册时间'
) COMMENT '用户表';

CREATE TABLE clm_role
(
    id       TINYINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色id',
    nickname VARCHAR(20) NOT NULL COMMENT '角色昵称'
) COMMENT '角色表';

CREATE TABLE clm_user_role
(
    user_id VARCHAR(50) NOT NULL COMMENT '用户id',
    role_id TINYINT     NOT NULL COMMENT '角色id',
    UNIQUE INDEX `user_role_unique` (user_id, role_id),
    CONSTRAINT clm_user_role_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_user_role_clm_role_role_id FOREIGN KEY (role_id) REFERENCES clm_role (id)
) COMMENT '用户角色表';

CREATE TABLE clm_user_address
(
    id          VARCHAR(50) PRIMARY KEY COMMENT '地址id',
    user_id     VARCHAR(50)  NOT NULL COMMENT '用户id',
    latitude    FLOAT        NOT NULL COMMENT '经度',
    longitude   FLOAT        NOT NULL COMMENT '纬度',
    door_number VARCHAR(100) NOT NULL COMMENT '门牌号',
    phone       VARCHAR(11)  NOT NULL COMMENT '手机号',
    honorific   VARCHAR(2) DEFAULT '先生' COMMENT '性别',
    tag         VARCHAR(5) COMMENT '标签',
    CONSTRAINT clm_user_address_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id)
) COMMENT '用户地址表';

CREATE TABLE clm_user_address_default
(
    user_id    VARCHAR(50) PRIMARY KEY COMMENT '用户id',
    address_id VARCHAR(50) UNIQUE COMMENT '地址id',
    CONSTRAINT clm_user_address_default_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_user_address_default_clm_user_address_address_id FOREIGN KEY (address_id) REFERENCES clm_user_address (id)
) COMMENT '用户默认地址表';

CREATE TABLE clm_store
(
    id                   VARCHAR(50) PRIMARY KEY COMMENT '商店id',
    boss_id              VARCHAR(50)  NOT NULL COMMENT '店主id',
    name                 VARCHAR(20)  NOT NULL COMMENT '店铺名称',
    cover_image_url      VARCHAR(255) NOT NULL COMMENT '店铺封面',
    background_image_url VARCHAR(255) NOT NULL COMMENT '背景图片',
    description          TEXT         NOT NULL COMMENT '店铺描述',
    latitude             FLOAT        NOT NULL COMMENT '经度',
    longitude            FLOAT        NOT NULL COMMENT '纬度',
    open_time            TIMESTAMP    NOT NULL COMMENT '开始营业时间',
    close_time           TIMESTAMP    NOT NULL COMMENT '停止营业时间',
    CONSTRAINT clm_store_clm_user_boss_id FOREIGN KEY (boss_id) REFERENCES clm_user (id)
) COMMENT '店铺表';

CREATE TABLE clm_store_category
(
    id              VARCHAR(50) PRIMARY KEY COMMENT 'id',
    cover_image_url VARCHAR(255) NOT NULL COMMENT '封面',
    title           VARCHAR(10)  NOT NULL COMMENT '标题',
    is_sub          BOOLEAN DEFAULT FALSE COMMENT '是否为子分类'
) COMMENT '店铺分类';

CREATE TABLE clm_store_category_connect
(
    store_id    VARCHAR(50) NOT NULL COMMENT '店铺id',
    category_id VARCHAR(50) NOT NULL COMMENT '分类id',
    CONSTRAINT clm_store_category_connect_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_store (id),
    CONSTRAINT clm_store_category_connect_clm_store_category_category_id FOREIGN KEY (category_id) REFERENCES clm_store_category (id)
) COMMENT '店铺分类中间表';

CREATE TABLE clm_store_commodity
(
    id                    VARCHAR(50) PRIMARY KEY COMMENT '商品id',
    store_id              VARCHAR(50)  NOT NULL COMMENT '商店id',
    name                  VARCHAR(30)  NOT NULL COMMENT '商品名称',
    cover_image_url       VARCHAR(255) NOT NULL COMMENT '封面图片链接',
    description           TEXT         NOT NULL COMMENT '商品描述',
    main_ingredients      VARCHAR(30)  NOT NULL COMMENT '主料',
    secondary_ingredients VARCHAR(30)  NOT NULL COMMENT '辅料',
    taste                 VARCHAR(30)  NOT NULL COMMENT '口味',
    temperature           VARCHAR(30)  NOT NULL COMMENT '凉热',
    feel                  VARCHAR(30)  NOT NULL COMMENT '口感',
    CONSTRAINT clm_store_commodity_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_store (id)
) COMMENT '商品信息表';

CREATE TABLE clm_store_commodity_key
(
    id           VARCHAR(50) PRIMARY KEY COMMENT '规格名称id',
    commodity_id VARCHAR(50) NOT NULL COMMENT '商品id',
    name         VARCHAR(30) NOT NULL COMMENT '名称',
    CONSTRAINT clm_store_commodity_key_clm_store_commodity_commodity_id FOREIGN KEY (commodity_id) REFERENCES clm_store_commodity (id)
) COMMENT '商品规格键名表';

CREATE TABLE clm_store_commodity_value
(
    id     VARCHAR(50) PRIMARY KEY COMMENT '规格名称id',
    key_id VARCHAR(50) NOT NULL COMMENT '规格键名id',
    name   VARCHAR(30) NOT NULL COMMENT '名称',
    CONSTRAINT clm_store_commodity_value_clm_store_commodity_key_key_id FOREIGN KEY (key_id) REFERENCES clm_store_commodity_key (id)
) COMMENT '商品规格参数表';

CREATE TABLE clm_store_commodity_spec
(
    id           VARCHAR(50) PRIMARY KEY COMMENT 'sku id',
    commodity_id VARCHAR(50) NOT NULL COMMENT '商品id',
    spec         TEXT        NOT NULL COMMENT '规格',
    stock        INT         NOT NULL COMMENT '库存',
    price        FLOAT       NOT NULL COMMENT '价格',
    CONSTRAINT clm_store_commodity_spec_clm_store_commodity_commodity_id FOREIGN KEY (commodity_id) REFERENCES clm_store_commodity (id)
) COMMENT '商品表';

CREATE TABLE clm_coupons
(
    id          VARCHAR(50) PRIMARY KEY COMMENT '优惠卷id',
    name        VARCHAR(30) NOT NULL COMMENT '优惠卷名称',
    type        TINYINT DEFAULT 1 COMMENT '优惠卷类型，0代表官方发放，1代表店铺发放',
    store_id    VARCHAR(50) COMMENT '店铺id，官方卷可为null',
    full        INT         NOT NULL COMMENT '满减',
    subtract    INT         NOT NULL COMMENT '满减',
    `limit`     TINYINT DEFAULT 1 COMMENT '领取限制，默认每人领取一张',
    duration    INT         NOT NULL COMMENT '有效时长，-1代表无期限',
    quantity    INT         NOT NULL COMMENT '优惠卷数量，-1代表无限',
    description VARCHAR(200) COMMENT '优惠卷描述',
    CONSTRAINT clm_coupons_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_store (id)
) COMMENT '优惠卷表';

CREATE TABLE clm_user_coupons
(
    id           VARCHAR(50) PRIMARY KEY COMMENT '用户优惠卷id',
    user_id      VARCHAR(50) NOT NULL COMMENT '用户id',
    coupons_id   VARCHAR(50) NOT NULL COMMENT '优惠卷id',
    receive_time TIMESTAMP DEFAULT NOW() COMMENT '领取时间',
    expire_time  TIMESTAMP   NOT NULL COMMENT '到期时间，-1代表永久',
    is_use       BOOL      DEFAULT FALSE COMMENT '是否已使用',
    CONSTRAINT clm_user_coupons_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_user_coupons_clm_coupons_coupons_id FOREIGN KEY (coupons_id) REFERENCES clm_coupons (id)
) COMMENT '用户优惠卷表';

CREATE TABLE clm_order
(
    id         VARCHAR(50) PRIMARY KEY COMMENT '订单号id',
    user_id    VARCHAR(50) NOT NULL COMMENT '用户id',
    store_id   VARCHAR(50) NOT NULL COMMENT '店铺id',
    address_id VARCHAR(50) NOT NULL COMMENT '地址id',
    coupons_id VARCHAR(50) COMMENT '优惠卷id',
    remark     VARCHAR(200) COMMENT '下单备注',
    start_time TIMESTAMP DEFAULT NOW() COMMENT '订单开始时间',
    end_time   TIMESTAMP COMMENT '订单结束时间',
    status     TINYINT     NOT NULL COMMENT '订单状态',
    CONSTRAINT clm_order_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_order_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_user (id),
    CONSTRAINT clm_order_clm_user_address_address_id FOREIGN KEY (address_id) REFERENCES clm_user (id),
    CONSTRAINT clm_order_clm_user_coupons_coupons_id FOREIGN KEY (coupons_id) REFERENCES clm_user_coupons (id)
) COMMENT '用户订单表';

CREATE TABLE clm_order_item
(
    order_id          VARCHAR(50) NOT NULL COMMENT '订单号id',
    commodity_spec_id VARCHAR(50) NOT NULL COMMENT '商品规格id',
    quantity          INT         NOT NULL COMMENT '下单数量',
    CONSTRAINT clm_order_item_clm_order_order_id FOREIGN KEY (order_id) REFERENCES clm_order (id),
    CONSTRAINT clm_order_item_clm_store_commodity_spec_commodity_spec_id FOREIGN KEY (commodity_spec_id) REFERENCES clm_store_commodity_spec (id)
) COMMENT '用户订单项';

CREATE TABLE clm_user_cart
(
    user_id           VARCHAR(50) NOT NULL COMMENT '用户id',
    store_id          VARCHAR(50) NOT NULL COMMENT '店铺id',
    commodity_spec_id VARCHAR(50) NOT NULL COMMENT '商品规格id',
    CONSTRAINT clm_user_cart_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_user_cart_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_store (id),
    CONSTRAINT clm_user_cart_clm_store_commodity_spec_commodity_spec_id FOREIGN KEY (commodity_spec_id) REFERENCES clm_store_commodity_spec (id)
) COMMENT '用户购物车';

CREATE TABLE clm_comment
(
    id          VARCHAR(50) PRIMARY KEY COMMENT '评价id',
    user_id     VARCHAR(50) NOT NULL COMMENT '用户id',
    store_id    VARCHAR(50) NOT NULL COMMENT '店铺id',
    content     TEXT        NOT NULL COMMENT '评价内容',
    star        FLOAT       NOT NULL COMMENT '星级',
    create_time TIMESTAMP DEFAULT NOW() COMMENT '评价时间',
    CONSTRAINT clm_comment_clm_user_user_id FOREIGN KEY (user_id) REFERENCES clm_user (id),
    CONSTRAINT clm_comment_clm_store_store_id FOREIGN KEY (store_id) REFERENCES clm_store (id)
) COMMENT '评价表';

CREATE TABLE clm_comment_image
(
    comment_id VARCHAR(50) PRIMARY KEY COMMENT '评价id',
    image_url  VARCHAR(200) NOT NULL COMMENT '图片url',
    CONSTRAINT clm_comment_image_clm_comment_comment_id FOREIGN KEY (comment_id) REFERENCES clm_comment (id)
) COMMENT '评论图片表';

-- 数据
INSERT INTO clm_role
VALUES (1, 'manager');
INSERT INTO clm_role
VALUES (2, 'consumer');
INSERT INTO clm_role
VALUES (3, 'provider');