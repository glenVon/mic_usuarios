package com.altias.mic_autenticacion.service;

import com.altias.mic_autenticacion.model.User;
//import com.altias.mic_autenticacion.model.usuario;
import com.altias.mic_autenticacion.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class servicioUsuario {

    private final UserRepository userRepository;

    @Autowired
    public servicioUsuario(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public ResponseEntity<?> login(@RequestBody User user) {
        User authenticatedUser = userRepository.findByNombreUsuarioAndPassword(user.getNombreUsuario(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }
    }

    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
        
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

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

    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    if (userRepository.existsById(id)) {
        userRepository.deleteById(id);  // Elimina directamente por ID
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado");
    } else {
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
    }

    public ResponseEntity<?> authenticatedUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticatedUser'");
    }


    // public ResponseEntity<?> getDatosProtegidos(
    //         @RequestParam String nombreUsuario,
    //         @RequestParam String password) {
        
    //     // Verificación básica de credenciales
    //     Map <String, String> response = new HashMap<>();
    //     if ("admin".equals(nombreUsuario) && "1234".equals(password)) {
    //         //return ResponseEntity.ok("Autorizado para entar a la MATRIX");
            
    //         response.put("mensaje", "Autorizado para entrar a la MATRIX");
    //         response.put("usuario", "Admistrador");
    //         return ResponseEntity.ok(response);
    //     }
    //     response.put("mensaje", "y tu quien eres?? vete");
    //         response.put("usuario", "no autorizado");
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    // }



}
