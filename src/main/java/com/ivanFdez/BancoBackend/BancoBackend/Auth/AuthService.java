package com.ivanFdez.BancoBackend.BancoBackend.Auth;

import com.ivanFdez.BancoBackend.BancoBackend.model.LoginRequest;
import com.ivanFdez.BancoBackend.BancoBackend.model.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivanFdez.BancoBackend.BancoBackend.Jwt.JwtService;
import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import com.ivanFdez.BancoBackend.BancoBackend.repository.UsuarioBancoRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioBancoRepository usuarioBancoRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UsuarioBanco user= usuarioBancoRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken((UserDetails) user);
        return AuthResponse.builder()
                .token(token)
                .usuarioBanco(user)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (usuarioBancoRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Crear el nuevo usuario
        UsuarioBanco newUsuario = new UsuarioBanco();
        newUsuario.setUsername(request.getUsername());
        newUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        newUsuario.setEmail(request.getEmail());
        LocalDateTime fecha_creacion = LocalDateTime.now();
        newUsuario.setFechaCreacion(fecha_creacion);

        // Guardar el nuevo usuario en el repositorio
        usuarioBancoRepository.save(newUsuario);

        // Generar y devolver el token JWT
        String token = jwtService.getToken((UserDetails) newUsuario);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
