package com.ivanFdez.BancoBackend.BancoBackend.Auth;

import com.ivanFdez.BancoBackend.BancoBackend.model.UsuarioBanco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;
    UsuarioBanco usuarioBanco;
}
