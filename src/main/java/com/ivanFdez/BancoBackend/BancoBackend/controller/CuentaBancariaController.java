package com.ivanFdez.BancoBackend.BancoBackend.controller;

import com.ivanFdez.BancoBackend.BancoBackend.model.CuentaBancaria;
import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import com.ivanFdez.BancoBackend.BancoBackend.service.CuentaBancariaService;
import com.ivanFdez.BancoBackend.BancoBackend.service.UsuariosBancoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @Autowired
    private UsuariosBancoService usuariosBancoService;

    @GetMapping("/cuentasBancarias")
    @Operation(summary = "Devuelve todas las cuentas bancarias", tags = {"cuentas bancarias"})
    public List<CuentaBancaria> getAllCuentasBancarias() { return cuentaBancariaService.getAllCuentasBancarias();}

    @GetMapping("/cuentasBancarias/{numeroCuenta}")
    @Operation(summary = "Devuelve datos de cuenta mediante su numero",tags = {"cuentas bancarias"})
    public ResponseEntity<CuentaBancaria> getCuentaBancariaByNumeroCuenta(@PathVariable String numeroCuenta) {
        Optional<CuentaBancaria> optionalCuentaBancaria = cuentaBancariaService.getCuentaBancariaByNumeroCuenta(numeroCuenta);

        if (optionalCuentaBancaria.isPresent()) {
            CuentaBancaria cuentaBancaria = optionalCuentaBancaria.get();
            return new ResponseEntity<>(cuentaBancaria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/cuentasBancarias")
    @Operation(summary = "Crear cuenta bancaria", tags = {"cuentas bancarias"})
    public ResponseEntity<CuentaBancaria> crearCuentaBancaria(@RequestParam CuentaBancaria.TipoCuenta tipoCuenta,
                                                              @RequestParam BigDecimal saldo,
                                                              @RequestParam BigInteger usuarioId) {
        // Generar número de cuenta aleatorio de 20 dígitos
        String numeroCuenta = generarNumeroCuenta();

        // Verificar que el número de cuenta no existe en la base de datos
        while (cuentaBancariaService.existeNumeroCuenta(numeroCuenta)) {
            numeroCuenta = generarNumeroCuenta();  // Si ya existe, generar otro
        }

        // Obtener el usuario por ID
        Optional<UsuarioBanco> usuarioOptional = usuariosBancoService.getUsuarioBancoById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UsuarioBanco usuario = usuarioOptional.get();

        CuentaBancaria nuevaCuenta = new CuentaBancaria(numeroCuenta, tipoCuenta, saldo, usuario);
        CuentaBancaria cuentaCreada = cuentaBancariaService.createCuentaBancaria(nuevaCuenta);

        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaCreada);
    }

    // Método para generar un número de cuenta aleatorio de 20 dígitos
    private String generarNumeroCuenta() {
        Random random = new Random();
        StringBuilder numeroCuenta = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            numeroCuenta.append(random.nextInt(10));  // Genera un número entre 0 y 9
        }
        return numeroCuenta.toString();
    }
}
