package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.AuthService;
import com.example.demo.model.dto.LoginDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(
      @RequestBody LoginDTO loginDTO) {

    var token = authService.login(loginDTO);

    return ResponseEntity.ok(
      Map.of("token", token)
    );
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      @RequestHeader("Authorization") String authHeader) {

    if (authHeader != null &&
      authHeader.startsWith("Bearer ")) {

      var token = authHeader.substring(7);
      authService.logout(token);
    }

    return ResponseEntity.ok().build();
  }
}