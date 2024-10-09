package com.ivanFdez.BancoBackend.BancoBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "cuentas_bancarias_Banco")
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioBanco usuario;

    public CuentaBancaria() {}

    public CuentaBancaria(String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldo, UsuarioBanco usuario) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public enum TipoCuenta {
        AHORROS,
        CORRIENTE
    }
}