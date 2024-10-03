package com.ivanFdez.BancoBackend.BancoBackend.service;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.repository.CuentaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaBancariaService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    // Obtener todas las cuentas bancarias
    public List<CuentaBancaria> getAllCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }



    // Obtener una cuenta bancaria por su número de cuenta
    public Optional<CuentaBancaria> getCuentaBancariaByNumeroCuenta(String numeroCuenta) {
        return cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta);
    }

    // Verificar si un número de cuenta ya existe
    public boolean existeNumeroCuenta(String numeroCuenta) {
        return cuentaBancariaRepository.existsByNumeroCuenta(numeroCuenta);
    }

    public CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria) {
        if (cuentaBancaria.getNumeroCuenta() == null) {
            throw new IllegalArgumentException("El numero de cuenta no puede ser nulo");
        }

        if (cuentaBancaria.getTipoCuenta() == null) {
            throw new IllegalArgumentException("El tipo de cuenta no puede ser nulo");
        }

        if (cuentaBancaria.getSaldo() == null) {
            throw new IllegalArgumentException("El saldo no puede ser nulo");
        }

        return cuentaBancariaRepository.save(cuentaBancaria);
    }
}
