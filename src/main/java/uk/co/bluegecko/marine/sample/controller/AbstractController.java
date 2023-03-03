package uk.co.bluegecko.marine.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

/**
 * Error handling helper functions for controllers/routing.
 */
public class AbstractController {
	protected static final RequestPredicate ACCEPT_JSON = accept(APPLICATION_JSON);
	protected static final RequestPredicate ACCEPT_XML = accept(APPLICATION_XML);
	protected static final RequestPredicate ACCEPT_JSON_XML = accept(APPLICATION_JSON, APPLICATION_XML);

	protected ServerResponse buildResponse(Throwable e, ServerRequest request, HttpStatus status, Clock clock) {
		Map<String, Object> attributes = new LinkedHashMap<>();
		attributes.put("timestamp", AbstractController.timestamp(clock));
		attributes.put("status", status.value());
		attributes.put("error", status.getReasonPhrase());
		attributes.put("exception", e.getClass().getSimpleName());
		attributes.put("message", e.getMessage());
		attributes.put("path", format("%s %s", request.method(), request.requestPath()));
		attributes.put("accept", acceptType(request));
		return EntityResponse.fromObject(attributes).status(status).build();
	}

	private static String timestamp(Clock clock) {
		return LocalDateTime.now(clock)
				.truncatedTo(ChronoUnit.SECONDS).format(ISO_LOCAL_DATE_TIME);
	}

	private String acceptType(ServerRequest request) {
		return request.headers().accept().stream().map(MediaType::toString)
				.collect(Collectors.joining(","));
	}
}
