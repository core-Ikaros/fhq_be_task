package com.example.blog.servlet;

import com.example.blog.User;
import com.example.blog.dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao(); // 初始化 UserDao
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取用户提交的表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证用户信息
        User user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功，将用户信息存入 Session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 重定向到首页
            response.sendRedirect("index.jsp");
        } else {
            // 登录失败，返回登录页面并提示错误信息
            request.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

