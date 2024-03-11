/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.FileReader;

/**
 *
 * @author Shakiel
 */
import com.mycompany.interfileproject.Book.Model.Book;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileReaderClass {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String JDBC_USERNAME = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    public void importData() {
    ClassLoader classLoader = getClass().getClassLoader();
    URL resourceUrl = classLoader.getResource("bookstore.txt");

    if (resourceUrl == null) {
        System.out.println("File not found!");
        return;
    }

    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
        List<Book> books = readBooksFromFile(resourceUrl.getFile()); 
        for (Book book : books) {
            insertBook(connection, book);
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}



    private List<Book> readBooksFromFile(String filename) throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 5) {
                    Book book = new Book();
                    book.setTitle(parts[1]);
                    book.setYear(Integer.parseInt(parts[2]));
                    book.setPrice(Double.parseDouble(parts[3].substring(2)));
                    book.setCategory(parts[4]);
                    book.setCreatedDate(LocalDateTime.now());
                    book.setModifiedDate(LocalDateTime.now());
                    books.add(book);
                }
            }
        }
        return books;
    }

    private void insertBook(Connection connection, Book book) throws SQLException {
        String insertBookQuery = "INSERT INTO Book (title, category, year, price, createddate, modifieddate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertBookQuery)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getCategory());
            statement.setInt(3, book.getYear());
            statement.setDouble(4, book.getPrice());
            statement.setObject(5, book.getCreatedDate());
            statement.setObject(6, book.getModifiedDate());
            statement.executeUpdate();
        }
    }
}

