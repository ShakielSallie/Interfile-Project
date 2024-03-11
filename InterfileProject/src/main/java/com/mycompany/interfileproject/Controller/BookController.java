/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.Controller;

/**
 *
 * @author Shakiel
 */
import com.mycompany.interfileproject.Book.Dao.BookDAO;
import com.mycompany.interfileproject.Book.Model.Book;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ViewScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Book> books;
    private final BookDAO bookDAO = new BookDAO();

    // Getter and setter for books

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void loadBooks() {
        try {
            books = bookDAO.getAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }


    @PostConstruct
    public void init() {
        loadBooks();
    }

    

    
    public void addBook(Book book) {
        try {
            bookDAO.insertBook(book);
            loadBooks(); 
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error
        }
    }
    
    public void deleteBook(long id){
        try {
            bookDAO.deleteBook(id);
            loadBooks();
        } catch (Exception e) {
        }
    }
    
    public void updateBook(long id){
        try {
            bookDAO.deleteBook(id);
            loadBooks();
        } catch (Exception e) {
        }
    }
}
