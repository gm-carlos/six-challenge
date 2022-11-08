package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.exceptions.SecurityNotFoundException;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityInputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityUpdateInputDto;
import com.six.challenge.tradingplatform.model.database.SecurityDao;
import com.six.challenge.tradingplatform.repository.SecurityJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.SECURITY_V1)
class SecurityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    private final SecurityJpaRepository repository;

    SecurityController(SecurityJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<SecurityOutputDto> findAll() {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.FIND_ALL + " endpoint call");
        return repository.findAll().stream()
                .map(SecurityDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID_WITH_PARAM)
    SecurityOutputDto findById(@PathVariable UUID id) {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.FIND_BY_ID_WITH_PARAM + " endpoint call with id: " + id);
        return repository.findById(id).orElseThrow(
                        () -> new SecurityNotFoundException(id))
                .toDto();
    }

    @GetMapping(Endpoints.FIND_BY_NAME_WITH_PARAM)
    SecurityOutputDto findByName(@PathVariable String name) {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.FIND_BY_NAME_WITH_PARAM + " endpoint call with name: " + name);
        return repository.findByName(name).orElseThrow(
                        () -> new SecurityNotFoundException(name))
                .toDto();
    }

    @PostMapping(Endpoints.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    SecurityOutputDto create(@RequestBody SecurityInputDto security) {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.CREATE + " endpoint call with security: " + security.toString());
        return repository.save(security.toDao()).toDto();
    }

    @PostMapping(Endpoints.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    SecurityOutputDto update(SecurityUpdateInputDto securityInput) {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.UPDATE + " endpoint call with security: " + securityInput.toString());
        SecurityDao security = repository.findById(securityInput.getId()).orElseThrow(
                () -> new SecurityNotFoundException(securityInput.getId()));
        security.setName(securityInput.getName());
        return repository.save(security).toDto();
    }

    @DeleteMapping(Endpoints.DELETE_BY_ID)
    void delete(@PathVariable UUID id) {
        LOGGER.info(Endpoints.SECURITY_V1 + Endpoints.DELETE_BY_ID + " endpoint call with id: " + id);
        SecurityDao security = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        repository.delete(security);
    }
}
