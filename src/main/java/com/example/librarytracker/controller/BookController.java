package com.example.librarytracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarytracker.service.DataLoaderService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private DataLoaderService dataLoaderService;

    @PostMapping("/import")
    public String importBooks() {
        dataLoaderService.importBooksFromCsv();
        return "Books imported successfully!";
    }
}
