alter table notification
	add notifier_name varchar(100) not null after notifier;

alter table notification modify receiver bigint not null after notifier_name;

alter table notification
	add outer_title varchar(255) not null;