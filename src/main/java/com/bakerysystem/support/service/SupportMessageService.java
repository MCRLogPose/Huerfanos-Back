package com.bakerysystem.support.service;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.auth.repository.UserRepository;
import com.bakerysystem.support.dto.SupportMessageRequest;
import com.bakerysystem.support.dto.SupportMessageResponse;
import com.bakerysystem.support.model.SupportMessage;
import com.bakerysystem.support.repository.SupportMessageRepository;
import com.bakerysystem.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportMessageService {

    private final SupportMessageRepository supportRepo;
    private final UserRepository userRepo;
    private final EmailService emailService;

    private final String SUPPORT_EMAIL = "cruzriveraedwinmanuel@gmail.com";

    public List<SupportMessageResponse> getAllMessages() {
        return supportRepo.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SupportMessageResponse> getMessagesByUser(Long userId) {
        return supportRepo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private SupportMessageResponse toResponse(SupportMessage msg) {
        return SupportMessageResponse.builder()
                .id(msg.getId())
                .fromEmail(msg.getFromEmail())
                .toEmail(msg.getToEmail())
                .subject(msg.getSubject())
                .body(msg.getBody())
                .createdAt(msg.getCreatedAt())
                .userFullName(msg.getUser() != null
                        ? msg.getUser().getFirstName() + " " + msg.getUser().getLastName()
                        : "Invitado")
                .build();
    }

    public SupportMessageResponse sendMessage(SupportMessageRequest request) {
        User user = null;
        if (request.getUserId() != null) {
            user = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        SupportMessage message = SupportMessage.builder()
                .user(user)
                .fromEmail(request.getFromEmail())
                .toEmail(SUPPORT_EMAIL)
                .subject(request.getSubject())
                .body(request.getBody())
                .build();

        supportRepo.save(message);

        // Envía email
        emailService.sendEmail(request.getFromEmail(), SUPPORT_EMAIL, request.getSubject(), request.getBody());

        return SupportMessageResponse.builder()
                .id(message.getId())
                .fromEmail(message.getFromEmail())
                .toEmail(message.getToEmail())
                .subject(message.getSubject())
                .body(message.getBody())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
