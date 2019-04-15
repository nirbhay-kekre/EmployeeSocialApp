package edu.sjsu.cmpe275.api.error.mapping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * SocialApp ExceptionHandler, its a controller Advice
 * @author nirbhaykekre
 */
@ControllerAdvice
public class SocialAppExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * handleMissingServeletRequestParameter
	 * 
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	protected ResponseEntity<Object> handleMissingServeletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String format = request.getParameter("format");
		HttpHeaders header = new HttpHeaders();
		headers.add("Content-Type", format + "; charset=UTF-8");
		return new ResponseEntity<Object>(header,HttpStatus.BAD_REQUEST);
	}

}
