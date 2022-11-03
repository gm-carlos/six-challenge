package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.exceptions.SecurityNotFoundException;
import com.six.challenge.tradingplatform.model.api.v1.SecurityDto;
import com.six.challenge.tradingplatform.model.database.SecurityDao;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/security")
class SecurityController {

    private final SecurityJpaRepository repository;

    SecurityController(SecurityJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/findAll")
    List<SecurityDto> findAll() {
        return repository.findAll().stream()
                .map(SecurityDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    SecurityDto findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new SecurityNotFoundException(id))
                .toDto();
    }

    @PostMapping("/create")
    SecurityDto create(@RequestBody SecurityDao security) {
        return repository.save(security).toDto();
    }

    @PostMapping("/update")
    SecurityDto update(SecurityDao security) {
        return repository.save(security).toDto();
    }
}
