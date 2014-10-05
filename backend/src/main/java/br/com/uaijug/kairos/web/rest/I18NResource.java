package br.com.uaijug.kairos.web.rest;

import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uaijug.kairos.repository.SerializableMessageSource;
import br.com.uaijug.kairos.utils.MediaTypeUtils;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/app")
public class I18NResource {

	private final Logger log = LoggerFactory.getLogger(I18NResource.class);

	@Inject
	private SerializableMessageSource serializableMessageSource;

	/**
	 * POST /rest/speakers -> Create a new speaker.
	 */
	@RequestMapping(value = "/rest/i18n", method = RequestMethod.GET, produces = MediaTypeUtils.APPLICATION_JSON_UTF8_VALUE)
	@Timed
	public ResponseEntity<Properties> get(@RequestParam("lang") String lang) {
		this.log.debug("REST request to get locale: {}", lang);

		if (lang == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Properties allProperties = this.serializableMessageSource.getAllProperties(new Locale(lang));

		return new ResponseEntity<>(allProperties, HttpStatus.OK);

	}

}
