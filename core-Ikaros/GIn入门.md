# Gin 框架

Gin框架包含了很多封装好的函数，结构体等模块，用来方便计算机网络相关功能的实现。

# RESTful API

一种写代码的统一风格，简单理解就是以名词，属性为路径名给服务器上的资源分类，用不同的方法去和资源互动。

# Template 模板

事先写好的HTML，在需要填充内容的地方用`{{ . }}`代替，能方便的对WEB页面上的内容进行修改。

## 定义模板

就是写HTML，记得留`{{ . }}`

## 解析模板

将HTML文件转换储存为go代码中的变量

    func sayHello(w http.ResponseWriter,r *http.Request){
        t,err := template.ParseFiles("./index.html")
        if err != nil {
            fmt.Println("HTML解析错误！err:",err)
            return
        }
        type User struct{
            Name string
            Password string
        }
        my := User{
            Name: "Ikaors",
            Password: "nbacomcdj1017",
        }
        t.Execute(w,my)
    }

## 渲染模板

在解析的模板里填入内容。如果传入的是映射，结构体之类的，那么在`{{ . }}`中点代表传入的数据，
可以用key，结构体内变量名来描述填充在此处的内容所在。

    <!DOCTYPE html>
    <html lang = "en-US">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width" />
        <title>
            hello
        </title>
    </head>
    <body>
        <h1>hello,{{ .Name }}</h1>
        <h2>your username:{{ .Name }}</h2>
        <h2>your password:{{ .Password }}</h2>
        <h2></h2>
    </body>

## 简单模板语法

### 变量

可以在模板中创建变量，在模板中使用。

    {{ $v1 := 10 }}
    {{ $name := .Name }}

`$`是变量名的一部分，没有什么特殊意义，只是让变量在模板中更显眼。

### 移除空格

可以移除渲染内容左或右的所有空格。

    {{- . -}}

像个脸，可爱捏www

### 条件判断

和c、go差不多

    {{ if 布尔值}}
    执行语句
    {{else if 布尔值}}
    执行语句
    {{else}}
    执行语句
    {{end}}

返回布尔值的函数也能作为条件。用到的函数到用的时候自己找，这里先不写。

### range遍历

和go的`for range`一样,能遍历数组，切片等。
如果传入没有值，就执行`else`。

    {{ range $idx,$val := slice }}
        {p}idx = {{ #idx }},val = {{ $val }}{/p}
    {{ else }}
        请输入文本
    {{end}}

### with

创造一块作用域，内部的渲染内容会以with后的值为基准，方便大量渲染。

    {{with .People}}
    {{ .Chinese }}
    {{ .American}}
    {{ end }}

好像模板不算Gin的内容？那后面再学模板吧。

## HTML渲染
直接上代码说明：

    package main

    import (
        "net/http"

        "github.com/gin-gonic/gin"
    )

    func main(){
        //创建路由引擎
        r :=gin.Default()

        //解析模板
        r.LoadHTMLFiles(".index.html")

        //客户端用GET方法访问对应路径时，执行函数
        r.GET("/hello",func(ctx *gin.Context) {

            //返回状态码，渲染模板
            //gin.H()的作用时创建一个映射，其索引为字符串，值为空接口，用于填充进模板
            ctx.HTML(http.StatusOK,"./index.html",gin.H{
                "username" : "Ikaros",
                "password" : "nbacomcdj1017",
            })
        })

        //运行客户端
        r.Run()
    }

## 自定义模板函数

    //设置自定义函数
    r.SetFuncMap(template.FuncMap{

        //用映射写出函数名和功能
            "safe" : func () template.HTML {
                return template.HTML("<h2 style='color:red;'>hello</h2>")
            },
        })

## 静态文件处理

静态文件指css样式文件，图片等

    //加载静态文件
    //在模板中发现
    r.Static("/style", "./static")

## JSON渲染

    r := gin.Default()
        r.GET("/json",func(ctx *gin.Context) {
            
            //第一种方式：使用映射或gin.H创造的映射
            ctx.JSON(http.StatusOK,gin.H{
                "username" : "Ikaros",
                "password" : "nabcomcdj1017",
            })
        })

        r.Run(":9090")

    //第二种方法：用结构体
    r.GET("/another_json",func(ctx *gin.Context) {
		type a struct{
			Username string
			Password string
		}
		user := a{
			Username: "Ikaros",
			Password: "nbacomcdj1017",
		}
		ctx.JSON(http.StatusOK,user)
	})

## 获取querystring参数

querystring参数是前端发送请求到后端时附加的数据，类似映射。位于URL？后面，多个参数用&分隔。

`/user/search?username=Ikaros&address=Shenzhen`

示例：

    r.GET("/hello",func(ctx *gin.Context) {

        //第一种：获取querystring参数
        name := ctx.Query("name")
        age := ctx.Query("age")
        ctx.JSON(http.StatusOK,gin.H{
            "name" : name,
            "age" : age,
            })
        })

    r.GET("/hello",func(ctx *gin.Context) {

        //第二种：如果没有检索到key索引，则返回"somebody"
        name := ctx.DefaultQuery("key","somebody")
        ctx.JSON(http.StatusOK,gin.H{
             "name" : name,
            })
    })

    r.GET("/hello",func(ctx *gin.Context) {

        //第三种：如果没有检索到key索引，则第二个返回值为false
        name , ok := ctx.GetQuery("key")//返回string和bool
        if !ok {
            name = "somebody"
        }
        ctx.JSON(http.StatusOK,gin.H{
             "name" : name,
            })
    })

## 获取form参数

客户端填写的form表单数据以POST方式传给服务器。

HTML:
    <body>
            <h1>HELLO,USER</h1>
            <form action="/login" method="post">
                <h1>用户名：</h1>
                <input type="text" name="username" id="username">//填入的数据会以"name"命名，类似变量
                <h1>邮箱</h1>
                <input type="text" name="email" id="email">
                <h1>密码</h1>
                <input type="text" name="password" id="password">
                <button type="submit">注册</button>
            </form>
        </body>

Go:

    //获取form表单数据
    r.POST("/login",func(c *gin.Context) {

        //第一种方式：PostForm获取对应"name"的数据
        //若表单数据中没有需要的"name"则返回空字符串，而非空
        username := c.PostForm("username")
        email := c.PostForm("email")
        password := c.PostForm("password")
        c.HTML(http.StatusOK,"index.html",gin.H{
            "Username" : username,
            "Email" : email,
            "Password" :password,
        })
    })

    r.POST("/login",func(c *gin.Context) {

        //第二种方式：DefaultPostForm获取
        //若表单数据中没有需要的"name"，则返回第二个参数，此例中为"somebody"
		username := c.DefaultPostForm("username","somebody")
		email := c.DefaultPostForm("email","somebody")
		password := c.DefaultPostForm("password","somebody")
		c.HTML(http.StatusOK,"index.html",gin.H{
			"Username" : username,
			"Email" : email,
			"Password" :password,
		})
	})

    r.POST("/login",func(c *gin.Context) {

        //第三种方式：GetPostForm获取
        //若表单数据中没有需要的"name"，则第二个返回值为false
        username ok := c.GetPostForm("username")
        if !ok {
            username = "somebody"
        }
        c.HTML(http.StatusOK,"index.html",gin.H{
            "Username" : username,
        })
    })

## 获取URI路径参数

### 动态路径

在前面加入冒号后，此路径将不固定，由客户端输入的路径名决定。例如go代码中使用路径`/blog/:year/:month`，
则客户端可以输入`……/blog/2025/10`

    r.GET("/user/search/:username/:address", func(c *gin.Context) {

            //用Param获取动态路径中填入对应位置的数据
            username := c.Param("username")
            address := c.Param("address")

            c.JSON(http.StatusOK,gin.H{
                "message":  "ok",
                "username": username,
                "address":  address,
            })
        })

要小心动态路径之间的冲突，如：

    r.GET(":username/:address", func(c *gin.Context) {
            username := c.Param("username")
            address := c.Param("address")

            c.JSON(http.StatusOK,gin.H{
                "message":  "ok",
                "username": username,
                "address":  address,
            })
        })

        r.GET("/blog/:year/:month", func(c *gin.Context) {
            year := c.Param("year")
            month := c.Param("month")

            c.JSON(http.StatusOK,gin.H{
                "message":  "ok",
                "year": year,
                "month":  month,
            })
        })

此时存在两种路径：:username/:address和/blog/:year/:month，由于:username和blog同位置，
客户端填写URI时可能将blog认为是username位置的动态路径。

## 参数绑定

请求中需要获取的数据较多时使用。用结构体或映射等统一存储数据

    type user struct{

        //需要用标签将字段与数据对应，如form对应表单数据和querystring，uri对应动态路径数据
        Username string `form:"username"`
        Password string `form:"password"`
        Uri string `uri:"location"`
    }

    func main() {
        r := gin.Default()

        r.GET("/user/:location",func(c *gin.Context) {

            //构造存数据的结构体
            var u user

            //检索结构体字段的标签，根据标签在请求中查找数据并返回
            c.ShouldBind(&u)
            c.ShouldBindUri(&u)
            c.JSON(http.StatusOK,gin.H{
                "username" : u.Username,
                "password" : u.Password,
                "location" : u.Uri,
            })
        })

        r.Run(":8080")
    }

## 文件上传

文件上传，就是上传文件

go代码：

    r.GET("/upload",func(c *gin.Context) {
            c.HTML(http.StatusOK,"upload.html",nil)
        })

        r.POST("/index",func(c *gin.Context) {

            //读取文件并存为变量f
            f,_ := c.FormFile("f1")

            //导入path包，设置保存路径
            path := path.Join("./",f.Filename)

            //保存在本地
            c.SaveUploadedFile(f,path)
            c.JSON(http.StatusOK,gin.H{
                "message": "Upload correct",
            })
        })

html：

    <body>
        <h1>upload</h1>

        //enctype用于指定数据传输时的编码方式，上传文件时必须要用示例的编码方式
        //将表单数据分成多部分上传
        <form action="/index" method="post" enctype="multipart/form-data">
            <input type="file" name="f1">
            <input type="submit" value="上传">
        </form>
    </body>

## 重定向

### HTTP重定向

HTTP重定向很容易，内外重定向都可以

    r.GET("/test",func(c *gin.Context) {

        //访问/test时重定向到百度
        c.Redirect(http.StatusTemporaryRedirect,"http://www.baidu.com")
        })

### 路由重定向

    r.GET("/a",func(c *gin.Context) {

            //路径修改为/b
            c.Request.URL.Path = "/b"

            //继续执行内容
            r.HandleContext(c)
        })

        r.GET("/b",func(c *gin.Context) {
            c.JSON(http.StatusOK,gin.H{
                "status" : "OK",
            })
        })

## 路由

路由是指将URL和对应执行代码联系起来的规则，如：`r.GET("/test",func(c *gin.Context) {})`
就是建立了一个路由，将/test与后面的执行代码联系。

常用的请求方法有GET，POST，PUT，DELETE，NoRoute。

访问没有定义的URI时统一使用NoRoute方法访问

    r.NoRoute(func(c *gin.Context) {
            c.JSON(200,"hi")
        })

## 路由组

要写多个路由且在统一路径下时用路由组归类

    //Group函数定义路由组
    videoGroup := r.Group("/file/video")
    {
        //在路由组URI下构造路由
        videoGroup.GET("/x",func(c *gin.Context) {
            c.JSON(200,"x")
        })
        videoGroup.GET("/y",func(c *gin.Context) {
            c.JSON(200,"y")
        })
        videoGroup.GET("/z",func(c *gin.Context) {
            c.JSON(200,"z")
        })
    }

路由组内可以继续嵌套路由组

## 中间件

在执行函数前执行的函数，可以统一对访问某些路径的请求进行处理

### 单个路由中注册中间件

    //定义中间件：只要传入参数是c *gin.Context就是中间件
    func middle (c *gin.Context)  {

        //记录现在时间
        start := time.Now()

        //先执行后面的执行函数
        c.Next()

        //c.Abort()不执行后面的执行函数

        //记录从start开始过了多久
        time := time.Since(start)
        fmt.Printf("处理时间：%v",time)
    }

    func main() {
        r := gin.Default()

        //把中间件放在路径和执行函数中间（感觉是个非常好的语法，执行顺序一目了然）
        r.GET("/index",middle,func(c *gin.Context) {
            fmt.Println("index")
            c.JSON(200,gin.H{
                "msg" : "index",
            })
        })	

        r.Run(":8080")
    }

### 全局统一注册中间件

    //全局注册middle中间件
    r.Use(middle)

### 路由组统一注册中间件

    //第一种方法：在路由组路径后面加中间件函数
    videoGroup := r.Group("/video",middle)
    {
        videoGroup.GET("/index",func(c *gin.Context) {
            c.JSON(200,"OK")
        })
    }

    //第二种方法：use函数
    videoGroup := r.Group("/video")
    videoGroup.Use(middle)
    {
        videoGroup.GET("/index",func(c *gin.Context) {
            c.JSON(200,"OK")
        })
    }