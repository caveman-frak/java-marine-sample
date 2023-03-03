package uk.co.bluegecko.marine.sample.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;

import java.time.Clock;

import static org.springframework.web.servlet.function.RequestPredicates.all;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * Routing for Vessel end-points.
 */
@Configuration(proxyBeanMethods = false)
public class VesselController extends AbstractController {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(VesselHandler vesselHandler, Clock clock) {
		return route().nest(RequestPredicates.path("/vessel"),
						builder -> {
							builder.GET("", AbstractController.ACCEPT_JSON, request ->
									vesselHandler.all());
							builder.GET("/{id}", AbstractController.ACCEPT_JSON, request ->
									vesselHandler.find(request.pathVariable("id")));
							builder.DELETE("/{id}", all(), request ->
									vesselHandler.delete(request.pathVariable("id")));
						}
				)
				.onError(IllegalArgumentException.class, (e, request) ->
						buildResponse(e, request, HttpStatus.BAD_REQUEST, clock)
				)
				.build();
	}

}
