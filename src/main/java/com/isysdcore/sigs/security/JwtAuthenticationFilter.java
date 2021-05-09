package com.isysdcore.sigs.security;

import com.isysdcore.sigs.user.User;
import com.isysdcore.sigs.user.UserRepository;
import com.isysdcore.sigs.util.JWTConstants;
import com.isysdcore.sigs.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Domingos M. Fernando
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository repository;

    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String header = httpServletRequest.getHeader(JWTConstants.HEADER_STRING);
        String username = null;
        String authToken = null;

        LOG.info("Request ::: " + httpServletRequest.getRequestURL());
        LOG.info("Request remote add ::: " + httpServletRequest.getRemoteAddr());
        LOG.info("Request Method ::: " + httpServletRequest.getMethod());

        if (header != null && header.startsWith(JWTConstants.TOKEN_PREFIX)) {
            authToken = header.replace(JWTConstants.TOKEN_PREFIX, "");
            try {

                String fullPathUrl = httpServletRequest.getRequestURL().toString();
                String requestMethod = httpServletRequest.getMethod();

                username = jwtTokenUtil.getUsernameFromToken(authToken);
                User currentUser = repository.findByCred(username);
                if (null == currentUser) {
                    logger.error("An error during getting user name");
                    throw new BadCredentialsException("Invalid tenant and user.");
                }

            }
            catch (IllegalArgumentException ex) {
                logger.error("An error during getting username from token", ex);
            }
            catch (ExpiredJwtException ex) {
                logger.warn("The token is expired and not valid anymore", ex);
            }
            catch (SignatureException | BadCredentialsException | WeakKeyException ex) {
                logger.error("Authentication Failed. Username or Password not valid.", ex);
            }
        }
        else {
            logger.warn("Couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
