package com.six.challenge.tradingplatform.controller;

import com.six.challenge.tradingplatform.model.User;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
class UserController {

    private final UserJpaRepository repository;

    UserController(UserJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users/findAll")
    List<User> findAll() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    User findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    @PostMapping("/users/create")
    User create(@RequestBody User user) {
        return repository.save(user);
    }

    @PostMapping("/users/update")
    User update(User user) {
        return repository.save(user);
    }

}
