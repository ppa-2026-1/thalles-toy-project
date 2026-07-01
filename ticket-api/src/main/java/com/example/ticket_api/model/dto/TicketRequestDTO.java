package com.example.ticket_api.model.dto;

import java.util.List;

public record TicketRequestDTO(
  String acao,
  String objeto,
  String detalhes,
  String criador,
  String destinatario,
  List<String> observadores
) {}