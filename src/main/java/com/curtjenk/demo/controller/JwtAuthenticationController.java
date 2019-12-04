package com.curtjenk.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.curtjenk.demo.security.JwtTokenUtil;
import com.curtjenk.demo.util.WebUtils;
import com.curtjenk.demo.security.JwtRequest;
import com.curtjenk.demo.security.JwtResponse;

@RestController
public class JwtAuthenticationController {
    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private WebUtils webUtils;

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationController(WebUtils webUtils, 
            AuthenticationManager authenticationManager, 
            JwtTokenUtil jwtTokenUtil) {

        this.webUtils = webUtils;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        logger.info(webUtils.getClientIp());
        
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        // final String token = jwtTokenUtil.generateToken(userDetails);
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}