package com.andyzhou.noteserver.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class Note {
    
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String content;
    // private LocalDateTime created_at;
    private LocalDateTime updated_at;

    // Getters and setters
    public UUID getId() {
         return id; 
        }

    public void setId(UUID id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) {
         this.title = title;
         }

    public String getContent() {
         return content; 
        }

    public void setContent(String content) { 
        this.content = content; 
    }

    // public LocalDateTime getCreatedAt() { 
    //     return created_at; 
    // }
    // public void setCreatedAt(LocalDateTime created_at) { 
    //     this.created_at = created_at; 
    // }

    public LocalDateTime getUpdatedAt() { 
        return updated_at; 
    }

    public void setUpdatedAt(LocalDateTime updated_at) { 
        this.updated_at = updated_at; 
    }
}

