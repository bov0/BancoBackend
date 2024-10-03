package com.ivanFdez.BancoBackend.BancoBackend.controller;

import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import com.ivanFdez.BancoBackend.BancoBackend.service.UsuariosBancoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsuarioBancoController {

    @Autowired
    private UsuariosBancoService usuarioBancoService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/usuariosBanco")
    @Operation(summary = "Devuelve todos los usuarios pertenecientes al banco", tags = {"usuarios banco"})
    public List<UsuarioBanco> getAllUsuariosBancos() {
        return usuarioBancoService.getAllUsuariosBanco();
    }

    @GetMapping("/usuariosBanco/{id}")
    @Operation(summary = "Devuelve el usuario con el id proporcionado perteneciente al banco", tags = {"usuarios banco"})
    public ResponseEntity<UsuarioBanco> getUsuarioBancoById(@PathVariable BigInteger id) {
        Optional<UsuarioBanco> optionalUsuariosBanco = usuarioBancoService.getUsuarioBancoById(id);

        if (optionalUsuariosBanco.isPresent()) {
            UsuarioBanco usuarioBanco = optionalUsuariosBanco.get();
            return new ResponseEntity<>(usuarioBanco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/usuariosBanco")
    @Operation(summary = "Crear usuario", tags = {"usuarios banco"})
    public ResponseEntity<UsuarioBanco> crearUsuario(@RequestParam String username,
                                                     @RequestParam String password,
                                                     @RequestParam String email) {

        String passwordEncriptada = passwordEncoder.encode(password);

        try {
            LocalDateTime fecha_creacion = LocalDateTime.now();
            UsuarioBanco usuarioBanco = usuarioBancoService.createUsuario(
                    new UsuarioBanco(username, passwordEncriptada, email, fecha_creacion)
            );

            return new ResponseEntity<>(usuarioBanco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/usuariosBanco/{id}")
    @Operation(summary = "Editar usuario", tags = {"usuarios banco"})
    public ResponseEntity<UsuarioBanco> actualizarUsuario(@PathVariable("id") BigInteger id,
                                                          @RequestParam(required = false) String username,
                                                          @RequestParam(required = false) String password,
                                                          @RequestParam(required = false) String email) {
        Optional<UsuarioBanco> usuarioOptional = usuarioBancoService.getUsuarioBancoById(id);

        if (usuarioOptional.isPresent()) {
            UsuarioBanco usuarioBanco = usuarioOptional.get();

            // Solo actualizamos los campos que son proporcionados en la petición
            if (username != null && !username.isEmpty()) {
                usuarioBanco.setUsername(username);
            }

            if (email != null && !email.isEmpty()) {
                usuarioBanco.setEmail(email);
            }

            if (password != null && !password.isEmpty()) {
                String passwordEncriptada = passwordEncoder.encode(password);
                usuarioBanco.setPassword(passwordEncriptada);
            }

            UsuarioBanco usuarioActualizado = usuarioBancoService.updateUsuario(usuarioBanco);

            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
