package br.com.uaijug.kairos.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.uaijug.kairos.domain.Speaker;
import br.com.uaijug.kairos.repository.SpeakerRepository;
import br.com.uaijug.kairos.utils.MediaTypeUtils;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Speaker.
 */
@RestController
@RequestMapping("/app")
public class SpeakerResource {

	private final Logger log = LoggerFactory.getLogger(SpeakerResource.class);

	@Inject
	private SpeakerRepository speakerRepository;

	/**
	 * POST /rest/speakers -> Create a new speaker.
	 */
	@RequestMapping(value = "/rest/speakers", method = RequestMethod.POST, produces = MediaTypeUtils.APPLICATION_JSON_UTF8_VALUE)
	@Timed
	public void create(@RequestBody Speaker speaker) {
		this.log.debug("REST request to save Speaker : {}", speaker);
		this.speakerRepository.save(speaker);
	}

	/**
	 * GET /rest/speakers -> get all the speakers.
	 */
	@RequestMapping(value = "/rest/speakers", method = RequestMethod.GET, produces = MediaTypeUtils.APPLICATION_JSON_UTF8_VALUE)
	@Timed
	public List<Speaker> getAll() {
		this.log.debug("REST request to get all Speakers");
		return this.speakerRepository.findAll();
	}

	/**
	 * GET /rest/speakers/:id -> get the "id" speaker.
	 */
	@RequestMapping(value = "/rest/speakers/{id}", method = RequestMethod.GET, produces = MediaTypeUtils.APPLICATION_JSON_UTF8_VALUE)
	// @RequestMapping(value = "/rest/speakers/{id}", method =
	// RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Timed
	public ResponseEntity<Speaker> get(@PathVariable Long id,
			HttpServletResponse response) {
		this.log.debug("REST request to get Speaker : {}", id);
		Speaker speaker = this.speakerRepository.findOne(id);
		if (speaker == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.log.debug("Returning speaker: {}", speaker);
		return new ResponseEntity<>(speaker, HttpStatus.OK);
	}

	/**
	 * DELETE /rest/speakers/:id -> delete the "id" speaker.
	 */
	@RequestMapping(value = "/rest/speakers/{id}", method = RequestMethod.DELETE, produces = MediaTypeUtils.APPLICATION_JSON_UTF8_VALUE)
	@Timed
	public void delete(@PathVariable Long id) {
		this.log.debug("REST request to delete Speaker : {}", id);
		this.speakerRepository.delete(id);
	}
}
