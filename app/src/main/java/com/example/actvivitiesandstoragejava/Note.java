package com.example.actvivitiesandstoragejava;

import java.io.Serializable;

public class Note implements Serializable {
    private String name;
    private String content;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return name;
    }
} 