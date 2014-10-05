package br.com.uaijug.kairos.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import br.com.uaijug.kairos.repository.SerializableMessageSource;
import br.com.uaijug.kairos.utils.MediaTypeUtils;

/**
 * Test classes for the I18NResorce REST Controller
 *
 * @author Josenaldo de Oliveira Matos Filho
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class I18NResourceTest {

	private MockMvc restI18NMockMvc;

	@Inject
	private SerializableMessageSource serializableMessageSource;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		I18NResource i18nResource = new I18NResource();

		ReflectionTestUtils.setField(i18nResource, "serializableMessageSource", this.serializableMessageSource);
		this.restI18NMockMvc = MockMvcBuilders.standaloneSetup(i18nResource).build();
	}

	@Test
	public void testGetLanguages() throws Exception {

		this.restI18NMockMvc.perform(get("/app/rest/i18n?lang={locale}", "en")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypeUtils.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$['error.message']").value("Message:"));

		this.restI18NMockMvc.perform(get("/app/rest/i18n/?lang={locale}", "pt")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypeUtils.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$['error.message']").value("Mensagem:"));

	}
}
