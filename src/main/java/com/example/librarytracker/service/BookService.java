package com.example.librarytracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarytracker.entity.Book;
import com.example.librarytracker.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContaining(query);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book GetBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
