package com.ivanFdez.BancoBackend.BancoBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Table(name = "usuarios_banco")
public class UsuarioBanco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fecha_creacion;

    public UsuarioBanco() {}

    public UsuarioBanco(String username, String password, String email, Date fecha_creacion) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
    }
}
