package com.andyzhou.noteserver.repository;

import com.andyzhou.noteserver.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    // Spring Data JPA automatically provides findAll(), findById(), save(), deleteById() etc.
}