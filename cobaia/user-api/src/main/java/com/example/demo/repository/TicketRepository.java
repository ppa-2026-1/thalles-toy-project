package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class TicketRepository {
  private final EntityManager em;

  public TicketRepository(EntityManager em) {
    this.em = em;
  }

  public Optional<Ticket> findById(Integer id) {
    return em.createQuery("FROM Ticket t WHERE t.id = :id", Ticket.class)
      .setParameter("id", id)
      .getResultStream()
      .findFirst();
  }

  public List<Ticket> findAll() {
    return em.createQuery("FROM Ticket t", Ticket.class)
      .getResultList();
  }

  public List<Ticket> findByStatus(String status) {
    return em.createQuery("FROM Ticket t WHERE t.status = :status", Ticket.class)
      .setParameter("status", status)
      .getResultList();
  }

  public List<Ticket> findByCriador(String criador) {
    return em.createQuery("FROM Ticket t WHERE t.criador = :criador", Ticket.class)
      .setParameter("criador", criador)
      .getResultList();
  }

  @Transactional
  public void save(Ticket ticket) {
    if (ticket.getId() == null) {
      em.persist(ticket);
    } else {
      em.merge(ticket);
    }
  }
}