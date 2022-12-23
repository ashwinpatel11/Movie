package com.example.dao;

import com.example.entity.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractDAO.java
 * This DAO class provides CRUD database operations for the table film
 * in the database.
 *
 * @author www.codejava.net
 */
public class FilmDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public FilmDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean insertFilm(Film film) throws SQLException {
        int id = 0;
        connect();
        String sql = "SELECT * from films ORDER BY id DESC limit 1";
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id");
            System.out.println(rs.getInt("id"));
        }
        film.setId(id+1);
        sql = "INSERT INTO films (id,title,year,director,stars,review) VALUES (?,?,?,?,?,?)";

        statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, film.getId());
        statement.setString(2, film.getTitle());
        statement.setInt(3, film.getYear());
        statement.setString(4, film.getDirector());
        statement.setString(5, film.getStars());
        statement.setString(6, film.getReview());
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public List<Film> listAllFilms() throws SQLException {
        List<Film> listFilms = new ArrayList<>();
        String sql = "SELECT * FROM films order by id desc";
        connect();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int year = Integer.parseInt(resultSet.getString("year"));
            String director = resultSet.getString("director");
            String stars = resultSet.getString("stars");
            String review = resultSet.getString("review");
            Film film = new Film(id, title, year, director, stars, review);
            listFilms.add(film);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listFilms;
    }

    public boolean deleteFilm(int id) throws SQLException {
        String sql = "DELETE FROM films where id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    public boolean updateFilm(Film film) throws SQLException {
        String sql = "UPDATE films SET title = ?, year = ?, director = ?,stars = ?,review = ?";
        sql += " WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, film.getTitle());
        statement.setInt(2, film.getYear());
        statement.setString(3, film.getDirector());
        statement.setString(4, film.getStars());
        statement.setString(5, film.getReview());
        statement.setInt(6, film.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }

    public Film getFilm(int id) throws SQLException {
        Film film = null;
        String sql = "SELECT * FROM films WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String title = resultSet.getString("title");
            int year = resultSet.getInt("year");
            String director = resultSet.getString("director");
            String stars = resultSet.getString("stars");
            String review = resultSet.getString("review");
            film = new Film(id, title, year, director, stars, review);
        }

        resultSet.close();
        statement.close();

        return film;
    }
}
