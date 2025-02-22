<%@ page import="com.example.blog.Blog" %>
<%@ page import="com.example.blog.dao.BlogDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>博客首页</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>博客首页</h1>
<a href="blog.jsp">发布新博客</a>
<a href="user.jsp">个人中心</a>
<hr>
<%
  BlogDao blogDao = new BlogDao();
  List<Blog> blogList = blogDao.getAllBlogs();
  for (Blog blog : blogList) {
%>
<div class="blog-item">
  <h2><a href="blog.jsp?id=<%= blog.getId() %>"><%= blog.getTitle() %></a></h2>
  <p><%= blog.getContent() %></p>
  <p>发布时间：<%= blog.getCreateTime() %></p>
  <hr>
</div>
<%
  }
%>
</body>
</html>

