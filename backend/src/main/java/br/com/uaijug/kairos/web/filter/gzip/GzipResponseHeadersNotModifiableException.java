package br.com.uaijug.kairos.web.filter.gzip;

import javax.servlet.ServletException;

public class GzipResponseHeadersNotModifiableException extends ServletException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -94125138727030151L;
	

	public GzipResponseHeadersNotModifiableException(String message) {
        super(message);
    }
}
