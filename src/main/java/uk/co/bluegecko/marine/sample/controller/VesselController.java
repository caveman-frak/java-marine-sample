package uk.co.bluegecko.marine.sample.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class VesselController {

	private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

	@Bean
	public RouterFunction<ServerResponse> routerFunction(VesselHandler vesselHandler) {
		return route().nest(RequestPredicates.path("/vessel"),
				builder -> {
					builder.GET("", ACCEPT_JSON, request -> vesselHandler.all());
					builder.GET("/{id}", ACCEPT_JSON, request -> vesselHandler.find(request.pathVariable("id")));
				}
		).build();
	}
}
