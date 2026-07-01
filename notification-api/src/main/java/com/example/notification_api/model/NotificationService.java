package com.example.notification_api.model;

import com.example.notification_api.dto.CreateNotificationDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  public void enviar(CreateNotificationDTO dto) {
    for (String email : dto.getDestinatarios()) {
    System.out.println("======================================");
    System.out.println("NOVA NOTIFICAÇÃO");
    System.out.println("--------------------------------------");
    System.out.println("Para: " + email);
    System.out.println("Assunto: " + dto.getAssunto());
    System.out.println("Mensagem:");
    System.out.println(dto.getMensagem());
    System.out.println("======================================");
}
  }
}