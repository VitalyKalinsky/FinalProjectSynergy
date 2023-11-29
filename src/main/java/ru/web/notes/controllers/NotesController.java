package ru.web.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.web.notes.dao.NoteDaoImpl;
import ru.web.notes.models.WebNote;

@Controller
@RequestMapping("/notes")
public class NotesController {

	private final NoteDaoImpl noteDao;
	
	@Autowired
	public NotesController(NoteDaoImpl noteDao) {
		this.noteDao = noteDao;
	}
	
	@GetMapping()
	public String index(Model model) {
		model.addAttribute("notes", noteDao.findAll());
		return "notes/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		model.addAttribute("note", noteDao.findById(id));
		return "notes/show";
	}
	
	@GetMapping("/new")
	public String newNote(Model model) {
		model.addAttribute("webNote", new WebNote());
		return "notes/new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("webNote") WebNote webNote) {
		noteDao.insert(webNote);
		return "redirect:/notes";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("webNote", noteDao.findById(id));
		return "notes/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("webNote") WebNote webNote, @PathVariable("id") int id) {
		noteDao.update(webNote);
		return "redirect:/notes";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		noteDao.delete(id);
		return "redirect:/notes";
	}

}

