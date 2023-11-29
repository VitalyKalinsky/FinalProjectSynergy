package ru.web.notes.dao;

import ru.web.notes.models.WebNote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    private final String LOGIN;
    private final String PASSWORD;
    private static final String DELETE = "DELETE FROM note WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM note ORDER BY id";
    private static final String FIND_BY_NOTE = "SELECT * FROM note WHERE note=?";
    private static final String INSERT = "INSERT INTO note(note) VALUES(?)";
    private static final String UPDATE = "UPDATE note SET note=? WHERE id=?";

    public NoteDaoImpl() {
        File file = new File("src/main/webapp/resources/dbcredits.txt");
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            LOGIN = fileReader.readLine();
            PASSWORD = fileReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/notes", LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(WebNote webNote) {
        return delete(webNote.getId());
    }

    @Override
    public List<WebNote> findAll() {
        ArrayList<WebNote> list = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WebNote webNote = new WebNote();
                webNote.setId(resultSet.getInt("id"));
                webNote.setNote(resultSet.getString("note"));
                list.add(webNote);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public WebNote findById(int id) {
        return findAll().stream()
                .filter(a -> a.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public WebNote findByNote(String note) {
        return findAll().stream()
                .filter(a -> a.getNote().equals(note))
                .findAny()
                .orElse(null);
    }

    @Override
    public int insert(WebNote webNote) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, webNote.getNote());
            int res = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                webNote.setId(resultSet.getInt(1));
            }
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(WebNote webNote) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, webNote.getNote());
            statement.setInt(2, webNote.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
