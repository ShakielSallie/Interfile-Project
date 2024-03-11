/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.Book.Dao;

/**
 *
 * @author Shakiel
 */




import com.mycompany.interfileproject.Book.Model.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";

   

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public void insertBook(Book book) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO book (title, category, year, price, authorId, createddate, modifieddate) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getCategory());
            statement.setInt(3, book.getYear());
            statement.setDouble(4, book.getPrice());
            // Assuming authorId is not null and is set elsewhere
            statement.setNull(5, java.sql.Types.BIGINT);
            statement.setTimestamp(6, Timestamp.valueOf(book.getCreatedDate()));
            statement.setTimestamp(7, Timestamp.valueOf(book.getModifiedDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Inserting book failed, no ID obtained.");
            }
        }
    }

    public void updateBook(Book book) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE book SET title=?, category=?, year=?, price=?, authorId=?, modifieddate=? WHERE id=?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getCategory());
            statement.setInt(3, book.getYear());
            statement.setDouble(4, book.getPrice());
            statement.setNull(5, java.sql.Types.BIGINT);
            statement.setTimestamp(6, Timestamp.valueOf(book.getModifiedDate()));
            statement.setLong(7, book.getId());
            statement.executeUpdate();
        }
    }

    public void deleteBook(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE id=?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
    
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM book");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setCategory(resultSet.getString("category"));
                book.setYear(resultSet.getInt("year"));
                book.setPrice(resultSet.getDouble("price"));
                book.setAuthorId(resultSet.getLong("authorId")); // Assuming authorId is retrieved from the database
                book.setCreatedDate(resultSet.getTimestamp("createddate").toLocalDateTime());
                book.setModifiedDate(resultSet.getTimestamp("modifieddate").toLocalDateTime());
                books.add(book);
            }
        }
        return books;
    }

     
}


