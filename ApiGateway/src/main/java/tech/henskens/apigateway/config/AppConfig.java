package tech.henskens.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
public class AppConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            if (ctx.getRequest().getMethod() == HttpMethod.OPTIONS) {
                ctx.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
                ctx.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");
                ctx.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
                ctx.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
                return Mono.empty();
            } else {
                return chain.filter(ctx);
            }
        };
    }
}