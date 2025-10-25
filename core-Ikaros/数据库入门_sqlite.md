# 关系型数据库

用于储存，分类，调用各种数据

## 数据表

数据库中储存数据的结构，和结构体很相似

- 主键（primary key）

    一张表的唯一且不相同的标识符，Gorm默认字段名为ID的作为主键。

- 表名

    数据表の名，通常用映射的结构体的全小写+复数形式作为表名。
    也可以用Table更名。

        func (User) TableName() string {
            return "profiles"
        }

- 列名

    由字段名决定，也可以在结构体字段上加tag改名

        type Animal struct {
        AnimalId    int64     `gorm:"column:beast_id"`         // set column name to `beast_id`
        Birthday    time.Time `gorm:"column:day_of_the_beast"` // set column name to `day_of_the_beast`
        Age         int64     `gorm:"column:age_of_the_beast"` // set column name to `age_of_the_beast`
        }

- 时间戳

    - CreatedAt

        如果模型有 CreatedAt字段，该字段的值将会是初次创建记录的时间。

    - UpdatedAt

        如果模型有UpdatedAt字段，该字段的值将会是每次更新记录的时间。

    - DeletedAt

        如果模型有DeletedAt字段，调用Delete删除该记录时，将会设置DeletedAt字段为当前时间，而不是直接将记录从数据库中删除。

## CRUD操作

就是增查改删四种操作的首字母缩写（create,read,update,delete）

一个例子：

    package main

    import (
    "gorm.io/driver/sqlite"
    "gorm.io/gorm"
    )

    type Product struct {
    gorm.Model
    Code  string
    Price uint
    }

    func main() {
    db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})
    if err != nil {
        panic("failed to connect database")
    }

    // Migrate the schema
    db.AutoMigrate(&Product{})

    // Create
    db.Create(&Product{Code: "D42", Price: 100})

    // Read
    var product Product
    db.First(&product, 1) // find product with integer primary key
    db.First(&product, "code = ?", "D42") // find product with code D42

    // Update - update product's price to 200
    db.Model(&product).Update("Price", 200)
    // Update - update multiple fields
    db.Model(&product).Updates(Product{Price: 200, Code: "F42"}) // non-zero fields
    db.Model(&product).Updates(map[string]interface{}{"Price": 200, "Code": "F42"})

    // Delete - delete product
    db.Delete(&product, 1)
    }

### Create（创建）

`db.Create(&Product{Code:"D42",Price:"100"})`

在库中创建一个新的数据表并初始化，自动分配主键值

### Read（查询）

    var product Product
    db.First(&product, 1) // 查询主键值为1的数据表，将数据赋给product，包括主键值，以此联系两者
    db.First(&product, "code = ?", "D42") // 按条件查询

### Update（更新）

    db.Model(&product).Update("Price", 200)//更新对应字段
    db.Model(&product).Updates(Product{Price: 200, Code: "F42"})//批量更新字段，如果字段原来为零值则不能赋值
    db.Model(&product).Updates(map[string]interface{}{"Price": 200, "Code": "F42"})//也是批量更新，但可以给零值字段赋值

### Delete（删除）

    //软删除数据表，也就是标为删除但还在库中
    //如果结构体由主键值后面可以不填主键值
    db.Delete(&product, 1)
