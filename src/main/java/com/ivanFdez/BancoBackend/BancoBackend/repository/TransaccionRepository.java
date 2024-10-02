package com.ivanFdez.BancoBackend.BancoBackend.repository;

import com.ivanFdez.BancoBackend.BancoBackend.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    List<Transaccion> findByCuentaBancariaId(Long cuentaId);
    List<Transaccion> findByTarjetaCreditoId(Long tarjetaId);
}

