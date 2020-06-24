create table notification
(
	id bigint auto_increment comment '主键',
	notifier bigint not null comment '通知人',
	outer_id bigint not null comment '是分享或者回复的id',
	type int not null comment '用于区分评论还是回复',
	gmt_created datetime not null,
	status int default 0 not null comment '已读/未读',
	constraint notification_pk
		primary key (id)
);