package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    Stop create(@RequestBody Stop input) {
        return stopRepository.save(input);
    }
}
