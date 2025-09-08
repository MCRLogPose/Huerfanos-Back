package com.huerfanosSRL.Huerfanosback.auth.security;

import com.huerfanosSRL.Huerfanosback.auth.model.UserModel;
import com.huerfanosSRL.Huerfanosback.auth.model.RoleModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserModel user;

    public CustomUserDetails(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleModel role = user.getRole();
        if (role == null) {
            return List.of(); // sin roles asignados
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // ya debe estar encriptada (BCrypt)
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // se usará como identificador único
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // podrías controlar fechas de expiración si lo implementas
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"BLOCKED".equalsIgnoreCase(String.valueOf(user.getState()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // podrías ligarlo con `lastPasswordChange` en CredentialsModel
    }

    @Override
    public boolean isEnabled() {
        String estado = String.valueOf(user.getState());
        return estado != null && (estado.equalsIgnoreCase("ACTIVE"));
    }

    public UserModel getUser() {
        return user;
    }
}
