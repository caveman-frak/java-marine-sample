package uk.co.bluegecko.marine.sample.controller;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
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
@Value
@Slf4j
public class VesselController {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(VesselHandler vesselHandler, ErrorHandler errorHandler) {
		return route().nest(RequestPredicates.path("/vessel"),
						builder -> {
							builder.before(request -> {
								log.info("Processing {} {}", request.method(), request.requestPath());
								return request;
							});
							builder.GET("", ACCEPT_JSON, request ->
									vesselHandler.all());
							builder.GET("/{id}", ACCEPT_JSON, request ->
									vesselHandler.find(request.pathVariable("id")));
							builder.DELETE("/{id}", all(), request ->
									vesselHandler.delete(request.pathVariable("id")));
						}
				)
				.onError(IllegalArgumentException.class, (e, request) ->
						errorHandler.buildResponse(e, request, HttpStatus.BAD_REQUEST))
				.build();
	}

}
