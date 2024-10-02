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
    private String numero_cuenta;

    public enum TipoCuenta {
        AHORRO,
        CORRIENTE
    }
    @Column(name = "tipo_cuenta" , nullable = false)
    private TipoCuenta tipo_cuenta;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioBanco usuario;

    public CuentaBancaria() {}

    public CuentaBancaria(String numero_cuenta, TipoCuenta tipo_cuenta, BigDecimal saldo, UsuarioBanco usuario) {
        this.numero_cuenta = numero_cuenta;
        this.tipo_cuenta = tipo_cuenta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

}
