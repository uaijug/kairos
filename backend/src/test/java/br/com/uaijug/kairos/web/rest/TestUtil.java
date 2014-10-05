package br.com.uaijug.kairos.web.rest;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for testing REST controllers.
 */
public class TestUtil {

	/**
	 * Convert an object to JSON byte array.
	 *
	 * @param object
	 *            the object to convert
	 * @return the JSON byte array
	 * @throws IOException
	 */
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
