package com.example.librarytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librarytracker.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
