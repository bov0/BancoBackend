package com.ivanFdez.BancoBackend.BancoBackend.repository;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {

    boolean existsByNumeroCuenta(String numeroCuenta);

    // Método para encontrar una cuenta bancaria por su número
    Optional<CuentaBancaria> findByNumeroCuenta(String numeroCuenta);

    // Método para encontrar cuentas bancarias por ID de usuario
    List<CuentaBancaria> findByUsuarioId(BigInteger usuarioId);

    // Método para encontrar una cuenta bancaria por usuario
    Optional<CuentaBancaria> findByUsuario(UsuarioBanco usuario);
}
