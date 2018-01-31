package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.Stop;
import validaator.model.Ticket;
import validaator.model.TicketRepository;
import validaator.model.UserRepository;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/tickets")
public class TicketRestController {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketRestController(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    Collection<Ticket> readAll(@PathVariable Long userId) {
        return this.ticketRepository.findByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{ticketId}")
    Ticket readOne(@PathVariable Long userId, @PathVariable Long ticketId) {
        //TODO Implement ReadOne
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@PathVariable Long userId, @RequestBody Ticket input) {
        return ResponseEntity.noContent().build();
    }

    //TODO: Create, Update, Delete
}
