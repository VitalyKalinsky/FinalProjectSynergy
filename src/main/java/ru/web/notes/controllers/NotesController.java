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

import ru.web.notes.dao.WebNoteDAO;
import ru.web.notes.models.WebNote;

@Controller
@RequestMapping("/notes")
public class NotesController {
	
	private final WebNoteDAO webNoteDAO;
	
	@Autowired
	public NotesController(WebNoteDAO webNoteDAO) {
		this.webNoteDAO = webNoteDAO;
	}
	
	@GetMapping()
	public String index(Model model) {
		model.addAttribute("notes",webNoteDAO.index());
		return "notes/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		model.addAttribute("note", webNoteDAO.show(id));
		return "notes/show";
	}
	
	@GetMapping("/new")
	public String newNote(Model model) {
		model.addAttribute("webNote", new WebNote());
		return "notes/new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("webNote") WebNote webNote) {
		webNoteDAO.save(webNote);
		return "redirect:/notes";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("webNote",webNoteDAO.show(id));
		return "notes/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("webNote") WebNote webNote, @PathVariable("id") int id) {
		webNoteDAO.update(id, webNote);
		return "redirect:/notes";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		webNoteDAO.delete(id);
		return "redirect:/notes";
	}

}

