//https://github.com/glenVon/mic_usuarios
package com.altias.mic_autenticacion.controller;

import com.altias.mic_autenticacion.model.User;
import com.altias.mic_autenticacion.service.servicioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")//http://localhost:8080/api/v1/users (ruta para POSTMAN)
//SELECT * FROM bd_usuario.user;(en la base de datos bd_usuario)
public class Control {

    private final servicioUsuario servicioUsuario;

    @Autowired
    public Control(servicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping("/login")
    //http://localhost:8080/api/v1/users/login (en POSTMAN)
    public ResponseEntity<?> login(@RequestBody User user) {
        return servicioUsuario.authenticatedUser(user);
    }

    @PostMapping("/creador")
    //http://localhost:8080/api/v1/users/creador(en POSTMAN)
    //{"nombre":"Juan","nombreUsuario":"jdoe","password":"1234","apellido_paterno":"Doe","apellido_materno":"Smith","email":"
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return servicioUsuario.createUser(user);
    }
    
    @GetMapping
    //http://localhost:8080/api/v1/users (en POSTMAN)
    public ResponseEntity<List<User>> getAllUsers() {
        return servicioUsuario.getAllUsers();
    }
    
    @GetMapping("/usuarioPorId")
    //http://localhost:8080/api/v1/users/usuarioPorId?id=1
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        return servicioUsuario.getUserById(id);
    }

    //eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    //http://localhost:8080/api/v1/users/eliminar/1 (en POSTMAN)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return servicioUsuario.deleteUser(id);
    }
  
    // @GetMapping("/datos-protegidos")
    // //http://localhost:8080/api/v1/users/datos-protegidos?nombreUsuario=admin&password=1234    
}







