package com.andyzhou.noteserver.controller;

import com.andyzhou.noteserver.dto.SyncRequest;
import com.andyzhou.noteserver.model.Note;
import com.andyzhou.noteserver.repository.NoteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository repository;

    public NoteController(NoteRepository repository) {
        this.repository = repository;
    }

    // ✅ Existing endpoints can stay here

    // Add the syncNotes method here
    
    @PostMapping("/sync")
    @Transactional
    public SyncRequest syncNotes(@RequestBody SyncRequest request) {
        List<Note> updatedNotes = new ArrayList<>();
        for (UUID deletedId : request.getDeletedNotes()) {
            System.out.println("Deleting note with ID: " + deletedId);
            deleteNoteById(deletedId);
        }
        for (Note clientNote : request.getNotes()) {
            try {
                Note syncedNote = saveNoteSafely(clientNote);
                updatedNotes.add(syncedNote);
            } catch (Exception e) {
                System.err.println("Failed to sync note " + clientNote.getId() + ": " + e.getMessage());
            }
        }
        System.out.println("Updated notes: " + updatedNotes);
        SyncRequest response = new SyncRequest();
        response.setNotes(updatedNotes);
        response.setLastSync(LocalDateTime.now());
        response.setDeletedNotes(null);
        return response;
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Note saveNoteSafely(Note note) {
        Note existing = repository.findById(note.getId()).orElse(null);
        if (existing != null) {
            System.out.println("Updating note with ID: " + note.getId());
            existing.setTitle(note.getTitle());
            existing.setContent(note.getContent());
            existing.setUpdatedAt(note.getUpdatedAt());
            return em.merge(existing);
        } else {
            System.out.println("Creating new note with ID: " + note.getId());
            return em.merge(note);
        }
    }

    public void deleteNoteById(UUID id) {
        repository.deleteById(id);
    }
}
