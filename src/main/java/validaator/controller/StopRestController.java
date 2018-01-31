package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.Stop;
import validaator.model.StopRepository;

import java.util.Collection;

@RestController
@RequestMapping("/stops")
public class StopRestController {

    private final StopRepository stopRepository;

    @Autowired
    public StopRestController(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Stop> readAll() { return stopRepository.findAll(); }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Stop readOne(@PathVariable Long id) { return stopRepository.findOne(id); }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody Stop input) {
        return ResponseEntity.noContent().build();
    }

    // TODO: Create, Update, Delete routes
}
