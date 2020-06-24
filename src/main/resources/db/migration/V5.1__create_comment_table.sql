create table comment
(
	id BIGINT auto_increment primary key,
	parent_id BIGINT not null comment '父类id',
	type int not null comment '父类类型',
	commentator int not null comment '评论人id',
	gmt_created DATETIME not null comment '创建时间',
	gmt_modified DATETIME not null comment '修改时间',
	like_count bigint default 0 not null comment '点赞数'
);

