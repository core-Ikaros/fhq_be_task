# show databases;

查看所有数据库

# create database ...;

创建对应名称的数据库

# use ...;

使用对应名称数据库

# create tables ...(...);

在当前使用库里创建对应名称的表，也就是说要事先使用库。括号内为字段定义

# describe tableName;

查看表的字段结构

# select * from tableName (where ...);

查看选中的列，可用 where 加入筛选条件
*表示全部

# insert into tableName(...) values (...);

在表中插入行，两个括号分别是字段和对应填写数据

# update tableName set ...=... where ...;

更新筛选出的行里对应字段的数据

# drop table/database ...;

删除表/库

大部分操作是在GORM里实现的，这里只是对MySQL增删改查操作的简单了解