package com.ivanFdez.BancoBackend.BancoBackend.controller;

import com.ivanFdez.BancoBackend.BancoBackend.model.Transaccion;
import com.ivanFdez.BancoBackend.BancoBackend.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/transacciones")
    @Operation(summary = "Obtener todas las transacciones", tags = {"transacciones"})
    public List<Transaccion> getAllTransacciones() {
        return transaccionService.getAllTransacciones();
    }

    @GetMapping("/transacciones/cuenta/{cuentaId}")
    @Operation(summary = "Obtener transacciones por cuenta bancaria", tags = {"transacciones"})
    public ResponseEntity<List<Transaccion>> getTransaccionesByCuenta(@PathVariable Long cuentaId) {
        List<Transaccion> transacciones = transaccionService.getTransaccionesByCuenta(cuentaId);
        if (transacciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transacciones);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transacciones);
    }

    @GetMapping("/transacciones/tarjeta/{tarjetaId}")
    @Operation(summary = "Obtener transacciones por tarjeta de crédito", tags = {"transacciones"})
    public ResponseEntity<List<Transaccion>> getTransaccionesByTarjeta(@PathVariable Long tarjetaId) {
        List<Transaccion> transacciones = transaccionService.getTransaccionesByTarjeta(tarjetaId);
        if (transacciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transacciones);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transacciones);
    }

    @PostMapping("/transacciones")
    @Operation(summary = "Crear una transacción", tags = {"transacciones"})
    public ResponseEntity<Transaccion> crearTransaccion(@RequestParam Transaccion.TipoTransaccion tipoTransaccion,
                                                        @RequestParam (required = true)BigDecimal monto,
                                                        @RequestParam(required = true) Long cuentaId,
                                                        @RequestParam(required = false) Long tarjetaId,
                                                        @RequestParam(required = false) Transaccion.Sector sector) {
        try {
            Transaccion nuevaTransaccion = transaccionService.createTransaccion(tipoTransaccion, monto, cuentaId, tarjetaId, sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

