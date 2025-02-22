# JavaWeb

## 网络

### Socket

TCP建立过程

数据传输
InputStreamReader

OutputStreamWriter

BufferReader 缓冲流可以一行一行读

setSoTimeout 设定时间

setKeepAlive 当对方没有发消息超过一定时间，那么会发送一个ack探测包探测双方的TCP/IP连接是否有效

浏览器访问socket服务器

## 数据库

MySQL

规范化

每一列都是不可分割的基础类型

表中必须存在主键

所有信息都不传递依赖于主键

BCNF 关键字段不能互相决定

### SQL语句

不区分大小写

#### DDL

CREATE DATABASE 创建数据库

DROP DATABASE 删除数据库

DEFAULT CHARSET ...COLLATE ...设定编码格式

TABLE 表

数据类型

varchar(n)长度不固定，但不超过n，不会用空格填充，一般常用

列级约束条件 

primary key 主键 foreign key 外键 not null 

default 默认

表的创建

create table 表名(名 类型 列级约束条件)

表级约束条件

[CONSTRAINT<外键名>]FOREIGN KEY字段名[,字段名2,...]REFERENCES<主表名>主键列1[,主键列2,...]

修改表

ADD 加列

DROP COLUMN 删列

ALTER COLUMN 改列属性

#### DML

##### 插入数据

INSERT INTO 表名 VALUES（值1，2，3）

INSERT INTO 表名（列名1，2） VALUES（值1，2），（），（）

##### 修改数据

UPDATA 表名 SET 列名 WHERE 条件

##### 删除数据

DELETE 表名 WHERE 条件

#### DQL

查询

SELECT 列名 FROM 表名 WHERE 条件

distinct去重

##### 常用查询条件

大小比较

in ， not in

like % 模糊查询

##### 排序查询

ORDER BY

ASC 升序 DESC 降序

##### 聚集函数

count(*) 统计行数

count (distinct 列名) 统计值的总数

sum

avg

max

min

##### 分组和分页查询

与聚集函数联合使用

GROUP BY

HAVING限制分组条件

LIMIT 数量 限制查询数量 

分页 LIMIT 起始位置 数量

#####  多表查询

外连接查询(取交集)

inner join ... on 内连接

left join ...on 左连接 不仅返回交集并输出左表信息

嵌套查询

SELECT ... FROM ... WHERE ... in (SELECT ...)

#### DCL

创建用户

CREATE USER 用户名 identified by 密码

用户授权

grant ... on ... to

revoke 收回权限

