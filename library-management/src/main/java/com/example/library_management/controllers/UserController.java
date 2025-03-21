package com.example.library_management.controllers;

import com.example.library_management.models.User;
import com.example.library_management.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    /**
     * Constructor de UserController.
     * @param userRepository repositorio de usuarios.
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * @return lista de usuarios.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @param email correo electrónico del usuario.
     * @return ResponseEntity con el usuario encontrado o 400 si no se encuentra.
     */
    @GetMapping("/search")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}

