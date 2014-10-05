package br.com.uaijug.kairos.repository;

import java.util.Locale;
import java.util.Properties;

public interface SerializableMessageSource {

	public abstract Properties getAllProperties(Locale locale);

}