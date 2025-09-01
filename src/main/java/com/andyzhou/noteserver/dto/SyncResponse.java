package com.andyzhou.noteserver.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.andyzhou.noteserver.model.Note;

public class SyncResponse {
    private LocalDateTime lastSync;
    private List<Note> updatedNotes;
    private List<String> deletedNotes;
}
