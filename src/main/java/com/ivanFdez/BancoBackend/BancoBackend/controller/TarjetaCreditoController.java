package com.ivanFdez.BancoBackend.BancoBackend.controller;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.model.TarjetaCredito;
import com.ivanFdez.BancoBackend.BancoBackend.service.CuentaBancariaService;
import com.ivanFdez.BancoBackend.BancoBackend.service.TarjetaCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api")
public class TarjetaCreditoController {

    @Autowired
    private TarjetaCreditoService tarjetaCreditoService;
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/tarjetasCredito")
    @Operation(summary = "Devuelve todas las tarjetas de credito", tags = {"tarjetas de credito"})
    public List<TarjetaCredito> getAllTarjetasCredito() {return tarjetaCreditoService.getAllTarjetasCredito();}

    @GetMapping("/tarjetasCredito/{numeroTarjeta}")
    @Operation(summary = "Devuelve una tarjeta en funcion de su numero de tarjeta", tags = {"tarjetas de credito"})
    public ResponseEntity<TarjetaCredito> getTarjetaCreditoByNumeroTarjeta(String numeroTarjeta) {
        Optional<TarjetaCredito> optionalTarjetaCredito = tarjetaCreditoService.getTarjetaCreditoByNumeroTarjeta(numeroTarjeta);
        if (optionalTarjetaCredito.isPresent()) {
            TarjetaCredito tarjetaCredito = optionalTarjetaCredito.get();
            return new ResponseEntity<>(tarjetaCredito, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/tarjetasCredito")
    @Operation(summary = "Crear tarjeta de crédito", tags = {"tarjetas de credito"})
    public ResponseEntity<TarjetaCredito> crearTarjetaCredito(@RequestParam(required = false) String numeroTarjeta,
                                                              @RequestParam BigDecimal limiteCredito,
                                                              @RequestParam CuentaBancaria cuentaBancaria) {

        LocalDateTime fechaVencimiento = LocalDateTime.now();
        // Si no se proporciona un número de tarjeta, generarlo aleatoriamente
        if (numeroTarjeta == null || numeroTarjeta.isEmpty()) {
            numeroTarjeta = generarNumeroTarjeta();
        }

        // Verificar que el número de tarjeta no existe en la base de datos
        while (tarjetaCreditoService.existeNumeroTarjeta(numeroTarjeta)) {
            numeroTarjeta = generarNumeroTarjeta();  // Si ya existe, generar otro
        }

        // Obtener la cuenta bancaria asociada por ID
        Optional<CuentaBancaria> cuentaOptional = cuentaBancariaService.getCuentaBancariaByNumeroCuenta(cuentaBancaria.getNumeroCuenta());
        if (cuentaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Si no se encuentra la cuenta
        }

        TarjetaCredito nuevaTarjeta = new TarjetaCredito(numeroTarjeta, fechaVencimiento, limiteCredito, cuentaBancaria);
        TarjetaCredito tarjetaCreada = tarjetaCreditoService.createTarjetaCredito(nuevaTarjeta);

        return ResponseEntity.status(HttpStatus.CREATED).body(tarjetaCreada);
    }

    // Método para generar un número de tarjeta de crédito aleatorio de 16 dígitos
    private String generarNumeroTarjeta() {
        Random random = new Random();
        StringBuilder numeroTarjeta = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            numeroTarjeta.append(random.nextInt(10));  // Genera un número entre 0 y 9
        }
        return numeroTarjeta.toString();
    }


}
