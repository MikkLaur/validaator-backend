package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import validaator.model.TicketRepository;
import validaator.model.UserRepository;

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
}
