package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.TransactionRepository;
import validaator.model.User;
import validaator.model.UserRepository;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserRestController(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<User> readAll() {
        return userRepository.findAll();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    User readUser(@PathVariable Long id) {
        return userRepository.findOne(id);
    }
}
