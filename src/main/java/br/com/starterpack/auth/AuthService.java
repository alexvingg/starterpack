package br.com.starterpack.auth;

import br.com.starterpack.config.SecurityVerification;
import br.com.starterpack.model.User;
import br.com.starterpack.repository.UserRepository;
import br.com.starterpack.util.ClaimNames;
import br.com.starterpack.util.JWT;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthService implements SecurityVerification {

	@Autowired
	private UserRepository userRepository;

	private static final String SECRET_KEY = "server.secretkey";

	private final Environment env;

	public AuthService(Environment env) {
		this.env = env;
	}

	@Override
	public User findUserByToken(final String token) {
		log.info("token: {}", token);

		final Claims claims = JWT.claims(this.env.getProperty("SERVER_SECRET_KEY",
				this.env.getProperty(SECRET_KEY)), token);

		return userRepository.findById(claims.get(ClaimNames.USER).toString()).get();

	}

	@Override
	public boolean isTokenExpired(final String token) {
		log.info("token: {}", token);
		try {
			
			final Claims claims = JWT.claims(this.env.getProperty("SERVER_SECRET_KEY", 
					this.env.getProperty(SECRET_KEY)), token);
			
			return new Date().after(claims.getExpiration());
		} catch(Exception e){
			
			log.info("Token problem {} excep {}", token, e.getMessage());
			return true;
		}
		
		
	}

}
