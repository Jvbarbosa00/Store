package br.com.jvbarbosa.store.controller;

import br.com.jvbarbosa.store.dto.UserCreateDTO;
import br.com.jvbarbosa.store.dto.UserResponseDTO;
import br.com.jvbarbosa.store.dto.mapper.UserMapper;
import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO createDTO) {
        User userToSave = userMapper.toEntity(createDTO);

        User savedUser = userService.save(userToSave);

        UserResponseDTO responseDTO = userMapper.toDTO(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUser(){
        List<User> users = userService.findAll();

        List<UserResponseDTO> usersDTO = users.stream().map(user -> userMapper.toDTO(user)).toList();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByIdUsers(@PathVariable Long id){
        Optional<User> userData = userService.findById(id);

        return userData.map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable long id, @RequestBody UserCreateDTO userDTO) {

        return userService.findById(id).map(existingUser -> {
            existingUser.setName(userDTO.name());
            existingUser.setEmail(userDTO.email());
            existingUser.setPassword(userDTO.password());

            User updatedUser = userService.save(existingUser);

            UserResponseDTO responseDTO = userMapper.toDTO(updatedUser);
            return ResponseEntity.ok(responseDTO);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
