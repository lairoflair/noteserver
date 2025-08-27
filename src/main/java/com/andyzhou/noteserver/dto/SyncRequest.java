package com.andyzhou.noteserver.dto;

import com.andyzhou.noteserver.model.Note;
import java.time.LocalDateTime;
import java.util.List;

public class SyncRequest {
    private LocalDateTime lastSync;
    private List<Note> notes;

    // Getters and Setters
    public LocalDateTime getLastSync() { return lastSync; }
    public void setLastSync(LocalDateTime lastSync) { this.lastSync = lastSync; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
}