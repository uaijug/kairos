package br.com.uaijug.kairos.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.uaijug.kairos.domain.Event;
import br.com.uaijug.kairos.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/app")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    @Inject
    private EventRepository eventRepository;

    /**
     * POST  /rest/events -> Create a new event.
     */
    @RequestMapping(value = "/rest/events",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Event event) {
        log.debug("REST request to save Event : {}", event);
        eventRepository.save(event);
    }

    /**
     * GET  /rest/events -> get all the events.
     */
    @RequestMapping(value = "/rest/events",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Event> getAll() {
        log.debug("REST request to get all Events");
        return eventRepository.findAll();
    }

    /**
     * GET  /rest/events/:id -> get the "id" event.
     */
    @RequestMapping(value = "/rest/events/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Event> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Event : {}", id);
        Event event = eventRepository.findOne(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/events/:id -> delete the "id" event.
     */
    @RequestMapping(value = "/rest/events/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventRepository.delete(id);
    }
}
