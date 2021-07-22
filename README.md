# 玫瑰与鹿

## 工具
[Github OAuth](https://docs.github.com/en/developers/apps/building-github-apps/creating-a-github-app)
[OKHttp](https://square.github.io/okhttp/)
[MyBatis Generator](http://mybatis.org/generator/index.html)


## 脚本
```sql
create table question
(
	id int auto_increment,
	title varchar(50) null,
	description text null,
	gmt_create bigint null,
	gmt_modify bigint null,
	creator int null,
	comment_count int default 0 null,
	view_count int default 0 null,
	like_count int default 0 null,
	tag varchar(255) null,
	constraint question_pk
		primary key (id)
);

alter table user
    add avatar_url varchar(100) null;
    
    
```

```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate```


## 阅读数量
解决多并发下的问题: 
