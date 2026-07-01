package com.example.demo.model;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.dto.CreateTicketDTO;

@Service
public class TicketClient {
  private final RestTemplate restTemplate;

  public TicketClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void criarTicket(CreateTicketDTO dto) {
    restTemplate.postForEntity(
      "http://localhost:8081/api/v1/tickets",
      dto,
      Void.class
    );
  }
}