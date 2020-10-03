## 码匠社区
## 资料
[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[Github deploy key](https://developer.github.com/v3/guides/mNfinf-deploy-keys/#deploy-keys)
[Bootstrap](https://v3.bootcss.com/getting-started)
[Github Oauth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)
[editor.md](https://pandao.github.io/editor.md/)
[UFile SDK](https://github.com/ucloud/ufile-sdk-java)
## 工具
[Git](https://git-scm.com/download)
[Visual Paradigm](https://www.visual-paradigm.com)
[mybatis](https://mybatis.org/mybatis-3/zh/index.html)
[PostMan](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
##脚本
```sql
CREATE TABLE `user`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'https://avatars1.githubusercontent.com/u/43441229?v=4' COMMENT '头像',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `pwd` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'QQ号',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `gmt_created` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
);


INSERT INTO `user` VALUES (1, 'https://avatars1.githubusercontent.com/u/43441229?v=4', 'ch33orange', NULL, NULL, NULL, NULL, NULL, '2019-11-02 13:29:22', '2019-11-02 13:29:22');
INSERT INTO `user` VALUES (2, 'https://avatars1.githubusercontent.com/u/43441229?v=4', 'bluer', '90dbab2eedc07567ebf02642f87542cf', '13128225306', '972579187@qq.com', NULL, NULL, '2019-11-03 20:52:11', '2019-11-03 20:52:11');

SET FOREIGN_KEY_CHECKS = 1;
```
```sql
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `gmt_created` datetime(0) NOT NULL,
  `gmt_modify` datetime(0) NOT NULL,
  `creator` int(11) NOT NULL,
  `comment_count` int(11) DEFAULT 0 COMMENT '0',
  `like_count` int(11) DEFAULT 0 COMMENT '0',
  `view_count` int(11) DEFAULT 0 COMMENT '0',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

create unique index question_creator_uindex
	on question (creator);
```

```sql
create table comment
(
	id BIGINT auto_increment primary key,
	parent_id BIGINT not null comment '父类id',
	type int not null comment '父类类型',
	commentator int not null comment '评论人id',
	gmt_created DATETIME not null comment '创建时间',
	gmt_modified DATETIME not null comment '修改时间',
	like_count bigint default 0 not null comment '点赞数',
    comment text null comment '回复内容'
);

```


```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
