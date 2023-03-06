package uk.co.bluegecko.marine.sample.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import uk.co.bluegecko.marine.sample.handler.ErrorHandler;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;

import static org.springframework.web.servlet.function.RequestPredicates.all;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static uk.co.bluegecko.marine.sample.controller.ControllerConstants.ACCEPT_JSON;

/**
 * Routing for Vessel end-points.
 */
@Configuration(proxyBeanMethods = false)
public class VesselController {

	@Bean
	public RouterFunction<ServerResponse> vesselRouting(VesselHandler vesselHandler, ErrorHandler errorHandler) {
		return route().nest(RequestPredicates.path("/vessel"),
						builder -> {
							builder.before(errorHandler::logProcessingRequest);
							builder.GET("", ACCEPT_JSON, request ->
									vesselHandler.all());
							builder.GET("/{id}", ACCEPT_JSON, request ->
									vesselHandler.find(request.pathVariable("id")));
							builder.DELETE("/{id}", all(), request ->
									vesselHandler.delete(request.pathVariable("id")));
						}
				)
				.onError(IllegalArgumentException.class, (e, request) ->
						errorHandler.buildExceptionResponse(e, request, HttpStatus.BAD_REQUEST))
				.build();
	}

}