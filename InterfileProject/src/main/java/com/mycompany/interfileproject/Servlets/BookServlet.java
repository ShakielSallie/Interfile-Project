/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.Servlets;

/**
 *
 * @author Shakiel
 */
import com.mycompany.interfileproject.Book.Dao.BookDAO;
import com.mycompany.interfileproject.Book.Model.Book;
import com.mycompany.interfileproject.FileReader.FileReaderClass;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    
    public void init() throws ServletException {
        super.init();
        FileReaderClass fileReader = new FileReaderClass();
        fileReader.importData();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                {
                    try {
                        addBook(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "update":
                    updateBook(request, response);
                    break;
                case "delete":
                    deleteBook(request, response);
                    break;
                default:
                    response.sendRedirect("index.html"); // Redirect to the home page or display an error
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                
                case "edit":
                    getBook(request, response);
                    break;
                default:
                    response.sendRedirect("index.html"); // Redirect to the home page or display an error
                    break;
            }
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        int year = Integer.parseInt(request.getParameter("year"));
        double price = Double.parseDouble(request.getParameter("price"));
        long authorId = Long.parseLong(request.getParameter("authorId"));

        Book book = new Book();
        book.setTitle(title);
        book.setCategory(category);
        book.setYear(year);
        book.setPrice(price);
        book.setAuthorId(authorId);

        bookDAO.insertBook(book); 
        response.sendRedirect("success.html");
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        int year = Integer.parseInt(request.getParameter("year"));
        double price = Double.parseDouble(request.getParameter("price"));
        long authorId = Long.parseLong(request.getParameter("authorId"));

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setCategory(category);
        book.setYear(year);
        book.setPrice(price);
        book.setAuthorId(authorId);

        try {
            bookDAO.updateBook(book);
            response.sendRedirect("success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
            
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));

        try {
            bookDAO.deleteBook(id);
            response.sendRedirect("success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

    private void getBook(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

   
}

