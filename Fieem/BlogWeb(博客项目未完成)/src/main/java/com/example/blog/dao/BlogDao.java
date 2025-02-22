package com.example.blog.dao;

import com.example.blog.Blog;
import com.example.blog.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDao {

    // 添加博客
    public void addBlog(Blog blog) {
        String sql = "INSERT INTO blog (title, content, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, blog.getTitle());
            pstmt.setString(2, blog.getContent());
            pstmt.setInt(3, blog.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据博客 ID 查询博客
    public Blog getBlogById(int id) {
        String sql = "SELECT * FROM blog WHERE id = ?";
        Blog blog = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));
                    blog.setUserId(rs.getInt("user_id"));
                    blog.setCreateTime(rs.getTimestamp("create_time"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blog;
    }

    // 查询所有博客
    public List<Blog> getAllBlogs() {
        String sql = "SELECT * FROM blog ORDER BY create_time DESC";
        List<Blog> blogs = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setUserId(rs.getInt("user_id"));
                blog.setCreateTime(rs.getTimestamp("create_time"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // 根据用户 ID 查询博客
    public List<Blog> getBlogsByUserId(int userId) {
        String sql = "SELECT * FROM blog WHERE user_id = ? ORDER BY create_time DESC";
        List<Blog> blogs = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));
                    blog.setUserId(rs.getInt("user_id"));
                    blog.setCreateTime(rs.getTimestamp("create_time"));
                    blogs.add(blog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // 更新博客
    public void updateBlog(Blog blog) {
        String sql = "UPDATE blog SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, blog.getTitle());
            pstmt.setString(2, blog.getContent());
            pstmt.setInt(3, blog.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除博客
    public void deleteBlog(int id) {
        String sql = "DELETE FROM blog WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

