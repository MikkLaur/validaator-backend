package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import validaator.model.Ticket;
import validaator.model.TicketRepository;
import validaator.model.UserRepository;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/transactions")
public class TicketRestController {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    // addTicket
    // readTicket
        // read latest ticket
        // ?? read oldest ticket
    // readTickets

    @Autowired
    public TicketRestController(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Ticket> readTicket(@PathVariable Long userId) {
        return this.ticketRepository.findByUserId(userId);
    }
}
