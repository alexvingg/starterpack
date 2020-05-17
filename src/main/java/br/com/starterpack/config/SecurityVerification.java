package br.com.starterpack.config;


import br.com.starterpack.model.User;

public interface SecurityVerification {
	
	User findUserByToken(String token);
	
	boolean isTokenExpired(String token);
	
}
