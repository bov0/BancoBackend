package com.ivanFdez.BancoBackend.BancoBackend.service;

import com.ivanFdez.BancoBackend.BancoBackend.model.Transaccion;
import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.model.TarjetaCredito;
import com.ivanFdez.BancoBackend.BancoBackend.repository.TransaccionRepository;
import com.ivanFdez.BancoBackend.BancoBackend.repository.CuentaBancariaRepository;
import com.ivanFdez.BancoBackend.BancoBackend.repository.TarjetaCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    public List<Transaccion> getAllTransacciones() {
        return transaccionRepository.findAll();
    }

    public List<Transaccion> getTransaccionesByCuenta(Long cuentaId) {
        return transaccionRepository.findByCuentaBancariaId(cuentaId);
    }

    public List<Transaccion> getTransaccionesByTarjeta(Long tarjetaId) {
        return transaccionRepository.findByTarjetaCreditoId(tarjetaId);
    }

    public Transaccion createTransaccion(Transaccion.TipoTransaccion tipoTransaccion, BigDecimal monto,
                                         Long cuentaId, Long tarjetaId, Transaccion.Sector sector) {
        LocalDateTime fecha = LocalDateTime.now();
        CuentaBancaria cuentaBancaria = null;
        TarjetaCredito tarjetaCredito = null;

        // Verificar si se proporciona una cuenta bancaria o tarjeta de crédito
        if (cuentaId != null) {
            Optional<CuentaBancaria> cuentaOpt = cuentaBancariaRepository.findById(cuentaId);
            if (cuentaOpt.isPresent()) {
                cuentaBancaria = cuentaOpt.get();
            } else {
                throw new IllegalArgumentException("Cuenta bancaria no encontrada con ID: " + cuentaId);
            }
        }

        if (tarjetaId != null) {
            Optional<TarjetaCredito> tarjetaOpt = tarjetaCreditoRepository.findById(tarjetaId);
            if (tarjetaOpt.isPresent()) {
                tarjetaCredito = tarjetaOpt.get();
            } else {
                throw new IllegalArgumentException("Tarjeta de crédito no encontrada con ID: " + tarjetaId);
            }
        }

        // Crear la transacción
        Transaccion nuevaTransaccion = new Transaccion(tipoTransaccion, monto, cuentaBancaria, tarjetaCredito, sector, fecha);
        return transaccionRepository.save(nuevaTransaccion);
    }
}
