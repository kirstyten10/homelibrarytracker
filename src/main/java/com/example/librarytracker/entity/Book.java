package com.example.librarytracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    private Long goodreadsBookId;
    private Long isbn;
    private String authors;
    private Long publishYear;
    private String title;
    private String language;
    private double averageRating;
    private String imageUrl;

}
