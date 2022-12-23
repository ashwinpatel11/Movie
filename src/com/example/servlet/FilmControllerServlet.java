package com.example.servlet;

import com.example.entity.Film;
import com.example.dao.FilmDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 *
 * @author www.codejava.net
 */
public class FilmControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FilmDAO filmDAO;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        filmDAO = new FilmDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertFilm(request, response);
                    break;
                case "/delete":
                    deleteFilm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateFilm(request, response);
                    break;
                default:
                    listFilm(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void listFilm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Film> listFilm = filmDAO.listAllFilms();
        request.setAttribute("listFilm", listFilm);
        RequestDispatcher dispatcher = request.getRequestDispatcher("FilmList.jsp");
        dispatcher.forward(request, response);
    }
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("FilmForm.jsp");
        dispatcher.forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Film existingFilm = filmDAO.getFilm(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("FilmForm.jsp");
        request.setAttribute("film", existingFilm);
        dispatcher.forward(request, response);

    }
    private void insertFilm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        int year = Integer.parseInt(request.getParameter("year"));
        String director = request.getParameter("director");
        String stars = request.getParameter("stars");
        String review = request.getParameter("review");

        Film newFilm = new Film(title, year, director, stars, review);
        filmDAO.insertFilm(newFilm);
        response.sendRedirect("list");
    }
    private void updateFilm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int year = Integer.parseInt(request.getParameter("year"));
        String director = request.getParameter("director");
        String stars = request.getParameter("stars");
        String review = request.getParameter("review");

        Film film = new Film(id, title, year, director, stars, review);
        filmDAO.updateFilm(film);
        response.sendRedirect("list");
    }
    private void deleteFilm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        filmDAO.deleteFilm(id);
        response.sendRedirect("list");

    }

}
