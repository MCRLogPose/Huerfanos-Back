package com.huerfanosSRL.Huerfanosback.auth.mapper;

import com.huerfanosSRL.Huerfanosback.auth.dto.UserResponse;
import com.huerfanosSRL.Huerfanosback.auth.model.ModuleModel;
import com.huerfanosSRL.Huerfanosback.auth.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(UserModel user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());

        if (user.getRole() != null) {
            response.setRoleName(user.getRole().getName());
            response.setRoleName(user.getRole().getName());

            List<String> modules = user.getRole().getModules()
                    .stream()
                    .map(ModuleModel::getNameModule) // 👈 usa el campo de tu entidad
                    .collect(Collectors.toList());

            response.setModules(modules);
        }

        return response;
    }
}
