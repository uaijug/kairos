package br.com.uaijug.kairos.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.uaijug.kairos.Application;
import br.com.uaijug.kairos.domain.Speaker;
import br.com.uaijug.kairos.repository.SpeakerRepository;
import br.com.uaijug.kairos.utils.MediaTypeUtils;

/**
 * Test class for the SpeakerResource REST controller.
 *
 * @see SpeakerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class SpeakerResourceTest {

	private static final Long DEFAULT_ID = new Long(1L);

	private static final String DEFAULT_NAME = "João";

	private static final String DEFAULT_DESCRIPTION = "Descrição padrão";

	private static final String DEFAULT_LONG_DESCRIPTION = "Descrição longa padrão";

	private static final String DEFAULT_PHOTO = "photo.jpg";

	private static final String DEFAULT_THUMB = "thumb.jpg";

	private static final String UPD_NAME = "Pedro";

	private static final String UPD_DESCRIPTION = "Descrição atualizada";

	private static final String UPD_LONG_DESCRIPTION = "Descrição longa atualizada";

	private static final String UPD_PHOTO = "photo_atualizada.jpg";

	private static final String UPD_THUMB = "thumb_atualizada.jpg";

	@Inject
	private SpeakerRepository speakerRepository;

	private MockMvc restSpeakerMockMvc;

	private Speaker speaker;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		SpeakerResource speakerResource = new SpeakerResource();
		ReflectionTestUtils.setField(speakerResource, "speakerRepository", this.speakerRepository);

		this.restSpeakerMockMvc = MockMvcBuilders.standaloneSetup(speakerResource).build();

		this.speaker = new Speaker();
		this.speaker.setId(DEFAULT_ID);
		this.speaker.setName(DEFAULT_NAME);
		this.speaker.setDescription(DEFAULT_DESCRIPTION);
		this.speaker.setLongDescription(DEFAULT_LONG_DESCRIPTION);
		this.speaker.setPhoto(DEFAULT_PHOTO);
		this.speaker.setThumb(DEFAULT_THUMB);
	}

	@Test
	public void testCRUDSpeaker() throws Exception {

		// Create Speaker
		this.restSpeakerMockMvc.perform(
				post("/app/rest/speakers").contentType(MediaTypeUtils.APPLICATION_JSON_UTF8).content(
						TestUtil.convertObjectToJsonBytes(this.speaker))).andExpect(status().isOk());

		// Read Speaker
		this.restSpeakerMockMvc.perform(get("/app/rest/speakers/{id}", DEFAULT_ID)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypeUtils.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
				.andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION))
				.andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
				.andExpect(jsonPath("$.thumb").value(DEFAULT_THUMB));

		// Update Speaker
		this.speaker.setName(UPD_NAME);
		this.speaker.setDescription(UPD_DESCRIPTION);
		this.speaker.setLongDescription(UPD_LONG_DESCRIPTION);
		this.speaker.setPhoto(UPD_PHOTO);
		this.speaker.setThumb(UPD_THUMB);

		this.restSpeakerMockMvc.perform(
				post("/app/rest/speakers").contentType(MediaTypeUtils.APPLICATION_JSON_UTF8).content(
						TestUtil.convertObjectToJsonBytes(this.speaker))).andExpect(status().isOk());

		// Read updated Speaker
		this.restSpeakerMockMvc.perform(get("/app/rest/speakers/{id}", DEFAULT_ID)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypeUtils.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue())).andExpect(jsonPath("$.name").value(UPD_NAME))
				.andExpect(jsonPath("$.description").value(UPD_DESCRIPTION))
				.andExpect(jsonPath("$.longDescription").value(UPD_LONG_DESCRIPTION))
				.andExpect(jsonPath("$.photo").value(UPD_PHOTO)).andExpect(jsonPath("$.thumb").value(UPD_THUMB));

		// Delete Speaker
		this.restSpeakerMockMvc.perform(
				delete("/app/rest/speakers/{id}", DEFAULT_ID).accept(MediaTypeUtils.APPLICATION_JSON_UTF8)).andExpect(
				status().isOk());

		// Read nonexisting Speaker
		this.restSpeakerMockMvc.perform(
				get("/app/rest/speakers/{id}", DEFAULT_ID).accept(MediaTypeUtils.APPLICATION_JSON_UTF8)).andExpect(
				status().isNotFound());

	}
}
