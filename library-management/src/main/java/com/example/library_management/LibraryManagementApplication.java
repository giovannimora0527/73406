package com.example.library_management;

import com.example.library_management.models.Author;
import com.example.library_management.models.Book;
import com.example.library_management.models.User;
import com.example.library_management.repositories.AuthorRepository;
import com.example.library_management.repositories.BookRepository;
import com.example.library_management.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

/**
 * Clase principal que arranca la aplicación de gestión de biblioteca.
 */
@SpringBootApplication
public class LibraryManagementApplication {

    /**
     * Punto de entrada de la aplicación.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

    /**
     * Inicializa la base de datos con datos de prueba.
     *
     * @param userRepository repositorio de usuarios
     * @param authorRepository repositorio de autores
     * @param bookRepository repositorio de libros
     * @return un CommandLineRunner que inserta datos de prueba
     */
    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            userRepository.save(new User(null, "kevin@example.com", "Kevin Palacios"));
            userRepository.save(new User(null, "diego@example.com", "Diego Julian Gomez Velandia"));

            Author author = new Author(null, "Gabriel García Márquez", null);
            authorRepository.save(author);

            bookRepository.save(new Book(null, "Cien años de soledad", LocalDate.of(1967, 6, 5), author));
            bookRepository.save(new Book(null, "El amor en los tiempos del cólera", LocalDate.of(1985, 10, 10), author));
        };
    }
}

