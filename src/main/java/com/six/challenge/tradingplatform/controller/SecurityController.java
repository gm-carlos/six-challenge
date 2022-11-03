package com.six.challenge.tradingplatform.controller;

import com.six.challenge.tradingplatform.exceptions.SecurityNotFoundException;
import com.six.challenge.tradingplatform.model.Security;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
class SecurityController {

    private final SecurityJpaRepository repository;

    SecurityController(SecurityJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/security/findAll")
    List<Security> findAll() {
        return repository.findAll();
    }

    @GetMapping("/security/{id}")
    Security findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new SecurityNotFoundException(id));
    }

    @PostMapping("/security/create")
    Security create(@RequestBody Security user) {
        return repository.save(user);
    }

    @PostMapping("/security/update")
    Security update(Security user) {
        return repository.save(user);
    }
}
