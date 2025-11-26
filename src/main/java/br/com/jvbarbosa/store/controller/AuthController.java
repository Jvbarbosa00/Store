package br.com.jvbarbosa.store.controller;

import br.com.jvbarbosa.store.dto.LoginRequestDTO;
import br.com.jvbarbosa.store.dto.ResponseDTO;
import br.com.jvbarbosa.store.dto.UserCreateDTO;
import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.repository.UserRepository;
import br.com.jvbarbosa.store.service.TokenService;
import br.com.jvbarbosa.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body){
        User user = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserCreateDTO dto){
        if (userRepository.findByEmail(dto.email()).isPresent()) return ResponseEntity.badRequest().build();

        User newUser = new User();

        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password());

        this.userService.save(newUser);

        String token = this.tokenService.generateToken(newUser);

        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }
}
