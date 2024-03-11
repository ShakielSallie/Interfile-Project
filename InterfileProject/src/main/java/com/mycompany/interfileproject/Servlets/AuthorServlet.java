/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.Servlets;

/**
 *
 * @author Shakiel
 */
import com.mycompany.interfileproject.Author.Model.Author;
import com.mycompany.interfileproject.Author.Dao.AuthorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/AuthorServlet")
public class AuthorServlet extends HttpServlet {
    private final AuthorDAO authorDAO = new AuthorDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addAuthor(request, response);
                    break;
                case "update":
                    updateAuthor(request, response);
                    break;
                case "delete":
                    deleteAuthor(request, response);
                    break;
                default:
                    response.sendRedirect("index.html"); 
                    break;
            }
        }
    }

   

    private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        LocalDateTime currentDate = LocalDateTime.now();

        Author author = new Author();
        author.setName(name);
        author.setCreatedDate(currentDate);
        author.setModifiedDate(currentDate);

        try {
            authorDAO.insertAuthor(author);
            response.sendRedirect("success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error
            response.sendRedirect("error.html");
        }
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        LocalDateTime modifiedDate = LocalDateTime.now();

        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setModifiedDate(modifiedDate);

        try {
            authorDAO.updateAuthor(author);
            response.sendRedirect("success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));

        try {
            authorDAO.deleteAuthor(id);
            response.sendRedirect("success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

