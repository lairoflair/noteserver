package com.andyzhou.noteserver.controller;

import com.andyzhou.noteserver.dto.SyncRequest;
import com.andyzhou.noteserver.model.Note;
import com.andyzhou.noteserver.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public List<Note> syncNotes(@RequestBody SyncRequest request) {
        final LocalDateTime effectiveLastSync = request.getLastSync() == null ? LocalDateTime.MIN : request.getLastSync();

        // 1. Merge client notes into the DB
        if (request.getNotes() != null) {
            for (Note clientNote : request.getNotes()) {
                Optional<Note> existing = repository.findById(clientNote.getId());
                if (existing.isPresent()) {
                    Note dbNote = existing.get();
                    // Update DB note only if client version is newer
                    if (clientNote.getUpdatedAt().isAfter(dbNote.getUpdatedAt())) {
                        dbNote.setTitle(clientNote.getTitle());
                        dbNote.setContent(clientNote.getContent());
                        dbNote.setUpdatedAt(clientNote.getUpdatedAt());
                        repository.save(dbNote);
                    }
                } else {
                    // New note from client → save it
                    repository.save(clientNote);
                }
            }
        }

        // 2. Return all notes updated since last sync
        return repository.findAll().stream()
                .filter(note -> note.getUpdatedAt().isAfter(effectiveLastSync))
                .collect(Collectors.toList());
    }
}
