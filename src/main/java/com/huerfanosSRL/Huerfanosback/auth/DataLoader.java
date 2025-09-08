package com.huerfanosSRL.Huerfanosback.auth;

import com.huerfanosSRL.Huerfanosback.auth.model.ModuleModel;
import com.huerfanosSRL.Huerfanosback.auth.repository.ModuleRepository;
import com.huerfanosSRL.Huerfanosback.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ModuleRepository moduloRepository;

    @Autowired
    private RoleRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
            ModuleModel crearIncidencia = new ModuleModel();


    }
}

