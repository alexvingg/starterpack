package br.com.starterpack.auth;


import br.com.starterpack.model.User;
import br.com.starterpack.response.AuthResponse;
import br.com.starterpack.response.UserResponse;
import br.com.starterpack.service.CustomUserDetailsService;
import br.com.starterpack.util.JwtTokenUtil;
import br.com.starterpack.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthenticationResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public DeferredResult<ResponseEntity<Response>> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final User user = this.userDetailsService.getUserByUsername(authenticationRequest.getUsername());

        final DeferredResult<ResponseEntity<Response>> dr = new DeferredResult<>();

        final String token = jwtTokenUtil.generateToken(userDetails);

        dr.setResult(ResponseEntity.ok(Response.ok().addData("auth", new AuthResponse(token, UserResponse.toJson(user)))));

        return dr;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
