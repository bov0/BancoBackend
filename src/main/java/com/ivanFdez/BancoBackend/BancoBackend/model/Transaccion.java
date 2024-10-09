package com.ivanFdez.BancoBackend.BancoBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transacciones_Banco")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transaccion", nullable = false)
    private TipoTransaccion tipoTransaccion;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = true)
    private CuentaBancaria cuentaBancaria;

    // Cambiar el tipo de tarjetaCredito de BigInteger a TarjetaCredito
    @ManyToOne
    @JoinColumn(name = "tarjeta_id", nullable = true)
    private TarjetaCredito tarjetaCredito; // Corregido

    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private Sector sector;

    public Transaccion() {}

    public Transaccion(TipoTransaccion tipoTransaccion, BigDecimal monto, CuentaBancaria cuentaBancaria, TarjetaCredito tarjetaCredito, Sector sector, LocalDateTime fecha) {
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.cuentaBancaria = cuentaBancaria;
        this.tarjetaCredito = tarjetaCredito; // Corregido
        this.sector = sector;
        this.fecha = fecha;
    }

    public enum TipoTransaccion {
        DEPOSITO, RETIRO, PAGO, TRANSFERENCIA
    }

    public enum Sector {
        COMPRAS, OCIO, TRANSPORTE, RESTAURANTE, SERVICIOS, OTROS
    }
}