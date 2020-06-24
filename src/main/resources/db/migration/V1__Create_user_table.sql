create table user2
(
    id           bigint(10)   not null comment 'id'
        primary key,
    icon         varchar(255) null comment '头像',
    name         varchar(20)  not null comment '用户名',
    pwd          varchar(40)  null comment '密码',
    mobile       varchar(11)  null comment '手机号',
    email        varchar(40)  null comment '邮箱',
    qq           varchar(15)  null comment 'QQ号',
    bio          varchar(255)  null comment '简介',
    gmt_created  datetime     not null on update CURRENT_TIMESTAMP comment '创建时间',
    gmt_modified datetime     not null on update CURRENT_TIMESTAMP comment '修改时间'
);