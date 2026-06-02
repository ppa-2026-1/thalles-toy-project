package com.example.demo.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.AuthToken;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class AuthTokenRepository {

  private final EntityManager em;

  public AuthTokenRepository(EntityManager em) {
    this.em = em;
  }

  public Optional<AuthToken> findByToken(String token) {
    return em.createQuery(
          "FROM AuthToken a WHERE a.token = :token",
          AuthToken.class)
          .setParameter("token", token)
          .getResultStream()
          .findFirst();
  }

  @Transactional
  public void save(AuthToken authToken) {
    if (authToken.getId() == null) {
      em.persist(authToken);
    } else {
      em.merge(authToken);
    }
  }

  @Transactional
  public void delete(AuthToken authToken) {
    em.remove(
      em.contains(authToken)
        ? authToken
        : em.merge(authToken)
    );
  }
}