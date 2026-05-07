package com.example.chatbot.wa.dev.chatbotwa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/webhook")
public class WhatsAppWebhookController {

    @Value("${whatsapp.verify-token}")
    private String verifyToken;

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            log.info("Webhook verificado exitosamente.");
            return ResponseEntity.ok(challenge);
        } else {
            log.warn("Fallo en la verificación del webhook. Token recibido: {}", token);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> receiveMessage(@RequestBody String payload) {
        log.info("Payload recibido de WhatsApp Cloud API: \n{}", payload);
        
        // Es crítico responder con 200 OK inmediatamente para evitar que Meta marque timeout
        return ResponseEntity.ok().build();
    }
}
