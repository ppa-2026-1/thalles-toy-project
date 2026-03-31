package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.dto.TicketRequestDTO;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.entity.Ticket;
import com.example.demo.repository.entity.TicketStatus;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Transactional
  public void criarTicket(TicketRequestDTO dto) {
    Ticket ticket = new Ticket();
    ticket.setAcao(dto.acao());
    ticket.setObjeto(dto.objeto());
    ticket.setDetalhes(dto.detalhes());
    ticket.setCriador(dto.criador());
    
    if (dto.observadores() != null && !dto.observadores().isEmpty()) {
      ticket.setObservadores(String.join(", ", dto.observadores()));
    }

    ticket.setDestinatario(
      (dto.destinatario() == null || dto.destinatario().isEmpty()) 
      ? dto.criador() : dto.destinatario()
    );

    ticket.setStatus(TicketStatus.ABERTO);
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
  public void atualizarStatus(Integer id, TicketStatus novoStatus, String motivo, String responsavel) {

    Ticket ticket = buscarPorId(id);

    if (TicketStatus.EM_ANDAMENTO == novoStatus) {
      ticket.setResponsavel(responsavel);
    }

    if (TicketStatus.CANCELADO == novoStatus) {
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