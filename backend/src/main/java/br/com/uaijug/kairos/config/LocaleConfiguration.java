package br.com.uaijug.kairos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import br.com.uaijug.kairos.config.locale.AngularCookieLocaleResolver;
import br.com.uaijug.kairos.repository.SerializableMessageSource;
import br.com.uaijug.kairos.repository.SerializableResourceBundleMessageSource;

@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(LocaleConfiguration.class);

	private RelaxedPropertyResolver propertyResolver;

	public LocaleConfiguration() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.messageSource.");
	}

	@Bean(name = "localeResolver")
	public LocaleResolver localeResolver() {
		final AngularCookieLocaleResolver cookieLocaleResolver = new AngularCookieLocaleResolver();
		cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY");
		return cookieLocaleResolver;
	}

	@Bean
	public SerializableMessageSource serializableMessageSource() {

		this.log.debug("Configuring SerializableResourceBundleMessageSource");
		final SerializableResourceBundleMessageSource serializableMessageSource = new SerializableResourceBundleMessageSource();
		serializableMessageSource.setBasename("classpath:/i18n/messages");
		serializableMessageSource.setDefaultEncoding("UTF-8");
		serializableMessageSource.setCacheSeconds(this.propertyResolver.getProperty("cacheSeconds", Integer.class, 1));
		return serializableMessageSource;
	}

	@Bean
	public MessageSource messageSource() {
		this.log.debug("Configuring MessageSource");
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(this.propertyResolver.getProperty("cacheSeconds", Integer.class, 1));
		return messageSource;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");

		registry.addInterceptor(localeChangeInterceptor);
	}
}
