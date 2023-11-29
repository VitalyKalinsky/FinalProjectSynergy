package ru.web.notes.dao;

import ru.web.notes.models.WebNote;

import java.util.List;

public interface NoteDao {
    public int delete(int id);

    public List<WebNote> findAll();

    public WebNote findById(int id);

    public WebNote findByNote(String note);

    public int insert(WebNote webNote);

    public int update(WebNote webNote);
}
