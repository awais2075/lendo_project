package sa.lendo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sa.lendo.dto.response.ApiError;
import sa.lendo.service.CustomUserDetailsService;
import sa.lendo.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            if(Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtUtil.extractUsername(token);
            }

            if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                var userDetails = userDetailsService.loadUserByUsername(username);

                if(jwtUtil.isValidToken(token, userDetails)) {

                    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(objectMapper.writeValueAsString(new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage())));
        }
    }
}
