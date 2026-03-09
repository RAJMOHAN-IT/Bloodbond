package com.randy.bloodbond.security;
import com.randy.bloodbond.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
@Autowired
private JwtUtil jwtUtil;
@Autowired
private UserService userService;
@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
String path = request.getServletPath();
return path.startsWith("/api/auth/") || path.startsWith("/assets/") || path.startsWith("/pages/") || path.equals("/") || path.equals("/index.html") || path.equals("/login.html") || path.equals("/register.html") || path.equals("/api/test");
}
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String authHeader = request.getHeader("Authorization");
String username = null;
String jwtToken = null;
if (authHeader != null && authHeader.trim().startsWith("Bearer ")) {
jwtToken = authHeader.trim().substring(7).trim();
try { username = jwtUtil.extractUsername(jwtToken); } catch (Exception e) { filterChain.doFilter(request, response); return; }
}
if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
try {
UserDetails userDetails = userService.loadUserByUsername(username);
if (jwtUtil.validateToken(jwtToken, userDetails)) {
UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
SecurityContextHolder.getContext().setAuthentication(authToken);
}
} catch (Exception e) {
filterChain.doFilter(request, response);
return;
}
}
filterChain.doFilter(request, response);
}
}