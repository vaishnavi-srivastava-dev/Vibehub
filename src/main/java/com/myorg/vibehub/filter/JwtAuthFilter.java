package com.myorg.vibehub.filter;

import com.myorg.vibehub.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    //as we need AuthObj too and userdetailsservice has the method to getUser();
    @Autowired
    private UserDetailsService userDetailsService;

    //write our main code, what all things we need
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); //has Bearer

        String token = null;
        String username = null;
        String role = null;

        if(authHeader != null && authHeader.startsWith("Bearer"))
        {
            //7 as Bearer has 6 letters + one space
            token = authHeader.substring(7);

            //extract username from real token
            username = jwtUtil.extractUsername(token);
            //role = ;

        }

        //user should not be logged in already. No sessions (as no form login)
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //extracted user details from username. to verify but this is unneccessary
            //because else calls will go on database.

            //userDetails is needed as we do not have authorities(ROLE) in the token.
            //an upgraded version of this would be to add authorities in token itself.


            //UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    //userDetails.getAuthorities()
                  //  List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        //add to filter
        //forward request
        filterChain.doFilter(request,response);

        System.out.println("Token added to config");
    }
}
