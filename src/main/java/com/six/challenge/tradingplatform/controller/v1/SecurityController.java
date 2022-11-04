package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.SecurityNotFoundException;
import com.six.challenge.tradingplatform.model.api.v1.SecurityInputDto;
import com.six.challenge.tradingplatform.model.api.v1.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.database.SecurityDao;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.SECURITY_V1)
class SecurityController {

    private final SecurityJpaRepository repository;

    SecurityController(SecurityJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<SecurityOutputDto> findAll() {
        return repository.findAll().stream()
                .map(SecurityDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID)
    SecurityOutputDto findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new SecurityNotFoundException(id))
                .toDto();
    }

    @GetMapping(Endpoints.FIND_BY_NAME)
    SecurityOutputDto findByName(@PathVariable String name) {
        return repository.findByName(name).orElseThrow(
                        () -> new SecurityNotFoundException(name))
                .toDto();
    }

    @PostMapping(Endpoints.CREATE)
    SecurityOutputDto create(@RequestBody SecurityInputDto security) {
        return repository.save(security.toDao()).toDto();
    }

    @PostMapping(Endpoints.UPDATE)
    SecurityOutputDto update(SecurityDao security) {
        return repository.save(security).toDto();
    }
}
