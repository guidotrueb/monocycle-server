package io.monocycle.server.controllers.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo serverKeyNotFound(HttpServletRequest req, Exception ex) {
		
		LOGGER.error(req.getRequestURI(), ex);
		
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage("server.key.not.found", null, locale);

		errorMessage += 1;
		String errorURL = req.getRequestURL().toString();

		return new ErrorInfo(errorURL, errorMessage);
	}

}
