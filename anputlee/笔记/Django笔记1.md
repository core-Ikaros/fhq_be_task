# Django学习摘要

这段时间主要一直在看Django的官方文档，因为确实不太擅长一直看文档和视频教程之类的记东西，所以我先跟着Django文档中的第一步教程，构建出了一个简单的项目，了解了Django的基本构成和用法，然后再一边做一边在文档和搜索引擎中寻找自己需要的东西。作为第一个练手的项目，我选择了文档中所说的简单的博客管理系统，期望达到的目标是：

- 简易美观的博客主界面（主要是写HTML/CSS）
- 基本功能齐全的后台管理（新建文章，编辑/浏览文章等功能）
- 账号管理以及相应的评论等功能
- 基础的搜索功能（想了想感觉不太好做，先放一放）
- 待补充

以下我将分别记录编写学习过程中在几个不同模块上总结出来的知识。

## Django的表单管理

Django提供了一套自己的表单管理方法，用于在网页视图中使用模板语言自动构建表单并收集表单信息，并储存到相应的模型中。相比较在HTML中直接构建表单，Django的方法能够更好地将表单从模板层面分离出来，并与模型产生相应的联系。我在编写过程中使用的第一个表单是用于新建文章页面：

```Python
class PostForm(forms.ModelForm):
    class Meta:
        model = Post
        fields = ['title', 'content', 'author']
        labels = {
            'title': '标题',
            'content': '内容',
        }
        widgets = {
            'title': forms.TextInput(attrs={'class': 'form-control', 'placeholder': '请输入标题'}),
            'content': forms.Textarea(attrs={'class': 'form-control', 'placeholder': '支持Markdown语法'}),
            'author': forms.HiddenInput(),
        }
```

这里的Post是我用于存储文章内容的模型，author是其中的外键字段，用于和账号管理建立联系，自动记录文章的发布者并展示。比较不理解的是我在模型中添加了两个字段SlugField（用于文章的url中）和User的外键，但是在进行数据库迁移的时候均出现问题：

`It is impossible to add a non-nullable field 'author' to post without specifying a default. This is because the database needs something to populate existing rows.`

这两个字段都没有通过表单的方式收集，slug是通过重写了model的save方法，根据标题自动生成的，而author外键则是在类视图中我重写了form_valid方法，来填充表单中的字段。然而模型中的标题字段和内容字段却没有出现这样的问题，在表单验证中同样会检查这两个字段的空缺。我的解决办法是给slug赋予了一个初始值（这个字段设置了unique属性），给author字段设置可空，只能在程序中确保最终储存时不会为空。暂时没有找到这个问题的原因（？）

