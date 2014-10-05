package br.com.uaijug.kairos.repository;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource implements
		SerializableMessageSource {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * br.com.uaijug.kairos.repository.MessageSourceRepository#getAllProperties
	 * (java.util.Locale)
	 */
	@Override
	public Properties getAllProperties(Locale locale) {
		this.clearCacheIncludingAncestors();
		PropertiesHolder propertiesHolder = this.getMergedProperties(locale);
		Properties properties = propertiesHolder.getProperties();

		return properties;
	}
}
