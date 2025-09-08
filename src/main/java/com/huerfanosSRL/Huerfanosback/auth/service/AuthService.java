package com.huerfanosSRL.Huerfanosback.auth.service;

import com.huerfanosSRL.Huerfanosback.auth.dto.RegisterRequest;
import com.huerfanosSRL.Huerfanosback.auth.dto.RegisterResponse;
import com.huerfanosSRL.Huerfanosback.auth.dto.LoginRequest;
import com.huerfanosSRL.Huerfanosback.auth.dto.LoginResponse;
import com.huerfanosSRL.Huerfanosback.auth.dto.UserResponse;
import com.huerfanosSRL.Huerfanosback.auth.mapper.UserMapper;
import com.huerfanosSRL.Huerfanosback.auth.model.*;
import com.huerfanosSRL.Huerfanosback.auth.repository.*;
import com.huerfanosSRL.Huerfanosback.auth.security.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private CredentialsRepository userCredentialsRepository;

    @Autowired
    private TwoFARepository user2FARepository;

    @Autowired
    private TokenRepository userTokensRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    // ---- REGISTRO ----
    public RegisterResponse register(RegisterRequest request) throws BadRequestException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("El username ya está en uso");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El correo ya está en uso");
        }

        UserModel user = new UserModel();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        // Rol por defecto
        String roleName = request.getRoleName() != null ? request.getRoleName() : "CUSTOMER";
        RoleModel role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        // Estado
        user.setState(StateUser.ACTIVE);

        // Guardar usuario
        UserModel savedUser = userRepository.save(user);

        // Guardar credenciales
        CredentialsModel credentials = new CredentialsModel();
        credentials.setUser(savedUser);
        credentials.setPasswordHash(encodedPassword);
        credentials.setSalt(null);
        userCredentialsRepository.save(credentials);

        // Guardar configuración 2FA
        TwoFAModel twoFA = new TwoFAModel();
        twoFA.setUser(savedUser);
        twoFA.setSecretKey("DEFAULT");
        twoFA.setEnabled(false);
        user2FARepository.save(twoFA);

        // Generar token JWT
        String jwt = jwtService.generateToken(savedUser);

        return new RegisterResponse(jwt);
    }

    // ---- LOGIN ----
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        UserModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getState() == StateUser.LOCKED || user.getState() == StateUser.INACTIVE) {
            throw new RuntimeException("Usuario bloqueado o inactivo");
        }

        String jwt = jwtService.generateToken(user);

        // Registrar token
        TokenModel tokenRecord = new TokenModel();
        tokenRecord.setUser(user);
        tokenRecord.setRefreshToken(jwt);
        tokenRecord.setRevoked(false);
        tokenRecord.setExpired(false);
        tokenRecord.setIpAddress(httpRequest.getRemoteAddr());
        tokenRecord.setUserAgent(httpRequest.getHeader("User-Agent"));
        userTokensRepository.save(tokenRecord);

        List<String> modules = user.getRole().getModules()
                .stream().map(ModuleModel::getNameModule).toList();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return new LoginResponse(jwt, user.getUsername(), user.getRole().getName(), modules);
    }

    // ---- LISTAR USUARIOS ----
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ---- LISTAR USUARIOS POR ROL ----
    public List<UserResponse> getAllByRole(String roleName) {
        List<UserModel> users = userRepository.findByRole_NameIgnoreCase(roleName);
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}
