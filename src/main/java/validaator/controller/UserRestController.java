package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import validaator.model.TicketRepository;
import validaator.model.User;
import validaator.model.UserRepository;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public UserRestController(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<User> readAll() {
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    User readOne(@PathVariable Long id) {
        return userRepository.findOne(id);
    }

    //TODO: Create, Update, Delete
}
