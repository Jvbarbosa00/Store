package br.com.jvbarbosa.store.controller;

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

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getByIdUsers(@PathVariable Long id){
        Optional<User> userData = userService.findById(id);

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails) {

        return userService.findById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());

            User updatedUser = userService.save(existingUser);
            return ResponseEntity.ok(updatedUser);
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
