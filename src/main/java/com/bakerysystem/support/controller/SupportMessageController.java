package com.bakerysystem.support.controller;

import com.bakerysystem.support.dto.SupportMessageRequest;
import com.bakerysystem.support.dto.SupportMessageResponse;
import com.bakerysystem.support.service.SupportMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportMessageController {

    private final SupportMessageService supportService;

    @PostMapping("/send")
    public ResponseEntity<SupportMessageResponse> sendMessage(@RequestBody SupportMessageRequest request) {
        return ResponseEntity.ok(supportService.sendMessage(request));
    }
    // ADMIN: Listar todos los mensajes
    @GetMapping("/messages")
    public ResponseEntity<List<SupportMessageResponse>> getAllMessages() {
        return ResponseEntity.ok(supportService.getAllMessages());
    }

    // USUARIO: Listar solo los suyos
    @GetMapping("/messages/user/{userId}")
    public ResponseEntity<List<SupportMessageResponse>> getMessagesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(supportService.getMessagesByUser(userId));
    }

}