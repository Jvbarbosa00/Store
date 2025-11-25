package br.com.jvbarbosa.store.service;

import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.repository.UserRepository;
import br.com.jvbarbosa.store.service.exception.EmailAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() && !existingUser.get().equals(user)){
            throw new  EmailAlreadyExistException("The email " + user.getEmail() + " Already exist!");
        }
        if (user.getSignUpDate() == null) {
            user.setSignUpDate(LocalDateTime.now());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
