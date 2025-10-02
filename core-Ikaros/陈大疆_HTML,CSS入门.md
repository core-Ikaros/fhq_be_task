# HTML基础

## HTML组成

由**元素**和**标签**组成，通过使用标签能将文本、图片等封装为元素，如：

`<标签名 属性="属性值">内容</标签名>`

开始标签由尖括号<>和内部的**名称**组成，结束标签则是在名称前加/，两标签包夹的是**内容**，这一坨就是**元素**。
名称后可以加入**属性**，用空格隔开。属性用于标记、分类元素，方便后续的操作。属性需要用等号和双引号赋值**属性值**。

元素可以嵌套。

### 空元素

不需要内容，因此不包含内容，元素的功能有标签和属性实现。`<img>`元素是个经典例子，作用为插入图片：

`<img src="images/firefox-icon.png" alt="My test image" />`

### 主体（不知道那一段叫什么随便取的名字反正自己看得懂就行）前的部分

一个例子：

<!doctype html>
<html lang="en-US">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width" />
    <title>My test page</title>
  </head>
  <body>
    <img src="images/firefox-icon.png" alt="My test image" />
  </body>
</html>

- `<!doctype html>` ：**文档类型**,只知道用于保证文档正常读取，这样就够了。
- `<html></html>` ：<html>元素，内容为**整个网页**，含有**lang属性**，用于设置网页语种。
- `<head></head>` :<head>元素，内容会被隐藏，比如搜索用的关键字什么的。
- `<meta charset="utf-8">` ：表示文档使用utf-8编码，基本能处理所有文本内容，**不用换**。
- `<meta name="viewport" content="width=device-width">` ：**视口元素**，能让网页适配设备屏幕大小。
- `<title></title>` ：<title>元素，用来设置标签页标题。
- `<body></body>` ：<body>元素，显示网页内容。

## 图像

<img src="images/firefox-icon.png" alt="My test image" />

属性**src**包含图片相对于html文件的路径，初始目录为包含html文件的目录。**alt**用于在*图片无法显示*时展示内容。

## 标记文本

### 注释

`<!-- 这是注释 -->`

### 标题

`<h1>一级标题</h1>`
`<h2>二级标题</h2>`
`<h3>三级标题</h3>`
`……`

### 段落

`<p>这是段落</p>`

### 列表

<ul>
  <li>technologists</li>
  <li>thinkers</li>
  <li>builders</li>
</ul>
<ol>
  <li>technologists</li>
  <li>thinkers</li>
  <li>builders</li>
</ol>

<p>working together…</p>
1. **无序列表**，用`<ul></ul>`表示。
2. **有序列表**，用`<ol></ol>`表示。
每一项都要用`<li></li>`标签标注。

### 链接

<a href="https://www.mozilla.org/zh-CN/about/manifesto/">
  Mozilla Manifesto
</a>

属性**href**包含链接的目标网址，不要忘了**协议部分**（`http://`或`https://`）

# CSS基础

一门样式表语言（什么东西），为HTML元素添加样式，也就是做装饰。
要使用写好的CSS文件（路径styles/style.css），要在HTML文件的`<head>`内容中加入：

`<link href="styles/style.css" rel="stylesheet" />`

现在还没弄清属性**rel**的作用，后面**记得查**。

## 结构集

一个结构集如下所展示：

p,h1,ul{
  color: red;
}

- 选择器
  在最前面的元素名，用于筛选出**要操作的元素**,可以一次选中多个元素，用逗号`,`分隔。
  也有其他类型的选择器，后面再慢慢学完。

- 声明
  用于为元素的**指定属性**添加样式，包括属性和属性值，末尾要加**分号;**。

## 字体文本

<link
  href="https://fonts.googleapis.com/css?family=Open+Sans"
  rel="stylesheet" />

html {
  font-size: 10px; /* px 表示“像素（pixel）”: 基础字号为 10 像素 */
  font-family:
    "Open Sans", sans-serif; /* 这应该是你从 Google Fonts 得到的其余输出。 */
}

在`<head>`的内容中插入`<link>`元素，链接到需要的样式表.
用<html>元素修改页面字体`font-family`和大小`font-size`。

可以在其他元素的内容中加入各种声明，修改对应元素的样式：

h1 {
  font-size: 60px;
  text-align: center; //水平对齐方式，此为居中
}

p,
li {
  font-size: 16px;
  line-height: 2; //行高
  letter-spacing: 1px;  //字间距
}

## 盒子

页面内容由“盒子”容纳，一些属性能修改“盒子”：

- padding（内边距）：是指内容周围的空间。它是段落文本周围的空间。
- border（边框）：是紧接着内边距的实线。
- margin（外边距）：是围绕元素边框外侧的空间。
- width：元素的宽度。
- background-color：元素内容和内边距底下的颜色。
- color：元素内容（通常是文本）的颜色。
- text-shadow：为元素内的文本设置阴影。
- display：设置元素的显示模式