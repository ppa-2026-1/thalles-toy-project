package com.example.demo.repository.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum TicketStatus {
  ABERTO, 
  EM_ANDAMENTO, 
  CONCLUIDO, 
  CANCELADO;

  @JsonCreator
  public static TicketStatus decode(String value) {
    if (value == null) return null;
    
    String normalized = value.toUpperCase()
      .replace(" ", "_")
      .replace("-", "_");
                              
    return Stream.of(TicketStatus.values())
      .filter(status -> status.name().equals(normalized))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + value));
  }
}