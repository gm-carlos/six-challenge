package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.model.api.v1.UserInputDto;
import com.six.challenge.tradingplatform.model.api.v1.UserOutputDto;
import com.six.challenge.tradingplatform.model.database.UserDao;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.USER_V1)
class UserController {

    private final UserJpaRepository repository;

    UserController(UserJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<UserOutputDto> findAll() {
        return repository.findAll().stream()
                .map(UserDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID)
    UserOutputDto findById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @GetMapping(Endpoints.FIND_BY_NAME)
    UserOutputDto findName(@PathVariable String name) {
        return repository.findByName(name).orElseThrow(
                () -> new UserNotFoundException(name)).toDto();
    }

    @PostMapping(Endpoints.CREATE)
    UserOutputDto create(@RequestBody UserInputDto userInput) {
        return repository.save(userInput.toDao()).toDto();
    }

    @PostMapping(Endpoints.UPDATE)
    UserOutputDto update(UserInputDto userInput) {
        return repository.save(userInput.toDao()).toDto();
    }

}
