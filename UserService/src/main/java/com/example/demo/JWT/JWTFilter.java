package com.example.demo.JWT;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import jakarta.servlet.http.Cookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.UserinfoService.UserinfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter{

	  
	  private Jwtservice jwtservice;
	  
	 
	 
	    
	    @Autowired
	    private UserinfoService userinfoservice; 
	    
	    public JWTFilter() {

	    }
	    
	    @Autowired
	    public JWTFilter(Jwtservice jwtService) {
	        this.jwtservice=jwtService;
	    }
		/*
		 * @Autowired public JWTFilter (JwtService jwtservice, UserinfoService
		 * userDetailsService) { this.jwtservice=jwtservice;
		 * this.userDetailsService=userDetailsService; }
		 */


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		List<String> paths = List.of("/login","/authenticate","/api/videos/trending");

		String token = null;
		String username = null;

		String requestedPath = request.getServletPath();




		if (paths.stream().anyMatch(path -> path.equals(requestedPath))) {
			filterChain.doFilter(request, response);
			return;
		}


		// First try from header
		String authHeader = request.getHeader("Authorization");
		System.out.println("AuthHeader"+authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		// If not found in header, check cookie
		if (token == null && request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("accessToken".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
     logger.info(token);
		// Extract username only if token is found
		if (token != null) {
			try {
				username = jwtservice.extractUsername(token);
				System.out.println("JWT Username: " + username);
			} catch (Exception e) {
				System.out.println("Invalid token: " + e.getMessage());
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userinfoservice.loadUserByUsername(username);
			if (jwtservice.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}




}
