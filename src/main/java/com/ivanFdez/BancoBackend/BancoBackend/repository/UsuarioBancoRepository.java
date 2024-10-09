package com.ivanFdez.BancoBackend.BancoBackend.repository;

import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UsuarioBancoRepository extends JpaRepository<UsuarioBanco, Long> {
    Optional<UsuarioBanco> findByUsername(String username);

    Optional<UsuarioBanco> findById(BigInteger id);

    Optional<UsuarioBanco> findByEmail(String email);
}
