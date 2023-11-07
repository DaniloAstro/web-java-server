package com.example.webjavaserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    public final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        // I'm not sure null is possible here,
        // but I would just return empty list in that case.
        // Empty list on server side - is not a problem with request itself, so 400 is not the best option
        // My choice would be to make 'users' final and remove null checks
//        if (users == null) {
//            return ResponseEntity.badRequest().build();
//        }
        return ResponseEntity.ok(users);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = findUserById(id);
        // you probably meant to check user == null, not users (plural)
        // also, I would return 404, not 400
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        else return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable
                                           Integer id, @RequestBody User request){
        User user = findUserById(id);
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

    public User findUserById(int id) {
        // nice use of streams
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
}