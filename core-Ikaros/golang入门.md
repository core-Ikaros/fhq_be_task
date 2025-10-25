# 内存分析

## 内存
计算机工作的临时存储区域，由内存单元组成，每个单元有唯一内存地址。

## 栈

用于存储局部变量，在嵌套函数中按先进后出，后进先出的方式储存、清除各函数调用的内存。
使用栈内存的变量生命周期不会超过创建它的函数。
每一个函数创建时都会在栈中画出属于它的一块内存，叫栈帧。

## 堆

用于存储生命周期超过函数，能跨函数调用的变量，需要手动清理内存。

# 变量

是数据存储的基本单元，由变量名，类型，值，地址构成。

    var a int = 10

    //第二种：
    var a int

    //第三种：（自动判断类型）
    var a = 10

    //第四种：（自动判断类型）
    a := 10

变量，函数名，结构体字段等首字母决定是否能被外部调用，大写公开，小写匿名。

## 数据类型

### 基本数据类型

- 整数类型

分int8,int16,int32,int64,int（系统决定，32位系统就是int32，64位就是int64）。
能存储正负整数和零，范围依次递增。前面加u就是把范围移到自然数。

- 布尔类型

和c一样

- 浮点类型

分float32和float64，推荐用64

- 字符类型

没有专门的字符类型，要保存单个字符推荐用byte类型保存。
*几个特殊转义字符：
    - \b   退格
    - \n   换行
    - \r   回车
    - \t   制表符

- 字符串类型

字符串定义好之后，不能通过索引改变单个字符。
没有特殊符号用双引号，有特殊字符用反引号。

    string1 := "abcdefg"

    sreing2 := `
    func main(){
        r := gin.Default()
        r.Run()
    }
    `

字符串可以拼接：

    string3 := "abc" + "def"
    string3 += "ghi"

基本数据类型默认值为零值，整数，浮点，布尔，字符串分别为0，0，false，""（空字符串）

#### 类型转换

var n1 int64 = 100

    //n1类型仍为int不变
    var n2 float32 = float32(n1)

    //转换时小心数据溢出
    var n3 int8 = int8(n1)

其他类型转为string的主要方式
    var a int = 10
    var b float32 = 3.14
    var c bool = false
    var d byte = 'a'

    //将a按十进制格式转换为字符串
    var s1 string = fmt.Sprintf("%d",a)

    //将b按浮点格式转换为字符串
    var s2 string = fmt.Sprintf("%f",a)

    //将c按布尔格式转换为字符串
    var s3 string = fmt.Sprintf("%t",a)

    //将d按字符格式转换为字符串
    var s4 string = fmt.Sprintf("%c",a)

string转为其他类型的

    //使用strconv包
    s1 := "13"
    s2 := "3.14"
    s3 := false
    //参数分别代表转换的字符串，十进制，转为int64
    函数会返回两个值，可用下划线忽略对应值
    n1,_ := strconv.ParseInt(s1,10,64)//也有ParseUint
    //字符串，float64
    n2,_ := strconv.ParseFloat(s2,64)
    //字符串
    n3 := strconv.ParseBool(s4)
    //如果字符串不能转成对应格式，则返回零值

### 复杂数据类型

#### 指针

任何变量都有16进制的内存地址，这就是指针。

a := 10
//将a的地址保存到ptr中，ptr类型为指针类型，指向int类型
var ptr *int = &a
//获取指针指向的变量储存的值
fmt.Println(*ptr)
//用指针能修改指向的变量的值
*ptr = 20
fmt.Println(*ptr)

#### 数组

和cc一样，语法：

    var array [5]int
    array[0] = 0
    arr[1] = 1
    array[2] = 2
    ……

##### 内存分析

array储存的是第一个内存的地址，各变量的内存按字节数连续，地址也按字节数连续

##### 初始化

    //第一种：
    var arr1 [5]int = [5]int{1,2,3,4,5}
    //第二种：
    var arr2 = [5]int{1,2,3,4,5}
    //第三种：
    var arr3 = [...]int{1,2,3,4,5,6……}
    //第四种：按索引赋值
    var arr1 = [...]int{2:3,4:2,……}

想在函数中修改外部数组，需要传入指针

##### 二维数组

`var arr [5][5]int `

#### 切片

切片相当于更灵活的数组，它是对一个数组片段的引用，是引用类型。

    var arr [5]int = [5]int{1,2,3,4,5}

    //方式一：按索引引用数组值，左闭右开
    var slice1 []int = arr[1:3]

    //方式二：make创造一个不可见的初始化数组，然后用切片引用。
    //传入参数：切片类型，长度，容量
    slice2 := make([]int,4,20)
    slice2[0] = 3
    ……

    //方式三：直接指定一个数组个切片引用，原理和make类似
    slice3 := []int{1,2,3}

切片能继续切片，将切片视为数组操作。
由于都是引用类型，所以不管更改哪个切片的值，最终会变得只有数组。

切片能扩容：

    //用append创造一个新数组，将slice3元素和追加的元素加入数组，让slice3（或者其他切片）引用它
    slice3 := append(slice3,4,5,……)

切片能拷贝：

    //用copy拷贝，将底层数组元素赋值给另一个底层数组
    slice4 := []int{1,2,3}
    slice5 := make([]int,10)
    copy(slice5,slice4)

##### 内存分析

切片本质是结构体，含三个字段：指向数组某元素的指针，切片长度，切片容量

#### 映射（map）

将键（key）与值（value）相关联，类似哈希表。
key唯一，但value可以重复。

    //方式一：声明一个map
    map1 := map[int]string

    //只声明不会分配内存空间，要make
    //这里设定map1能存10个键值对
    map1 = make([int]string,10)

    map1[123] = "aaa"
    map1[456] = "bbb"

    //方式二：不声明直接make
    map2 := make([int]string)

    方式三：
    map3 := [int]string{
        123 : "aaa",//注意逗号
        456 : "bbb",
        ……,
    }

##### map操作

增删改查四种，增加，修改上面就是。

    //删除有内置函数delete
    delete(map1,123)

    //查找
    value,ok := map1[456]//若没有对应键则ok得到false

# 面向对象

将现实中的事物用代码表示出来，表达他的属性，能力，方便程序编写，这叫做面向对象。

## 结构体

就是类。

    type human struct {
        Name string
        Gender string
        Age int
    }

    func main(){

        //实例创建方式
        //方法一：
        var t1 human
        t1.Gender = "男"
        
        //方法二：
        t2 := human{"Ikaos","man",18}

        //方式三：返回结构体指针
        var t3 *human = new(human)//new建立实例
        (*t3).Age = 18
        //go编译器对更简单的语法做了适配：
        t3.Gender = "man"

        //方式四：返回结构体指针
        var t4 *human = &human{}
    }

跨包访问结构体

    import(
        //导入自定义的model包，里面有一个定义好的首字母大写的结构体Student
        "……/model"
    )
    func main(){
        a := model.Student()
    }
### 内存分析

创建一个结构体类型变量teacher时，在内存空间中划分一块内存，在这块内存中按字段再划分多块内存。

### 结构体转换

两种结构体转换时，需要有完全一样的字段。

    type teacher struct{
        Age int
    }
    type student struct{
        Age int
    }

    func main(){
        t := teacher{40}
        var s = student{}
        s = student(t)
    }

用type可以给结构体取别名，但即使是同一种结构体，不同名之间也要进行转换

    type teacher struct{
        Age int
    }
    type tea teacher

    func main(){
        t1 := teacher{40}
        var t2 = tea{}
        t2 = tea(t1)
    }

## 方法

是指定数据类型可以使用的函数，相当于此种对象独有的能力。

    type human struct{
        Age int
    }

    //相比函数，方法在函数名前多了一个括号。
    //括号内容表示只有human结构体类型的变量能使用这个方法，将两者绑定
    //somebody代表变量名，和形参一样随便填
    func (somebody,human) speak(){

    }

    func main(){
        var a human

        //相当与把a传到方法名前的括号内
        a.speak()
        //编译器做了优化，若func (sb,*human)speak(){}，则a.speak相当于(&a).speak
        //int，float32这些数据类型也可以有方法
    }

## 封装

将字段隐藏在结构体内，不公开，只能通过方法修改。
实现起来很简单，字段首字母小写就行。

## 继承

结构体之间有时会有一些通用的字段、方法。
可以用一个结构体统一容纳通用字段方法，在构建其他结构体是直接导入。
被导入继承的匿名结构体的字段，方法无论大小写全都能被继承的结构体使用

    //创建Animal结构体
    type Animal struct{
        Age int
        Weight int
    }

    //给Animal绑定方法
    func (a,Animal)Shout(){
        fmt.Println("wow")
    }

    //创建cat结构体，将Animal继承给cat
    type cat struct{
        Animal
        Name string
    }

    func main(){
        //快捷访问继承结构体字段
        cat.Age = 3
    }

## 接口

“一个东西只要长得像鸭子，叫的像鸭子，走的像鸭子，那它就是鸭子”
接口联系着能实现它的函数和调用它的函数。

    //定义接口
    type 驾驶员 interface{
        //声明一些函数，不用定义
        前进()
        转弯()
        停车()
    }

    //构造有对应接口函数的结构体，这样结构体就实现了接口
    //如此例中张三能前进，转弯，停车，那他就是驾驶员
    //任何自定义类型都能有接口，不止结构体
    type 客车司机 struct{……}
    func (a,客车司机) 前进{……}
    func (a,客车司机) 转弯{……}
    func (a,客车司机) 停车{……}

    //一个函数用了驾驶员接口
    //张三是驾驶员，所以张三能被传入路考函数中
    func 路考(考生 驾驶员（接口名）){
        驾驶员.前进()
        驾驶员.转弯()
        驾驶员.停车()
    }

    func main(){
        张三 := 客车司机{}
        路考{张三{}}
        //将路考中的驾驶员.前进替换为了张三.前进
    }

接口可以继承其他接口，实现它需要实现所有继承的接口

    type B interface{
        b()
    }
    type C interface{
        c()
    }
    type A interface{
        a()
        B
        C
    }

# 携程

协程是一种轻量级线程，在一个函数执行时可以随时中断去执行另一个函数。
过程很像多线程，但只有一个线程来回切换。

- 程序

    为了完成某种任务编写的代码集合，是静态的。

- 进程

    程序的执行过程。在内存中会为每个进程分配不同的内存区域。

- 线程

    进程可以细化为线程，是程序的一条执行路径。可以同时进行

    func test1(){
        for i = 1；i<=10;i++{
            fmt.Println("1")
        }
    }

    

    func main(){//主线程
        //开启协程
        go test1
        
        func test2(){
            for i = 1；i<=10;i++{
                fmt.Println("2")
            }
        }
    }

主线程结束，则还在执行的协程也会强制结束。为避免这种情况，需要用到WaitGroup

    import (
        "fmt"
        "sync"
    )

    //声明计数器
    var wg sync.WaitGroup

    func main(){
        for i := 1; i < 5; i++ {
            //每开一个协程，计数器加一
            wg.Add(1)
            go func() {
            fmt.Println("nihao")

            //协程结束，计数器减一
            wg.Done()
            }()
        }

        //阻塞线程，直到计数器为0
        wg.Wait()
    }

## 锁同步协程

### 互斥锁
多个协程操纵同一个数据时，可能由于读取、改变、赋值顺序的混乱而不能达到想要的效果。
这种情况需要用到互斥锁，使一个协程在执行时另一个协程不执行。

    import (
        "fmt"
        "sync"
    )

    var wg sync.WaitGroup
    var num int = 0
    //加互斥锁
    var lock sync.Mutex

    func add(){
        for i = 1;i <= 10;i++{
            //上锁
            lock.Lock
            defer wg.Done()
            num = num + 1
            //解锁
            lock.Unlock
        }
    }

    func sub(){
        for i = 1;i <= 10;i++{
            lock.Lock
            defer wg.Done()
            num = num - 1
            lock.Unlock
        }
    }
    func main(){
        wg.Add(2)
        go add()
        go sub()
        wg.Wait()
    }

### 读写锁

读写锁的效率更高，适用于读多写少的场景。
原理是读写同时存在时隔开协程（会改变数据），都是读的情况不作用（不改变数据）

    import (
        "fmt"
        "sync"
    )

    var wg sync.WaitGroup
    var num int = 0
    
    var lockm sync.Mutex

    //加入读写锁
    var lockr sync.RwMutex

    func read(){
        
        //上锁
        lockr.RLock
        defer wg.Done()
        fmt.Printlv("读取数据")
        //解锁
        lockr.RUnlock
    }

    func write(){
        lockm.Lock
        defer wg.Done()
        fmt.Printlv("写入数据")
        lockm.Unlock
    }
    func main(){
        wg.Add(6)
        for i = 1;i <= 5;i++{
            go read()
        }
        go write()
        wg.Wait()
    }

# 管道

本质是队列，能储存多个数据，先存先取。

    //声明管道
    var intchan chan int

    //分配容量，这里能存三个int类型的数据
    intchan = make(chan int,3)

    func main(){
        num = 2
        //存入数据
        intchan <- 1
        intchan <- num
        intchan <- 3

        //可以用内置函数关闭管道，此时管道不能存储只能读取
        close(intchan)

        //读取数据
        num1 := <- intchan
        num2 := <- intchan
        num3 := <- intchan
    }

可以声明管道只写或只读

    //只读
    var intchan1 <-chan int
    //只写
    var intchan2 chan<- int

## 管道遍历

由于队列没有索引，所以管道的遍历只能用for range遍历，且遍历前要关闭管道

    var intchan chan int
    intchan := make(chan int , 100)
    for i=0;i<100;i++{
        intchan <- i
    }
    close(intchan)
    for v := range intchan {
        fmt.Println(v)
    }


# 运算符

和c一样，过。

# 流程控制

## if

和c一样，但条件表达式的小括号可以去掉，也推荐去掉。

## switch

和c一样，有几点：

- 一个case可以有多个值
- case不带break
- case末尾可以加fallthrough，强行执行下一个case，这叫switch穿透

- for

和c一样，有几点：

- 条件表达式去掉小括号。
- 有go语言特有的for range循环

    str := "hello world"
    //key接受索引，value接受值
    for key,value := range len(str){
        ……
    }

## goto

跳到指定的行

    func main(){
        ……
        ……
        ……
        goto label
        ……
        ……
        //跳到这一行继续执行
        label:
        ……
        ……
        ……
    }

## return

结束当前函数，不需要加0。

# 函数

    //依次为：函数名，形式参数，返回类型（可以返回多种类型），如果只返回一种类型可以去掉小括号
    func cal (a int,b int) (int) {
        ……
    }

- 可变参数

    形参课以用...代表可变参数，支持传入任意数量的变量。
    本质是将这些变量作为切片使用

        func cal (a ... int) int {
            ……
        }
        
- 函数作为数据类型

    在golang中函数也能作为数据类型赋给变量，此时变量能调用函数

        func text(num int){
            ……
        }
        func main(){
            //赋给a，此时a的类型为func(int)
            a := test
            //使用test函数
            a(10)//等价于test(10)

            //简单理解就是函数重命名为了a。
        }

    同理，函数也能作为形参被其他函数调用

## init函数

用init为名的函数在main入口函数之前执行

## defer

大多用于释放内存，使用时会压入栈中，在其他语句执行完毕后从栈中以此取出执行

    func main(){
        ……
        defer fmt.Println("释放内存1")
        defer fmt.Println("释放内存2")
        defer fmt.Println("释放内存3")
        ……
        //按3，2，1的顺序释放内存
    }

# 错误处理

## defer + recover

程序中如果出现错误，则无法继续运行。
用defer + recover的组合可以捕获错误，让程序继续执行

    func main(){
        test()
        fmt.Println("继续执行下面的语句)
        ……
    }

    func test(){
        defer func (){
            //调用recover捕获错误
            err := recover()

            //如果没有检测到错误则返回nil
            if err != nil {
                fmt.Println("出错了，错误是：",err)
            }
        }

        var result int = 10/0
    }

## panic

panic()函数执行括号内容后直接中断程序运行。


# 包

将写出的一批函数放到一个文件，这个文件就是包。
在自己的包中导入其他包可以使用其他包中的函数。
包名从$GOPATH/src/（$GOPATH是环境变量，对应下载开发环境时goproject的路径）后开始计算，用/分隔路径

`package`是对此文件进行包的声明，一般用main