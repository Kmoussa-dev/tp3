package fr.univorleans.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route( r-> r.path("/api/jeu/**")
						.filters(f->f.rewritePath("/api/(?<remains>.*)","/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8082")
				)
				.route(r -> r.path("/api/auth/**")
						.filters(f -> f.rewritePath("/api/auth/(?<remains>.*)", "/authent/${remains}")
								.preserveHostHeader()
						)
						.uri("http://localhost:8081")
				)
				.build();
	}
}

