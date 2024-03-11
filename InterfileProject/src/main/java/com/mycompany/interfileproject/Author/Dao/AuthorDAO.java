/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfileproject.Author.Dao;

/**
 *
 * @author Shakiel
 */
import com.mycompany.interfileproject.Author.Model.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/bookstore";
    private final String JDBC_USERNAME = "root";
    private final String JDBC_PASSWORD = "root";

    public AuthorDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public void insertAuthor(Author author) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO author (name, createddate, modifieddate) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, Timestamp.valueOf(author.getCreatedDate()));
            statement.setTimestamp(3, Timestamp.valueOf(author.getModifiedDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("No ID obtained.");
            }
        }
    }

    public void updateAuthor(Author author) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE author SET name=?, modifieddate=? WHERE id=?")) {
            statement.setString(1, author.getName());
            statement.setTimestamp(2, Timestamp.valueOf(author.getModifiedDate()));
            statement.setLong(3, author.getId());
            statement.executeUpdate();
        }
    }

    public void deleteAuthor(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Author WHERE id=?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Author getAuthorById(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAuthorFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM author");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                authors.add(extractAuthorFromResultSet(resultSet));
            }
        }
        return authors;
    }

    private Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setName(resultSet.getString("name"));
        author.setCreatedDate(resultSet.getTimestamp("createddate").toLocalDateTime());
        author.setModifiedDate(resultSet.getTimestamp("modifieddate").toLocalDateTime());
        return author;
    }
}



