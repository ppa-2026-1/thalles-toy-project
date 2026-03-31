package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.entity.Ticket;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Transactional
  public void criarTicket(Ticket ticket) {

    if (ticket.getDestinatario() == null || ticket.getDestinatario().isEmpty()) {
      ticket.setDestinatario(ticket.getCriador());
    }

    if (ticket.getStatus() == null) {
      ticket.setStatus("Aberto");
    }

    ticket.setUpdatedAt(LocalDateTime.now());
    ticketRepository.save(ticket);
  }

  public List<Ticket> listarTodos() {
    return ticketRepository.findAll();
  }

  public Ticket buscarPorId(Integer id) {
    return ticketRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
  }

  @Transactional
  public void atualizarStatus(Integer id, String novoStatus, String motivo, String responsavel) {

    Ticket ticket = buscarPorId(id);

    if ("Em andamento".equalsIgnoreCase(novoStatus)) {
      ticket.setResponsavel(responsavel);
    }

    if ("Cancelado".equalsIgnoreCase(novoStatus)) {
      if (motivo == null || motivo.isEmpty()) {
        throw new IllegalArgumentException("Motivo é obrigatório ao cancelar");
      }
      ticket.setMotivo(motivo);
    }

    ticket.setStatus(novoStatus);
    ticket.setUpdatedAt(LocalDateTime.now());
    ticketRepository.save(ticket);
  }
}