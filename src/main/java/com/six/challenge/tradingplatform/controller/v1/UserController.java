package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.model.api.v1.UserInputDto;
import com.six.challenge.tradingplatform.model.api.v1.UserOutputDto;
import com.six.challenge.tradingplatform.model.database.UserDao;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserJpaRepository repository;

    UserController(UserJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/findAll")
    List<UserOutputDto> findAll() {
        return repository.findAll().stream()
                .map(UserDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findById/{id}")
    UserOutputDto findById(@PathVariable UUID id) {
        System.out.println(id);
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @GetMapping("/findByName/{name}")
    UserOutputDto findName(@PathVariable String name) {
        return repository.findByName(name).orElseThrow(
                () -> new UserNotFoundException(name)).toDto();
    }

    @PostMapping("/create")
    UserOutputDto create(@RequestBody UserInputDto userInput) {
        return repository.save(userInput.toDao()).toDto();
    }

    @PostMapping("/update")
    UserOutputDto update(UserInputDto userInput) {
        return repository.save(userInput.toDao()).toDto();
    }

}
