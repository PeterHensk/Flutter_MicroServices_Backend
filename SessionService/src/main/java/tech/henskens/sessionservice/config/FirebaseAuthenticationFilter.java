package tech.henskens.sessionservice.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String idToken = authorizationHeader.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                logger.info("Token verified for user: {}", decodedToken.getUid());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        decodedToken, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("Token verification failed", e);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return; // Stop processing further if token verification fails
            }
        } else {
            logger.warn("No authorization header or invalid format");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return; // Stop processing further if no authorization header is present
        }
        filterChain.doFilter(request, response);
    }

}

