package com.ivanFdez.BancoBackend.BancoBackend.service;

import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import com.ivanFdez.BancoBackend.BancoBackend.repository.UsuarioBancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosBancoService {

    @Autowired
    private UsuarioBancoRepository usuarioBancoRepository;

    public List<UsuarioBanco> getAllUsuariosBanco() { return usuarioBancoRepository.findAll();}

    public Optional<UsuarioBanco> getUsuarioBancoById(BigInteger id) {
        return usuarioBancoRepository.findById(id);
    }

    public UsuarioBanco createUsuario(UsuarioBanco usuarioBanco) {
        // Verificamos si el username es nulo
        if (usuarioBanco.getUsername() == null) {
            throw new IllegalArgumentException("El username no puede ser nulo");
        }

        // Si es necesario, agregar más validaciones para otros campos
        if (usuarioBanco.getPassword() == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }

        if (usuarioBanco.getEmail() == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }

        // Aquí puedes realizar la lógica para guardar el usuario en la base de datos
        return usuarioBancoRepository.save(usuarioBanco);  // Asegúrate de tener un repository configurado
    }
}
