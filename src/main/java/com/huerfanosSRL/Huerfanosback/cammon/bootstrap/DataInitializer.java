package com.huerfanosSRL.Huerfanosback.cammon.bootstrap;

import com.huerfanosSRL.Huerfanosback.auth.model.RoleModel;
import com.huerfanosSRL.Huerfanosback.auth.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new RoleModel("ADMIN"));
                roleRepository.save(new RoleModel("GERENTE"));
                roleRepository.save(new RoleModel("OPERADOR"));
                roleRepository.save(new RoleModel("REPOSTERO"));
                roleRepository.save(new RoleModel("CUSTOMER"));
            }
        };
    }
}
