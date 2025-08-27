package com.andyzhou.noteserver;

import com.andyzhou.noteserver.model.Note;
import com.andyzhou.noteserver.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseTestRunner implements CommandLineRunner {

    private final NoteRepository repository;

    public DatabaseTestRunner(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Testing database connection...");

        List<Note> notes = repository.findAll();
        System.out.println("Fetched " + notes.size() + " notes from the database:");

        for (Note note : notes) {
            System.out.println(" - " + note.getTitle() + ": " + note.getContent());
        }
    }
}
