package uk.co.bluegecko.marine.sample.controller;

import org.springframework.web.servlet.function.RequestPredicate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

/**
 * Controller constants.
 */
public class ControllerConstants {
	public static final RequestPredicate ACCEPT_JSON = accept(APPLICATION_JSON);
	public static final RequestPredicate ACCEPT_XML = accept(APPLICATION_XML);
	public static final RequestPredicate ACCEPT_JSON_XML = accept(APPLICATION_JSON, APPLICATION_XML);
}
