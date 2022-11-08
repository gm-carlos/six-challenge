package com.six.challenge.tradingplatform.controller.v1;

import com.six.challenge.tradingplatform.constants.Endpoints;
import com.six.challenge.tradingplatform.model.api.v1.user.UserInputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import com.six.challenge.tradingplatform.model.api.v1.user.UserUpdateInputDto;
import com.six.challenge.tradingplatform.model.database.UserDao;
import com.six.challenge.tradingplatform.exceptions.UserNotFoundException;
import com.six.challenge.tradingplatform.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.USER_V1)
class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserJpaRepository repository;

    UserController(UserJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping(Endpoints.FIND_ALL)
    List<UserOutputDto> findAll() {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.FIND_ALL + " endpoint call");
        return repository.findAll().stream()
                .map(UserDao::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.FIND_BY_ID_WITH_PARAM)
    UserOutputDto findById(@PathVariable UUID id) {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.FIND_BY_ID_WITH_PARAM + " endpoint call with id: " + id);
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)).toDto();
    }

    @GetMapping(Endpoints.FIND_BY_NAME_WITH_PARAM)
    UserOutputDto findName(@PathVariable String name) {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.FIND_BY_NAME_WITH_PARAM + " endpoint call with name: " + name);
        return repository.findByName(name).orElseThrow(
                () -> new UserNotFoundException(name)).toDto();
    }

    @PostMapping(Endpoints.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    UserOutputDto create(@RequestBody UserInputDto userInput) {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.CREATE + " endpoint call with user: " + userInput.toString());
        return repository.save(userInput.toDao()).toDto();
    }

    @PostMapping(Endpoints.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    UserOutputDto update(UserUpdateInputDto userInput) {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.UPDATE + " endpoint call with user: " + userInput.toString());
        UserDao user = repository.findById(userInput.getId()).orElseThrow(
                () -> new UserNotFoundException(userInput.getName()));
        user.setName(userInput.getName());
        user.setPassword(userInput.getPassword());
        return repository.save(user).toDto();
    }

    @DeleteMapping(Endpoints.DELETE_BY_ID)
    void delete(@PathVariable UUID id) {
        LOGGER.info(Endpoints.USER_V1 + Endpoints.DELETE_BY_ID + " endpoint call with id: " + id);
        UserDao user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        repository.delete(user);
    }
}
