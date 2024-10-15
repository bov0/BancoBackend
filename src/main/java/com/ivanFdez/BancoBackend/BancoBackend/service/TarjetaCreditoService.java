package com.ivanFdez.BancoBackend.BancoBackend.service;

import com.ivanFdez.BancoBackend.BancoBackend.model.TarjetaCredito;
import com.ivanFdez.BancoBackend.BancoBackend.model.Transaccion;
import com.ivanFdez.BancoBackend.BancoBackend.repository.TarjetaCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaCreditoService {

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    public List<TarjetaCredito> getAllTarjetasCredito() {
        return tarjetaCreditoRepository.findAll();
    }

    public Optional<TarjetaCredito> getTarjetaCreditoByNumeroTarjeta(String numeroTarjeta) {
        return tarjetaCreditoRepository.findByNumeroTarjeta(numeroTarjeta);
    }

    public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjetaCredito) {
        return tarjetaCreditoRepository.save(tarjetaCredito);
    }

    public boolean existeNumeroTarjeta(String numeroTarjeta) {
        return tarjetaCreditoRepository.existsByNumeroTarjeta(numeroTarjeta);
    }

    public List<TarjetaCredito> getTarjetasByCuenta(Long cuentaId) {
        return tarjetaCreditoRepository.findByCuentaBancariaId(cuentaId);
    }
}
