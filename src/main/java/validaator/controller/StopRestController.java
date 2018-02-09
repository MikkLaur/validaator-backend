package validaator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validaator.model.Stop;
import validaator.model.StopRepository;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopRestController {

    private final StopRepository stopRepository;

    @Autowired
    public StopRestController(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> readAll() {
        List<Stop> stops = stopRepository.findAll();
        if(stops.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stops, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) {
        Stop stop = stopRepository.findOne(id);
        if(stop == null) {
            return new ResponseEntity<>("User with id " + id + " not found",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stop, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody Stop input) {
        Stop stop = stopRepository.save(input); // Throws ConstraintViolationException
        return new ResponseEntity<>(stop, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/edit")
    ResponseEntity<?> put(@PathVariable Long id, @RequestBody Stop input) {
        Stop updatedStop = stopRepository.findOne(id);

        if(updatedStop == null) {
            return new ResponseEntity<>("Unable to update. Stop with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        updatedStop.setName(input.getName());
        return new ResponseEntity<>(stopRepository.save(updatedStop),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}/delete")
    ResponseEntity<?> delete(@PathVariable Long id) {
        Stop stop = stopRepository.findOne(id);

        if(stop == null) {
            return new ResponseEntity<>("Unable to delete. Stop with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        stopRepository.delete(id);
        return new ResponseEntity<Stop>(HttpStatus.NO_CONTENT);
    }
}
