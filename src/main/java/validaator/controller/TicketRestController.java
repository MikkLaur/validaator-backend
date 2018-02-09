package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketRestController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> readAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        if(tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findOne(id);
        if(ticket == null) {
            return new ResponseEntity<>("Ticket with id: " + id + " not found",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
}
