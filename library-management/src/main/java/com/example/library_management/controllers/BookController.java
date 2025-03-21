package com.example.library_management.controllers;

import com.example.library_management.models.Book;
import com.example.library_management.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar los endpoints relacionados con los libros.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    /**
     * Constructor de BookController.
     * @param bookRepository repositorio de libros.
     */
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retorna la lista de todos los libros.
     * @return lista de libros.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Busca un libro por su título exacto (case sensitive).
     * @param title el título exacto del libro.
     * @return ResponseEntity con el libro o 404 si no se encuentra.
     */
    @GetMapping("/title")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retorna una lista de libros publicados en un rango de fechas.
     * @param start fecha de inicio en formato YYYY-MM-DD.
     * @param end fecha final en formato YYYY-MM-DD.
     * @return ResponseEntity con la lista de libros o 400 en caso de error.
     */
    @GetMapping("/dates")
    public ResponseEntity<List<Book>> getBooksByDateRange(@RequestParam String start, @RequestParam String end) {
        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            List<Book> books = bookRepository.findByPublicationDateBetween(startDate, endDate);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retorna una lista de libros asociados a un autor.
     * @param id el ID del autor.
     * @return lista de libros.
     */
    @GetMapping("/author/{id}")
    public List<Book> getBooksByAuthor(@PathVariable Long id) {
        return bookRepository.findByAuthorId(id);
    }
}

