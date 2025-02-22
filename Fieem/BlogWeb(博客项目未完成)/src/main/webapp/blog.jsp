<%@ page import="com.example.blog.Blog" %>
<%@ page import="com.example.blog.dao.BlogDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>博客详情</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>博客详情</h1>
<a href="index.jsp">返回首页</a>
<hr>
<%
  String id = request.getParameter("id");
  if (id != null) {
    // 显示博客详情
    BlogDao blogDao = new BlogDao();
    Blog blog = blogDao.getBlogById(Integer.parseInt(id));
    if (blog != null) {
%>
<div class="blog-detail">
  <h2><%= blog.getTitle() %></h2>
  <p><%= blog.getContent() %></p>
  <p>发布时间：<%= blog.getCreateTime() %></p>
</div>
<%
  } else {
    out.println("<p>博客不存在</p>");
  }
} else {
  // 显示发布博客表单
%>
<form action="blog" method="post">
  <input type="text" name="title" placeholder="博客标题" required><br>
  <textarea name="content" placeholder="博客内容" required></textarea><br>
  <button type="submit">发布</button>
</form>
<%
  }
%>
</body>
</html>
