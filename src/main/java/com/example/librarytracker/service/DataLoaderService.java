package com.example.librarytracker.service;

import com.example.librarytracker.entity.Book;
import com.example.librarytracker.repository.BookRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {
    @Autowired
    private BookRepository bookRepository;

    public void importBooksFromCsv() {
        ClassPathResource resource = new ClassPathResource("books.csv");
        String filePath;

        try {
            filePath = resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Error accessing the CSV file: " + e.getMessage());
            return;
        }

        List<Book> books = new ArrayList<>();
        int rowCount = 0;

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] line;

            // Skip the header row
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null && rowCount < 10001) {
                try {
                    Book book = new Book();

                    String authors = line[7];
                    if (authors.length() > 255) {
                        System.err.println("Skipping book due to author's name being too long: " + authors);
                        continue;
                    }
                    book.setBook_id(parseLong(line[0], "Book ID"));
                    book.setGoodreadsBookId(parseLong(line[1], "Goodreads Book ID"));
                    book.setIsbn(line[5]);
                    book.setAuthors(line[7]);
                    book.setPublishYear(parseLong(line[8].split("\\.")[0], "Publish Year"));
                    book.setTitle(line[10]);
                    book.setLanguage(line[11]);
                    book.setAverageRating(parseDouble(line[12], "Average Rating"));
                    book.setRatingCount(parseInteger(line[13], "Rating Count"));
                    book.setImageUrl(line[21]);

                    books.add(book);
                    System.out.println("Book processed: " + book.getTitle());

                } catch (NumberFormatException e) {
                    System.err.println("Skipping line due to format error: " + String.join(",", line));
                    continue;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping line due to missing fields: " + String.join(",", line));
                    continue;
                } catch (Exception e) {
                    System.err.println("Skipping line due to unknown error: " + String.join(",", line));
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }

        if (!books.isEmpty()) {
            bookRepository.saveAll(books);
        }
    }

    private Long parseLong(String value, String fieldName) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid " + fieldName + ": " + value);
            throw e;
        }
    }

    private Double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid " + fieldName + ": " + value);
            throw e;
        }
    }

    private Integer parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid " + fieldName + ": " + value);
            return null;
        }
    }
}
