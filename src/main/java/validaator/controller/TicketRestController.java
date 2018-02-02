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
@RequestMapping("/tickets")
public class TicketRestController {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketRestController(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    Collection<Ticket> readAll() {
        return this.ticketRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{ticketId}")
    Ticket readOne(@PathVariable Long ticketId) {
        return ticketRepository.findOne(ticketId);
    }
}
