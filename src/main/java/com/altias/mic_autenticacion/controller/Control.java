//https://github.com/glenVon/mic_usuarios
package com.altias.mic_autenticacion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;

import com.altias.mic_autenticacion.repository.UserRepository;
import com.altias.mic_autenticacion.usuario.User;


@RestController
@RequestMapping("/api/v1/users")//http://localhost:8080/api/v1/users (ruta para POSTMAN)
//SELECT * FROM bd_usuario.user;(en la base de datos bd_usuario)
public class Control {

    @Autowired
    private UserRepository userRepository;

    // Método para autenticar un usuario
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User authenticatedUser = userRepository.findByNombreUsuarioAndPassword(user.getNombreUsuario(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
    
    // Método para crear un nuevo usuario
    @PostMapping("/creador")
    //http://localhost:8080/api/v1/users/creador(en POSTMAN)
    //{"nombre":"Juan","nombreUsuario":"jdoe","password":"1234","apellido_paterno":"Doe","apellido_materno":"Smith","email":"
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
        
    }

    @GetMapping
    //http://localhost:8080/api/v1/users (en POSTMAN)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usuarioPorId")
    //http://localhost:8080/api/v1/users/usuarioPorId?id=1
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            //retorna solo email y nombre
            User userResponse = new User();

            userResponse.setNombre(user.getNombre());
            userResponse.setNombreUsuario(user.getNombreUsuario());
            userResponse.setApellido_paterno(user.getApellido_paterno());
            userResponse.setApellido_materno(user.getApellido_materno());
            userResponse.setEmail(user.getEmail());
            userResponse.setFecha_nacimiento(user.getFecha_nacimiento());
            userResponse.setId(user.getId());
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/datos-protegidos")
    //http://localhost:8080/api/v1/users/datos-protegidos?nombreUsuario=admin&password=1234
    public ResponseEntity<?> getDatosProtegidos(
            @RequestParam String nombreUsuario,
            @RequestParam String password) {
        
        // Verificación básica de credenciales
        Map <String, String> response = new HashMap<>();
        if ("admin".equals(nombreUsuario) && "1234".equals(password)) {
            //return ResponseEntity.ok("Autorizado para entar a la MATRIX");
            
            response.put("mensaje", "Autorizado para entrar a la MATRIX");
            response.put("usuario", "Admistrador");
            return ResponseEntity.ok(response);
        }
        response.put("mensaje", "y tu quien eres?? vete");
            response.put("usuario", "no autorizado");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    //eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    //http://localhost:8080/api/v1/users/eliminar/1 (en POSTMAN)  
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    if (userRepository.existsById(id)) {
        userRepository.deleteById(id);  // Elimina directamente por ID
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado");
    } else {
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
}

    
}







