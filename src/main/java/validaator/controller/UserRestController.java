package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.Ticket;
import validaator.model.TicketRepository;
import validaator.model.User;
import validaator.model.UserRepository;

import java.util.Collection;
import java.util.List;

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
    ResponseEntity<?> readAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) {
        User user = userRepository.findOne(id);
        if(user == null) {
            return new ResponseEntity<>("User with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody User input) {
        User user = userRepository.save(input); // Throws ConstraintViolationException, caught by RestResponseEntityExceptionHandler
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/edit")
    ResponseEntity<?> put(@PathVariable Long id, @RequestBody User input) {
        User updatedUser = userRepository.findOne(id);

        if(updatedUser == null) {
            return new ResponseEntity<>("Unable to update. User with id: " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }

        updatedUser.setEmail(input.getEmail());
        return new ResponseEntity<>(userRepository.save(updatedUser),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}/delete")
    ResponseEntity<?> delete(@PathVariable Long id) {
        User user = userRepository.findOne(id);

        if(user == null) {
            return new ResponseEntity<>("Unable to delete. User with id: " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        userRepository.delete(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /* -- Ticketing -----------------------------------------------*/

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/tickets")
    ResponseEntity<?> readAllTickets(@PathVariable Long userId) {
        Collection<Ticket> tickets = ticketRepository.findByUserId(userId);

        if(tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/tickets/{ticketId}")
    ResponseEntity<?> readOneTicket(@PathVariable Long userId, @PathVariable Long ticketId) {
        Ticket ticket = ticketRepository.findByUserId(userId).stream()
                .filter(t -> t.getId().longValue() == ticketId)
                .findFirst()
                .orElse(null);
        if(ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{userId}/tickets")
    ResponseEntity<?> createTicket(@PathVariable Long userId, @RequestBody Ticket input) {
        Ticket ticket = ticketRepository.save(new Ticket(input.getUser(), input.getStop()));
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
}

