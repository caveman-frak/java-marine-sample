package uk.co.bluegecko.marine.sample.handler;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * Handle controller related errors.
 */
@Service
@Value
@Slf4j
public class ErrorHandler {

	Clock clock;
	RandomGenerator generator;

	public ServerResponse buildResponse(Throwable e, ServerRequest request, HttpStatus status) {
		String marker = buildMarker();
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
		detail.setProperty("timestamp", timestamp(clock));
		detail.setProperty("marker", marker);

		return EntityResponse.fromObject(detail).status(status).build();
	}

	private String buildMarker() {
		byte[] bytes = new byte[Long.BYTES];
		generator.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}

	private String timestamp(Clock clock) {
		return LocalDateTime.now(clock)
				.truncatedTo(ChronoUnit.SECONDS).format(ISO_LOCAL_DATE_TIME);
	}

	private String acceptType(ServerRequest request) {
		return request.headers().accept().stream().map(MediaType::toString)
				.collect(Collectors.joining(","));
	}

}
