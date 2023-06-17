package com.garby.jwt.restcontrollers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

        @PostMapping("/login")
        public String login(@RequestBody UserCredentials credentials) {
            // TODO: Validate user credentials from a database or any other source

            // For simplicity, we'll use a fixed user/password combination
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            if ("admin".equals(username) && "password".equals(password)) {
                // Generate a JWT token
                String token = Jwts.builder()
                        .setSubject(username)
                        .signWith(SignatureAlgorithm.HS256, "secretKey")
                        .compact();

                return token;
            } else {
                throw new UnauthorizedException("Invalid username or password");
            }
        }

        @GetMapping("/profile")
        public String getProfile(@RequestHeader("Authorization") String authorizationHeader) {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            // Verify and parse the JWT token
            String username = Jwts.parser()
                    .setSigningKey("secretKey")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // TODO: Fetch user profile information from a database or any other source

            return "Hello, " + username + "!";
        }


}
