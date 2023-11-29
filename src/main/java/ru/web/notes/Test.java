package ru.web.notes;


import ru.web.notes.dao.NoteDaoImpl;
import ru.web.notes.models.WebNote;

public class Test {
    public static void main(String[] args) {
        NoteDaoImpl noteDao = new NoteDaoImpl();
        WebNote webNote = new WebNote();
        webNote.setNote("test");
        System.out.println(noteDao.findById(3));
    }
}
