package com.example.ticket_api.client;

import com.example.ticket_api.model.dto.CreateNotificationDTO;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationClient {
  private final RestTemplate restTemplate = new RestTemplate();
  private static final String URL = "http://localhost:8082/api/v1/notifications";

  public void enviar(List<String> destinatarios, String mensagem) {
    CreateNotificationDTO dto = new CreateNotificationDTO();

    dto.setDestinatarios(destinatarios);
    dto.setAssunto("Novo Ticket");
    dto.setMensagem(mensagem);

    restTemplate.postForEntity(
      URL,
      dto,
      Void.class
    );
  }
}