package br.com.jvbarbosa.store.dto.mapper;

import br.com.jvbarbosa.store.dto.UserCreateDTO;
import br.com.jvbarbosa.store.dto.UserResponseDTO;
import br.com.jvbarbosa.store.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreateDTO dto){
        User user = new User();

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return user;
    }

    public UserResponseDTO toDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
