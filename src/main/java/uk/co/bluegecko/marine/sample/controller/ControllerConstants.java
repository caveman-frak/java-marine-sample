package uk.co.bluegecko.marine.sample.controller;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.function.RequestPredicate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

/**
 * Controller constants.
 */
@UtilityClass
public class ControllerConstants {
	public static RequestPredicate ACCEPT_JSON = accept(APPLICATION_JSON);
	public static RequestPredicate ACCEPT_XML = accept(APPLICATION_XML);
	public static RequestPredicate ACCEPT_JSON_XML = accept(APPLICATION_JSON, APPLICATION_XML);
}
