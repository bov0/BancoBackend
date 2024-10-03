package com.ivanFdez.BancoBackend.BancoBackend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tarjetas_credito_banco")
public class TarjetaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "numero_tarjeta", nullable = false, unique = true, length = 16)
    private String numeroTarjeta;

    @Column(name = "fecha_vencimiento",nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(name = "limite_credito", nullable = false, precision = 15, scale = 2)
    private BigDecimal limiteCredito;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaBancaria cuentaBancaria;

    public TarjetaCredito() {}

    public TarjetaCredito(String numeroTarjeta, LocalDateTime fechaVencimiento, BigDecimal limiteCredito, CuentaBancaria cuentaBancaria) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.limiteCredito = limiteCredito;
        this.cuentaBancaria = cuentaBancaria;
    }
}
