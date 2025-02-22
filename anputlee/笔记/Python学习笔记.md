# Python学习笔记

在Go和Java之中选择了Python的原因就是~~以前有所了解比较熟悉^ ^~~，但是Python的面向对象语法对于我来说还比较陌生，一些更深入的语法我也没有学习。

## 装饰器

```Python
from functools import wraps
 
def a_new_decorator(a_func):
    @wraps(a_func)
    def wrapTheFunction():
        print("I am doing some boring work before executing a_func()")
        a_func()
        print("I am doing some boring work after executing a_func()")
    return wrapTheFunction
 
@a_new_decorator
def a_function_requiring_decoration():
    """Hey yo! Decorate me!"""
    print("I am the function which needs some decoration to "
          "remove my foul smell")
 
print(a_function_requiring_decoration.__name__)
# Output: a_function_requiring_decoration
```

**装饰器是一个语法糖，本质上是一个返回函数的函数，用途在于修改/扩展函数原有的功能。**

## 类的权限控制

### slots

在Python中可以在程序运行过程中给类和实例绑定任何属性和方法，给实例绑定的方法仅限该实例使用，而给类绑定的方法允许所有实例调用。想要限制实例的属性，可以定义\_\_slots\_\_变量：

```
class Student(object):
    __slots__ = ('name', 'age') # 用tuple定义允许绑定的属性名称
```

此时绑定其他属性将得到`AttributeError`的错误。

此定义仅对当前类的实例起作用，对继承的子类是不起作用的。

### 使用@property

```
class Student(object):
    @property
    def score(self):
        return self._score

    @score.setter
    def score(self, value):
        if not isinstance(value, int):
            raise ValueError('score must be an integer!')
        if value < 0 or value > 100:
            raise ValueError('score must between 0 ~ 100!')
        self._score = value
```

@property装饰器可以将类中的一个方法变为属性调用，实现属性的权限控制或参数检查等功能。

属性方法名和实例变量重名，会造成递归调用，导致栈溢出报错！

## MixIn

Python支持多重继承的特性。（此事在Django的API中亦有记载）

需要定义一个类，并继承不同父类的多种功能时，可以使用Mixin的设计方法，而非多层继承。

例如Django中的通用类视图就使用了Mixin的设计方法：

> ### `DetailView` ：使用单个 Django 对象
>
> DetailView ：使用单个 Django 对象
> 要显示一个对象的详情，我们基本上需要做两件事：我们需要查询对象，然后将该对象作为上下文，用一个合适的模板生成一个 TemplateResponse 。
>
> 为了得到对象，DetailView 依赖于 SingleObjectMixin ，它提供一个 get_object() 方法，该方法根据请求的 URL 来找出对象（它查找 URLconf 中声明的 pk 和 slug 关键字参数，并从视图上的 model 属性查找对象，或者从提供的 queryset 属性中查找）。SingleObjectMixin 还覆盖了 get_context_data() ，它被用于所有 Django 内置的基于类的视图，为模板渲染提供上下文数据。
>
> 然后为了生成一个 TemplateResponse， DetailView 使用了 SingleObjectTemplateResponseMixin，它扩展了 TemplateResponseMixin，如上所述的覆盖了 get_template_names()。它实际上提供了一组相当复杂的选项，但大多数人都会使用的主要选项是 <app_label>/<model_name> _detail.html。_detail 部分可以通过在子类上设置 template_name_suffix 来改变。（例如 通用编辑视图 的创建和更新视图使用 _form，删除视图使用 _confirm_delete。）
