package com.ivanFdez.BancoBackend.BancoBackend.service;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import com.ivanFdez.BancoBackend.BancoBackend.repository.CuentaBancariaRepository;
import com.ivanFdez.BancoBackend.BancoBackend.repository.UsuarioBancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaBancariaService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;
    private UsuarioBancoRepository usuarioBancoRepository;

    public List<CuentaBancaria> getAllCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }

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

    public List<CuentaBancaria> getCuentasBancariasByUsuarioId(BigInteger usuarioId) {
        return cuentaBancariaRepository.findByUsuarioId(usuarioId);
    }

    public CuentaBancaria getCuentaBancariaByUsuarioId(BigInteger usuarioId) {
        UsuarioBanco usuario = usuarioBancoRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cuentaBancariaRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("No se encontró la cuenta bancaria para el usuario proporcionado"));
    }
}
