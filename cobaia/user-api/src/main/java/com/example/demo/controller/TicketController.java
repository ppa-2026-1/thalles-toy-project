package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.TicketService;
import com.example.demo.model.dto.TicketRequestDTO;
import com.example.demo.repository.entity.Ticket;
import com.example.demo.repository.entity.TicketStatus;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void criar(@RequestBody TicketRequestDTO ticketDto) {
    ticketService.criarTicket(ticketDto);
  } 

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Ticket>> listar() {
    return ResponseEntity.ok(ticketService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ticket> buscar(@PathVariable Integer id) {
    return ResponseEntity.ok(ticketService.buscarPorId(id));
  }

  @PatchMapping("/{id}/status")
  public void atualizarStatus(
    @PathVariable Integer id,
    @RequestBody Map<String, String> body
  ) {
    TicketStatus novoStatus = TicketStatus.valueOf(body.get("status")
      .toUpperCase().replace(" ", "_"));
    
    ticketService.atualizarStatus(
      id,
      novoStatus,
      body.get("motivo"),
      body.get("responsavel")
    );
  }
}