package com.ivanFdez.BancoBackend.BancoBackend.repository;

import com.ivanFdez.BancoBackend.BancoBackend.model.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {

    Optional<TarjetaCredito> findByNumeroTarjeta(String numeroTarjeta);

    boolean existsByNumeroTarjeta(String numeroTarjeta);

    List<TarjetaCredito> findByCuentaBancariaId(Long cuentaId);
}
