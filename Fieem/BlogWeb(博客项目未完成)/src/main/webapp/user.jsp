<%@ page import="com.example.blog.User" %>
<%@ page import="com.example.blog.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>个人中心</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>个人中心</h1>
<a href="index.jsp">返回首页</a>
<hr>
<%
  String username = (String) session.getAttribute("username");
  if (username != null) {
    UserDao userDao = new UserDao();
    User user = userDao.getUserByUsername(username);
    if (user != null) {
%>
<div class="user-info">
  <p>用户名：<%= user.getUsername() %></p>
  <p>昵称：<%= user.getNickname() %></p>
  <p>邮箱：<%= user.getEmail() %></p>
</div>
<form action="user" method="post">
  <input type="text" name="nickname" placeholder="新昵称"><br>
  <input type="email" name="email" placeholder="新邮箱"><br>
  <input type="password" name="password" placeholder="新密码"><br>
  <button type="submit">更新信息</button>
</form>
<%
    } else {
      out.println("<p>用户信息获取失败</p>");
    }
  } else {
    response.sendRedirect("login.jsp");
  }
%>
</body>
</html>
