package uk.co.bluegecko.marine.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

/**
 * Error handling functions for controllers/routing.
 */
@Slf4j
public class AbstractController {
	protected static final RequestPredicate ACCEPT_JSON = accept(APPLICATION_JSON);
	protected static final RequestPredicate ACCEPT_XML = accept(APPLICATION_XML);
	protected static final RequestPredicate ACCEPT_JSON_XML = accept(APPLICATION_JSON, APPLICATION_XML);

	protected ServerResponse buildResponse(Throwable e, ServerRequest request, HttpStatus status, Clock clock) {
		String marker = getMarker();
		log.info("Error handled during {} {} [{}], exception {} -> {} {}. Marker: ({})",
				request.method(),
				request.requestPath(),
				acceptType(request),
				e.getClass().getSimpleName(),
				status.value(),
				status.getReasonPhrase(),
				marker);

		ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, e.getLocalizedMessage());
		detail.setInstance(request.uri());
		detail.setProperty("timestamp", AbstractController.timestamp(clock));
		detail.setProperty("marker", marker);

		return EntityResponse.fromObject(detail).status(status).build();
	}

	private static String getMarker() {
		Random random = new Random();
		byte[] bytes = new byte[Long.BYTES];
		random.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
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
