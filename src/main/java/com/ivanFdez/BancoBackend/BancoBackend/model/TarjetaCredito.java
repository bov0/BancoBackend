package com.ivanFdez.BancoBackend.BancoBackend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "tarjetas_credito_banco")
public class TarjetaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "numero_tarjeta", nullable = false, unique = true, length = 16)
    private String numero_tarjeta;

    @Column(name = "fecha_vencimiento",nullable = false)
    private LocalDateTime fecha_vencimiento;

    @Column(name = "limite_credito", nullable = false, precision = 15, scale = 2)
    private BigDecimal limite_credito;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CuentaBancaria cuentaBancaria;

    public TarjetaCredito() {}

    public TarjetaCredito(String numero_tarjeta, LocalDateTime fecha_vencimiento, BigDecimal limite_credito,CuentaBancaria cuentaBancaria) {
        this.numero_tarjeta = numero_tarjeta;
        this.fecha_vencimiento = fecha_vencimiento;
        this.limite_credito = limite_credito;
        this.cuentaBancaria = cuentaBancaria;
    }
}
