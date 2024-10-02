package com.ivanFdez.BancoBackend.BancoBackend.repository;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {

    Optional<CuentaBancaria> findByNumeroCuenta(String numeroCuenta);

    boolean existsByNumeroCuenta(String numeroCuenta);
}
