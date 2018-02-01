package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import validaator.model.Ticket;
import validaator.model.TicketRepository;
import validaator.model.User;
import validaator.model.UserRepository;

import java.net.URI;
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

    @RequestMapping(method = RequestMethod.POST, value="/{userId}/tickets")
    ResponseEntity<?> createTicket(@PathVariable Long userId, @RequestBody Ticket input) {
        Ticket result = ticketRepository.save(new Ticket(input.getUser(), input.getStop()));
        //Ticket result = ticketRepository.save(new Ticket(userRepository.findOne(userId), input.getStop()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}/tickets/{ticketId}")
                .buildAndExpand(result.getUser().getId(), result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

/*    //This works too, but ResponseMapping is less flexible?
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value="/{userId}/tickets")
    Ticket createTicket(@PathVariable Long userId, @RequestBody Ticket input) {
        return ticketRepository.save(new Ticket(input.getUser(), input.getStop()));
    }
*/
    //TODO: Create, Update, Delete
}

