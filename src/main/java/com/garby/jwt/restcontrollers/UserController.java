package com.garby.jwt.restcontrollers;

import com.garby.jwt.exceptions.UnauthorizedException;
import com.garby.jwt.models.UserCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    // The /login endpoint takes a UserCredentials object as a request body and validates the credentials.
    // If the credentials are valid, it generates a JWT token using the jjwt library.
    // The token is signed with a secret key, which should be securely stored in a configuration file or environment variable.

        @PostMapping("/login")
        public String login(@RequestBody UserCredentials credentials) throws UnauthorizedException {
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

       // The /profile endpoint requires an Authorization header with a valid JWT token.
       // It extracts the token from the header, verifies and parses it using the same secret key, and
       // retrieves the username from the token's claims.
       // This method can be customized to fetch the user profile information from database or any other source.


        @GetMapping("/profile")
        public String getProfile(@RequestHeader("Authorization") String authorizationHeader) {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            // Verify and parse the JWT token
            String username = Jwts.parser()        // TODO .parser() and .setSigningKey("string") have been deprecated. Find updated solutions
                    .setSigningKey("secretKey")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // TODO: Fetch user profile information from a database or any other source

            return "Hello, " + username + "!";
        }


}
