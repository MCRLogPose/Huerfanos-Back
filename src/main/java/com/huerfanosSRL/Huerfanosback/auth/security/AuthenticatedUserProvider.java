package com.huerfanosSRL.Huerfanosback.auth.security;

import com.huerfanosSRL.Huerfanosback.auth.model.UserModel;
import com.huerfanosSRL.Huerfanosback.auth.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    private final UserRepository userRepository;

    public AuthenticatedUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene el nombre de usuario actual desde el contexto de seguridad.
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }

    /**
     * Obtiene el ID del usuario autenticado.
     * El ID debe ser cargado en el Authentication (por ejemplo en JwtAuthenticationFilter).
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Usuario no autenticado");
        }

        Object details = authentication.getDetails();
        if (details instanceof Long userId) {
            return userId;
        }

        throw new IllegalStateException("El ID del usuario no está disponible en el contexto");
    }

    /**
     * Devuelve la entidad UserModel del usuario autenticado.
     */
    public UserModel getCurrentUser() {
        Long userId = getCurrentUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));
    }
}
