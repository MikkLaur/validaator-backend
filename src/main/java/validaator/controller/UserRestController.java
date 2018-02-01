package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.Ticket;
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


    /* Ticketing */

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/tickets")
    Collection<Ticket> readAllTickets(@PathVariable Long userId) {
        return this.ticketRepository.findByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/tickets/{ticketId}")
    Ticket readOneTicket(@PathVariable Long userId, @PathVariable Long ticketId) {
        return ticketRepository.findByUserId(userId).stream()
                .filter(ticket -> ticket.getId().longValue() == ticketId)
                .findFirst()
                .orElseThrow(
                        () -> new UserNotFoundException(userId)
                );
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@PathVariable Long userId, @RequestBody Ticket input) {
        return ResponseEntity.noContent().build();
    }

    //TODO: Create, Update, Delete
}

