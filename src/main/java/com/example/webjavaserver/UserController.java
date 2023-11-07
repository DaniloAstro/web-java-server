package com.example.webjavaserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    public List<User> users = new ArrayList<>();
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        if (users == null){
            return ResponseEntity.badRequest().build();
        }
        else return ResponseEntity.ok(users);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = findUserById(id);
        if (users == null){
            return ResponseEntity.badRequest().build();
        }
        else return ResponseEntity.ok(user);
    }
    public User findUserById(int id){
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable
                                           Integer id, @RequestBody User request){
        User user = findUserById(id);
        if (users == null){
            return ResponseEntity.badRequest().build();
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setFriends(request.getFriends());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable
                                           Integer id) {
        User user = findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        users.remove(user);
        return ResponseEntity.noContent().build();
    }

    // naming convention is to use plural nouns for endpoints
    @PostMapping ("/users")
    public ResponseEntity<User> createUser(@RequestBody User request) {
        //request validation
        if (findUserById(request.getId()) != null) {
            return ResponseEntity.badRequest().build();
        }

        // better not to use user provided id, generate your own with AtomicLong or UUID
        User u = new User(request.id, request.name,
                request.email, request.friends);
        users.add(u);
        return ResponseEntity.ok(u);
    }
}